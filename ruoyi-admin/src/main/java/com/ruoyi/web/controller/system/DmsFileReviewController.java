package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.DmsFilePublish;
import com.ruoyi.system.domain.DmsFileReview;
import com.ruoyi.system.service.IDmsFileReviewService;
import com.ruoyi.system.service.IDmsFileInfoService;
import com.ruoyi.system.service.IDmsFilePublishService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 文档评阅Controller
 * 
 * @author HYZ
 * @date 2024-02-05
 */
@RestController
@RequestMapping("/system/review")
public class DmsFileReviewController extends BaseController
{
    @Autowired
    private IDmsFileReviewService dmsFileReviewService;
    @Autowired
    private IDmsFileInfoService dmsFileInfoService;
    @Autowired
    private IDmsFilePublishService dmsFilePublishService;
    /**
     * 查询文档评阅列表
     */
    @PreAuthorize("@ss.hasPermi('system:review:list')")
    @GetMapping("/list")
    public TableDataInfo list(DmsFileReview dmsFileReview)
    {
        startPage();
        List<DmsFileReview> list = dmsFileReviewService.selectDmsFileReviewList(dmsFileReview);
        return getDataTable(list);
    }

    /**
     * 导出文档评阅列表
     */
    @PreAuthorize("@ss.hasPermi('system:review:export')")
    @Log(title = "文档评阅", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DmsFileReview dmsFileReview)
    {
        List<DmsFileReview> list = dmsFileReviewService.selectDmsFileReviewList(dmsFileReview);
        ExcelUtil<DmsFileReview> util = new ExcelUtil<DmsFileReview>(DmsFileReview.class);
        util.exportExcel(response, list, "文档评阅数据");
    }

    /**
     * 获取文档评阅详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:review:query')")
    @GetMapping(value = "/{fileId}/{reviewerId}")
    public AjaxResult getInfo(@PathVariable("fileId") String fileId,@PathVariable("reviewerId") Long reviewerId)
    {
    	DmsFileReview dmsFileReview = new DmsFileReview();
        dmsFileReview.setFileId(fileId);
        dmsFileReview.setReviewerId(reviewerId);
        return success(dmsFileReviewService.selectDmsFileReviewByFileIdAndReviewerId(dmsFileReview));
    }

    /**
     * 新增文档评阅
     */
    @PreAuthorize("@ss.hasPermi('system:review:add')")
    @Log(title = "文档评阅", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DmsFileReview dmsFileReview)
    {
        return toAjax(dmsFileReviewService.insertDmsFileReview(dmsFileReview));
    }

    /**
     * 修改文档评阅
     */
    @PreAuthorize("@ss.hasPermi('system:review:edit')")
    @Log(title = "文档评阅", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DmsFileReview dmsFileReview)
    {
    	// 更新评审结果
        int updateReviewResult = dmsFileReviewService.updateDmsFileReview(dmsFileReview);
        // 查询所有评审结果
        List<DmsFileReview> allReviews = dmsFileReviewService.getAllReviewsResultByFileId(dmsFileReview.getFileId());
        // 检查是否有评审结果为需修改
        boolean hasResultThree = allReviews.stream().anyMatch(review -> review.getIsPassed() == 3);
        if (hasResultThree) {
            long fileStatus = 4L;
            int updateFileStatusResult = dmsFileInfoService.updateDmsFileStatus(dmsFileReview.getFileId(), fileStatus);
            if (updateFileStatusResult > 0) {
                return AjaxResult.success("评审结果包含需修改，文件状态修改为待修改");
            } else {
                return AjaxResult.error("修改文件状态失败");
            }
        }else {
        	// 检查是否所有评审都通过
            boolean allApproved = allReviews.stream().allMatch(review -> review.getIsPassed() == 2);
            if (allApproved) {
            	long fileStatus = 2L;
                int updateFileStatusResult = dmsFileInfoService.updateDmsFileStatus(dmsFileReview.getFileId(), fileStatus);
                if (updateFileStatusResult > 0) {
					// 文档定稿表新增信息（查询是否有重复ID，若无新增信息，若有修改信息）
                    DmsFilePublish dmsFilePublish = new DmsFilePublish();
                    dmsFilePublish.setFileId(dmsFileReview.getFileId());
                    dmsFilePublish.setPublishId(dmsFileReview.getPublishId());
                 // 查询是否存在相同ID的记录
                    DmsFilePublish existingRecord = dmsFilePublishService.selectDmsFilePublishByFileId(dmsFilePublish.getFileId());
                    if (existingRecord != null) {
                        // 如果记录已存在，则重置定稿结果和定稿意见
                    	dmsFilePublish.setIsPassed(1L);
                    	dmsFilePublish.setComment("");
                        int updateResult = dmsFilePublishService.updateDmsFilePublish(dmsFilePublish);
                    } else {
                        // 如果记录不存在，则执行插入操作
                        int insertResult = dmsFilePublishService.insertDmsFilePublish(dmsFilePublish);
                    }
                    return AjaxResult.success("评审已通过，文件状态修改为待发布");
                } else {
                    return AjaxResult.error("修改文件状态失败");
                }
            }else {
            	long fileStatus = 1L;
            	int updateFileStatusResult = dmsFileInfoService.updateDmsFileStatus(dmsFileReview.getFileId(), fileStatus);
            	if (updateFileStatusResult > 0) {
                    return AjaxResult.success("存在待评审，文件状态修改为待评审");
                } else {
                    return AjaxResult.error("修改文件状态失败");
                }
            }
        }
    }

    /**
     * 删除文档评阅
     */
    @PreAuthorize("@ss.hasPermi('system:review:remove')")
    @Log(title = "文档评阅", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fileIds}")
    public AjaxResult remove(@PathVariable String[] fileIds)
    {
        return toAjax(dmsFileReviewService.deleteDmsFileReviewByFileIds(fileIds));
    }
}

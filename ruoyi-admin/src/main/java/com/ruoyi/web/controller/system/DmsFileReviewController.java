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
import com.ruoyi.system.domain.DmsFileInfo;
import com.ruoyi.system.domain.DmsFileReview;
import com.ruoyi.system.service.IDmsFileInfoService;
import com.ruoyi.system.service.IDmsFileReviewService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 评阅Controller
 * 
 * @author hyz
 * @date 2024-05-11
 */
@RestController
@RequestMapping("/system/review")
public class DmsFileReviewController extends BaseController
{
    @Autowired
    private IDmsFileReviewService dmsFileReviewService;
    @Autowired
    private IDmsFileInfoService dmsFileInfoService;
    /**
     * 查询评阅列表
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
     * 导出评阅列表
     */
    @PreAuthorize("@ss.hasPermi('system:review:export')")
    @Log(title = "评阅", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DmsFileReview dmsFileReview)
    {
        List<DmsFileReview> list = dmsFileReviewService.selectDmsFileReviewList(dmsFileReview);
        ExcelUtil<DmsFileReview> util = new ExcelUtil<DmsFileReview>(DmsFileReview.class);
        util.exportExcel(response, list, "评阅数据");
    }

    /**
     * 获取评阅详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:review:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(dmsFileReviewService.selectDmsFileReviewById(id));
    }

    /**
     * 新增评阅
     */
    @PreAuthorize("@ss.hasPermi('system:review:add')")
    @Log(title = "评阅", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DmsFileReview dmsFileReview)
    {
        return toAjax(dmsFileReviewService.insertDmsFileReview(dmsFileReview));
    }

    /**
     * 修改评阅
     */
    @PreAuthorize("@ss.hasPermi('system:review:edit')")
    @Log(title = "评阅", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DmsFileReview dmsFileReview)
    {
        dmsFileReviewService.updateDmsFileReview(dmsFileReview);
        // 查询本轮评阅是否全部完成，若完成，修改file状态待评阅（1）->待发布（2）
        // 若文件不为待评阅（1），则不修改
        DmsFileInfo dmsFileInfo = dmsFileInfoService.selectDmsFileInfoByFileId(dmsFileReview.getFileId());
        Long filestatus = dmsFileInfo.getFileStatus();
        if(filestatus==1L) {
	        DmsFileReview searchcurrentstatus = new DmsFileReview();
	        searchcurrentstatus.setFileId(dmsFileReview.getFileId());
	        searchcurrentstatus.setIsCurrent(1);
	        List<DmsFileReview> currentlist = dmsFileReviewService.selectDmsFileReviewList(searchcurrentstatus);
	        boolean allStatusAreTwo = currentlist.stream().allMatch(review -> review.getStatus() == 2);
	        if (allStatusAreTwo) {
	        	dmsFileInfoService.updateDmsFileStatus(dmsFileReview.getFileId(), 2L);
	        }
    	}
        return AjaxResult.success("评阅成功");
    }

    /**
     * 删除评阅
     */
    @PreAuthorize("@ss.hasPermi('system:review:remove')")
    @Log(title = "评阅", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dmsFileReviewService.deleteDmsFileReviewByIds(ids));
    }
}

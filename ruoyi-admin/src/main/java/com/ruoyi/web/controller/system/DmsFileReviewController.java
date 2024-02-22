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
import com.ruoyi.system.domain.DmsFileReview;
import com.ruoyi.system.service.IDmsFileReviewService;
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
        return toAjax(dmsFileReviewService.updateDmsFileReview(dmsFileReview));
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

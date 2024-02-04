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
 * 评审Controller
 * 
 * @author HYZ
 * @date 2024-02-01
 */
@RestController
@RequestMapping("/system/review")
public class DmsFileReviewController extends BaseController
{
    @Autowired
    private IDmsFileReviewService dmsFileReviewService;

    /**
     * 查询评审列表
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
     * 导出评审列表
     */
    @PreAuthorize("@ss.hasPermi('system:review:export')")
    @Log(title = "评审", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DmsFileReview dmsFileReview)
    {
        List<DmsFileReview> list = dmsFileReviewService.selectDmsFileReviewList(dmsFileReview);
        ExcelUtil<DmsFileReview> util = new ExcelUtil<DmsFileReview>(DmsFileReview.class);
        util.exportExcel(response, list, "评审数据");
    }

    /**
     * 获取评审详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:review:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(dmsFileReviewService.selectDmsFileReviewById(id));
    }

    /**
     * 新增评审
     */
    @PreAuthorize("@ss.hasPermi('system:review:add')")
    @Log(title = "评审", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DmsFileReview dmsFileReview)
    {
        return toAjax(dmsFileReviewService.insertDmsFileReview(dmsFileReview));
    }

    /**
     * 修改评审
     */
    @PreAuthorize("@ss.hasPermi('system:review:edit')")
    @Log(title = "评审", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DmsFileReview dmsFileReview)
    {
        return toAjax(dmsFileReviewService.updateDmsFileReview(dmsFileReview));
    }

    /**
     * 删除评审
     */
    @PreAuthorize("@ss.hasPermi('system:review:remove')")
    @Log(title = "评审", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(dmsFileReviewService.deleteDmsFileReviewByIds(ids));
    }
}

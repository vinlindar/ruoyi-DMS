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
import com.ruoyi.system.domain.DmsNewsImages;
import com.ruoyi.system.service.IDmsNewsImagesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 新闻照片Controller
 * 
 * @author HYZ
 * @date 2024-06-02
 */
@RestController
@RequestMapping("/system/images")
public class DmsNewsImagesController extends BaseController
{
    @Autowired
    private IDmsNewsImagesService dmsNewsImagesService;

    /**
     * 查询新闻照片列表
     */
    @PreAuthorize("@ss.hasPermi('system:images:list')")
    @GetMapping("/list")
    public TableDataInfo list(DmsNewsImages dmsNewsImages)
    {
        startPage();
        List<DmsNewsImages> list = dmsNewsImagesService.selectDmsNewsImagesList(dmsNewsImages);
        return getDataTable(list);
    }

    /**
     * 导出新闻照片列表
     */
    @PreAuthorize("@ss.hasPermi('system:images:export')")
    @Log(title = "新闻照片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DmsNewsImages dmsNewsImages)
    {
        List<DmsNewsImages> list = dmsNewsImagesService.selectDmsNewsImagesList(dmsNewsImages);
        ExcelUtil<DmsNewsImages> util = new ExcelUtil<DmsNewsImages>(DmsNewsImages.class);
        util.exportExcel(response, list, "新闻照片数据");
    }

    /**
     * 获取新闻照片详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:images:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(dmsNewsImagesService.selectDmsNewsImagesById(id));
    }

    /**
     * 新增新闻照片
     */
    @PreAuthorize("@ss.hasPermi('system:images:add')")
    @Log(title = "新闻照片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DmsNewsImages dmsNewsImages)
    {
        return toAjax(dmsNewsImagesService.insertDmsNewsImages(dmsNewsImages));
    }

    /**
     * 修改新闻照片
     */
    @PreAuthorize("@ss.hasPermi('system:images:edit')")
    @Log(title = "新闻照片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DmsNewsImages dmsNewsImages)
    {
        return toAjax(dmsNewsImagesService.updateDmsNewsImages(dmsNewsImages));
    }

    /**
     * 删除新闻照片
     */
    @PreAuthorize("@ss.hasPermi('system:images:remove')")
    @Log(title = "新闻照片", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dmsNewsImagesService.deleteDmsNewsImagesByIds(ids));
    }
}

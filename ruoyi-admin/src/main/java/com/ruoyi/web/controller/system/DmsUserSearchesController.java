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
import com.ruoyi.system.domain.DmsUserSearches;
import com.ruoyi.system.service.IDmsUserSearchesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户自定义搜索Controller
 * 
 * @author HYZ
 * @date 2024-03-27
 */
@RestController
@RequestMapping("/system/searches")
public class DmsUserSearchesController extends BaseController
{
    @Autowired
    private IDmsUserSearchesService dmsUserSearchesService;

    /**
     * 查询用户自定义搜索列表
     */
    @PreAuthorize("@ss.hasPermi('system:searches:list')")
    @GetMapping("/list")
    public TableDataInfo list(DmsUserSearches dmsUserSearches)
    {
        startPage();
        List<DmsUserSearches> list = dmsUserSearchesService.selectDmsUserSearchesList(dmsUserSearches);
        return getDataTable(list);
    }

    /**
     * 导出用户自定义搜索列表
     */
    @PreAuthorize("@ss.hasPermi('system:searches:export')")
    @Log(title = "用户自定义搜索", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DmsUserSearches dmsUserSearches)
    {
        List<DmsUserSearches> list = dmsUserSearchesService.selectDmsUserSearchesList(dmsUserSearches);
        ExcelUtil<DmsUserSearches> util = new ExcelUtil<DmsUserSearches>(DmsUserSearches.class);
        util.exportExcel(response, list, "用户自定义搜索数据");
    }

    /**
     * 获取用户自定义搜索详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:searches:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(dmsUserSearchesService.selectDmsUserSearchesById(id));
    }

    /**
     * 新增用户自定义搜索(先删除后新增，每个用户仅支持一条自定义搜索)
     */
    @Log(title = "用户自定义搜索", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DmsUserSearches dmsUserSearches)
    {
    	Long userId = dmsUserSearches.getUserId();
    	dmsUserSearchesService.deleteDmsUserSearchesByuserId(userId);
        return toAjax(dmsUserSearchesService.insertDmsUserSearches(dmsUserSearches));
    }

    /**
     * 修改用户自定义搜索
     */
    @PreAuthorize("@ss.hasPermi('system:searches:edit')")
    @Log(title = "用户自定义搜索", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DmsUserSearches dmsUserSearches)
    {
        return toAjax(dmsUserSearchesService.updateDmsUserSearches(dmsUserSearches));
    }

    /**
     * 删除用户自定义搜索
     */
    @Log(title = "用户自定义搜索", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userId}")
    public AjaxResult remove(@PathVariable Long userId)
    {
        return toAjax(dmsUserSearchesService.deleteDmsUserSearchesByuserId(userId));
    }
}

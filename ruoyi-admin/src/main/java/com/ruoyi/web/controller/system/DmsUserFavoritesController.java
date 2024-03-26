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
import com.ruoyi.system.domain.DmsUserFavorites;
import com.ruoyi.system.service.IDmsUserFavoritesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户收藏文件Controller
 * 
 * @author HYZ
 * @date 2024-03-26
 */
@RestController
@RequestMapping("/system/favorites")
public class DmsUserFavoritesController extends BaseController
{
    @Autowired
    private IDmsUserFavoritesService dmsUserFavoritesService;

    /**
     * 查询用户收藏文件列表
     */
    @GetMapping("/list")
    public TableDataInfo list(DmsUserFavorites dmsUserFavorites)
    {
        startPage();
        List<DmsUserFavorites> list = dmsUserFavoritesService.selectDmsUserFavoritesList(dmsUserFavorites);
        return getDataTable(list);
    }

    /**
     * 导出用户收藏文件列表
     */
    @PreAuthorize("@ss.hasPermi('system:favorites:export')")
    @Log(title = "用户收藏文件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DmsUserFavorites dmsUserFavorites)
    {
        List<DmsUserFavorites> list = dmsUserFavoritesService.selectDmsUserFavoritesList(dmsUserFavorites);
        ExcelUtil<DmsUserFavorites> util = new ExcelUtil<DmsUserFavorites>(DmsUserFavorites.class);
        util.exportExcel(response, list, "用户收藏文件数据");
    }

    /**
     * 获取用户收藏文件详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:favorites:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return success(dmsUserFavoritesService.selectDmsUserFavoritesByUserId(userId));
    }

    /**
     * 新增用户收藏文件（先删除后新增）
     */
    @Log(title = "用户收藏文件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DmsUserFavorites dmsUserFavorites)
    {
    	dmsUserFavoritesService.deleteDmsUserFavorites(dmsUserFavorites);
        return toAjax(dmsUserFavoritesService.insertDmsUserFavorites(dmsUserFavorites));
    }

    /**
     * 删除用户收藏文件
     */
    @Log(title = "用户收藏文件", businessType = BusinessType.DELETE)
	@DeleteMapping
    public AjaxResult remove(@RequestBody DmsUserFavorites dmsUserFavorites)
    {
        return toAjax(dmsUserFavoritesService.deleteDmsUserFavorites(dmsUserFavorites));
    }
}

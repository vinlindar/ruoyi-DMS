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
import com.ruoyi.system.domain.DmsFilePermissions;
import com.ruoyi.system.service.IDmsFilePermissionsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 文档权限Controller
 * 
 * @author hyz
 * @date 2024-03-08
 */
@RestController
@RequestMapping("/system/permissions")
public class DmsFilePermissionsController extends BaseController
{
    @Autowired
    private IDmsFilePermissionsService dmsFilePermissionsService;

    /**
     * 查询文档权限列表
     */
    @PreAuthorize("@ss.hasPermi('system:permissions:list')")
    @GetMapping("/list")
    public TableDataInfo list(DmsFilePermissions dmsFilePermissions)
    {
        startPage();
        List<DmsFilePermissions> list = dmsFilePermissionsService.selectDmsFilePermissionsList(dmsFilePermissions);
        return getDataTable(list);
    }

    /**
     * 导出文档权限列表
     */
    @PreAuthorize("@ss.hasPermi('system:permissions:export')")
    @Log(title = "文档权限", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DmsFilePermissions dmsFilePermissions)
    {
        List<DmsFilePermissions> list = dmsFilePermissionsService.selectDmsFilePermissionsList(dmsFilePermissions);
        ExcelUtil<DmsFilePermissions> util = new ExcelUtil<DmsFilePermissions>(DmsFilePermissions.class);
        util.exportExcel(response, list, "文档权限数据");
    }

    /**
     * 获取文档权限详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:permissions:query')")
    @GetMapping(value = "/{fileId}")
    public AjaxResult getInfo(@PathVariable("fileId") String fileId)
    {
    	List<DmsFilePermissions> list = dmsFilePermissionsService.selectDmsFilePermissionsByFileId(fileId);
        return success(list);
    }

    /**
     * 新增文档权限
     */
    @PreAuthorize("@ss.hasPermi('system:permissions:add')")
    @Log(title = "文档权限", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DmsFilePermissions dmsFilePermissions)
    {
        return toAjax(dmsFilePermissionsService.insertDmsFilePermissions(dmsFilePermissions));
    }

    /**
     * 修改文档权限
     */
    @PreAuthorize("@ss.hasPermi('system:permissions:edit')")
    @Log(title = "文档权限", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DmsFilePermissions dmsFilePermissions)
    {
        return toAjax(dmsFilePermissionsService.updateDmsFilePermissions(dmsFilePermissions));
    }

    /**
     * 删除文档权限
     */
    @PreAuthorize("@ss.hasPermi('system:permissions:remove')")
    @Log(title = "文档权限", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fileId}")
    public AjaxResult remove(@PathVariable String fileId)
    {
        return toAjax(dmsFilePermissionsService.deleteDmsFilePermissionsByFileId(fileId));
    }
}

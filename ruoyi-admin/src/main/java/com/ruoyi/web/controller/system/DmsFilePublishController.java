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
import com.ruoyi.system.domain.DmsFilePermissions;
import com.ruoyi.system.service.IDmsFilePublishService;
import com.ruoyi.system.service.IDmsFileInfoService;
import com.ruoyi.system.service.IDmsFilePermissionsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 定稿Controller
 * 
 * @author HYZ
 * @date 2024-02-25
 */
@RestController
@RequestMapping("/system/publish")
public class DmsFilePublishController extends BaseController
{
    @Autowired
    private IDmsFilePublishService dmsFilePublishService;
    @Autowired
    private IDmsFileInfoService dmsFileInfoService;
    @Autowired
    private IDmsFilePermissionsService dmsFilePermissionsService;
    /**
     * 查询定稿列表
     */
    @PreAuthorize("@ss.hasPermi('system:publish:list')")
    @GetMapping("/list")
    public TableDataInfo list(DmsFilePublish dmsFilePublish)
    {
        startPage();
        List<DmsFilePublish> list = dmsFilePublishService.selectDmsFilePublishList(dmsFilePublish);
        return getDataTable(list);
    }

    /**
     * 导出定稿列表
     */
    @PreAuthorize("@ss.hasPermi('system:publish:export')")
    @Log(title = "定稿", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DmsFilePublish dmsFilePublish)
    {
        List<DmsFilePublish> list = dmsFilePublishService.selectDmsFilePublishList(dmsFilePublish);
        ExcelUtil<DmsFilePublish> util = new ExcelUtil<DmsFilePublish>(DmsFilePublish.class);
        util.exportExcel(response, list, "定稿数据");
    }

    /**
     * 获取定稿详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:publish:query')")
    @GetMapping(value = "/{fileId}")
    public AjaxResult getInfo(@PathVariable("fileId") String fileId)
    { 
    	DmsFilePublish result = dmsFilePublishService.selectDmsFilePublishByFileId(fileId);
        if (result != null) {
            // 查询结果不为空，返回成功响应
            return success(result);
        } else {
            // 查询结果为空，返回自定义的失败响应，这里假设你的 AjaxResult 类有一个适当的构造函数
            return AjaxResult.error("未找到对应的定稿意见");
        }
    }
    /**
     * 新增定稿
     */
    @PreAuthorize("@ss.hasPermi('system:publish:add')")
    @Log(title = "定稿", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DmsFilePublish dmsFilePublish)
    {
        return toAjax(dmsFilePublishService.insertDmsFilePublish(dmsFilePublish));
    }

    /**
     * 修改定稿
     */
    @PreAuthorize("@ss.hasPermi('system:publish:edit')")
    @Log(title = "定稿", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DmsFilePublish dmsFilePublish)
    {
    	// 更新定稿结果
    	int updatePublishResult =dmsFilePublishService.updateDmsFilePublish(dmsFilePublish);
    	// 如果is_passsed是待发布(1)，则重置文档状态为待发布(2)
    	// 如果is_passsed是通过(2)，则修改文档状态为已发布(3) 同时更新文档权限表（先删除所有权限，后新建权限）
    	// 如果is_passed是需修改(3),则修改文档状态为需修改(4)
    	Long IsPassed = dmsFilePublish.getIsPassed();
        if (IsPassed == 2L) {
        	long fileStatus = 3L;
        	// 修改文档状态为已发布（3）
        	int updateFileStatusResult = dmsFileInfoService.updateDmsFileStatus(dmsFilePublish.getFileId(), fileStatus);
        	// 删除已有文档权限
        	int delFilePermissionsResult = dmsFilePermissionsService.deleteDmsFilePermissionsByFileId(dmsFilePublish.getFileId());
        	// 创建文档权限表新增信息
        	DmsFilePermissions dmsFilePermissions = new DmsFilePermissions ();
        	dmsFilePermissions.setFileId(dmsFilePublish.getFileId());
        	Long[] deptIds = dmsFilePublish.getDeptIds();
        	for (Long deptId : deptIds) {
	        	dmsFilePermissions.setDeptId(deptId);
		        int insertFilePermissionsResult = dmsFilePermissionsService.insertDmsFilePermissions(dmsFilePermissions);
        	}
        	return AjaxResult.success("定稿成功");
        }else if (IsPassed == 3L) {
        	long fileStatus = 4L;
        	int updateFileStatusResult = dmsFileInfoService.updateDmsFileStatus(dmsFilePublish.getFileId(), fileStatus);
        	return AjaxResult.success("定稿成功");
        }else if (IsPassed == 1L) {
        	long fileStatus = 2L;
        	int updateFileStatusResult = dmsFileInfoService.updateDmsFileStatus(dmsFilePublish.getFileId(), fileStatus);
        	return AjaxResult.success("定稿成功");
        }else {
        	return AjaxResult.error("未知的定稿结果");
        }
    }

    /**
     * 删除定稿
     */
    @PreAuthorize("@ss.hasPermi('system:publish:remove')")
    @Log(title = "定稿", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fileIds}")
    public AjaxResult remove(@PathVariable String[] fileIds)
    {
        return toAjax(dmsFilePublishService.deleteDmsFilePublishByFileIds(fileIds));
    }
}

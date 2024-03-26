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
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.DmsFileInfo;
import com.ruoyi.system.domain.DmsFilePublish;
import com.ruoyi.system.service.IDmsFileInfoService;
import com.ruoyi.system.service.IDmsFileReviewService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.system.domain.DmsFileReview;
import com.ruoyi.system.service.IDmsFilePublishService;


/**
 * 文件信息Controller
 * 
 * @author HYZ
 * @date 2024-01-31
 */
@RestController
@RequestMapping("/system/dmsfileupload")
public class DmsFileInfoController extends BaseController
{
    @Autowired
    private IDmsFileInfoService dmsFileInfoService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IDmsFileReviewService dmsFileReviewService;
    @Autowired
    private IDmsFilePublishService dmsFilePublishService;
    /**
     * 查询文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:dmsfileupload:list')")
    @GetMapping("/list")
    public TableDataInfo list(DmsFileInfo dmsFileInfo)
    {
    	startPage();
    	Long querykind = dmsFileInfo.getQuerykind();
        // 判断querykind是否为1，若是则为文档浏览的查询
        if(querykind == 1L) {
        	// 根据用户id查询归属部门ID，用户角色ID，赋给dmsFileInfo
        	Long userId = dmsFileInfo.getQueryuserId();
        	SysUser sysUser = userService.selectUserById(userId);
        	Long userDept = sysUser.getDeptId();
        	List<SysRole> roles = sysUser.getRoles();
        	Long userRole = null;
        	if (roles != null && !roles.isEmpty()) {
        	    userRole = roles.get(0).getRoleId();
        	}
        	dmsFileInfo.setQueryuserDept(userDept);
        	dmsFileInfo.setQueryuserRole(userRole);
        	List<DmsFileInfo> list = dmsFileInfoService.selectDmsFileInfoListByPremission(dmsFileInfo);
        	return getDataTable(list);
        }else {
	        List<DmsFileInfo> list = dmsFileInfoService.selectDmsFileInfoList(dmsFileInfo);
	        return getDataTable(list);
        }
    }

    /**
     * 导出文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:dmsfileupload:export')")
    @Log(title = "文件信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DmsFileInfo dmsFileInfo)
    {
        List<DmsFileInfo> list = dmsFileInfoService.selectDmsFileInfoList(dmsFileInfo);
        ExcelUtil<DmsFileInfo> util = new ExcelUtil<DmsFileInfo>(DmsFileInfo.class);
        util.exportExcel(response, list, "文件信息数据");
    }

    /**
     * 获取文件信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:dmsfileupload:query')")
    @GetMapping(value = "/{fileId}")
    public AjaxResult getInfo(@PathVariable("fileId") String fileId)
    {
        return success(dmsFileInfoService.selectDmsFileInfoByFileId(fileId));
    }

    /**
     * 新增文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:dmsfileupload:add')")
    @Log(title = "文件信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DmsFileInfo dmsFileInfo)
    {
    	// 新增文件信息->新建评审信息
    	dmsFileInfoService.insertDmsFileInfo(dmsFileInfo);
    	//提取reviewID,构建reviewform,循环新建评审信息
    	List<Long> reviewerIds = dmsFileInfo.getReviewerIds();
    	DmsFileReview dmsFileReview = new DmsFileReview();
    	dmsFileReview.setFileId(dmsFileInfo.getFileId());
    	dmsFileReview.setIsPassed(1L);
    	for (Long reviewerId : reviewerIds) {
	    	dmsFileReview.setReviewerId(reviewerId);
	    	dmsFileReviewService.insertDmsFileReview(dmsFileReview);
    	}
    	return AjaxResult.success("新建文件成功");
    }

    /**
     * 修改文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:dmsfileupload:edit')")
    @Log(title = "文件信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DmsFileInfo dmsFileInfo)
    {
    	// 修改文件信息->删除评审信息->删除定稿信息—>新建评审信息
    	dmsFileInfoService.updateDmsFileInfo(dmsFileInfo);
    	// 删除文件ID对应评审信息
    	dmsFileReviewService.deleteDmsFileReviewByFileId(dmsFileInfo.getFileId());
    	// 删除文件ID对应定稿信息
    	dmsFilePublishService.deleteDmsFilePublishByFileId(dmsFileInfo.getFileId());
    	//提取reviewID,构建reviewform,循环新建评审信息
    	List<Long> reviewerIds = dmsFileInfo.getReviewerIds();
    	DmsFileReview dmsFileReview = new DmsFileReview();
    	dmsFileReview.setFileId(dmsFileInfo.getFileId());
    	dmsFileReview.setIsPassed(1L);
    	for (Long reviewerId : reviewerIds) {
	    	dmsFileReview.setReviewerId(reviewerId);
	    	dmsFileReviewService.insertDmsFileReview(dmsFileReview);
    	}
    	return AjaxResult.success("修改文件成功");
    }

    /**
     * 删除单个文件信息及文件
     */
    @PreAuthorize("@ss.hasPermi('system:dmsfileupload:remove')")
    @Log(title = "文件信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fileId}")
    public AjaxResult remove(@PathVariable("fileId") String fileId)
    {	
    	
    	// 获取文件信息列表
    	DmsFileInfo dmsFileInfo = dmsFileInfoService.selectDmsFileInfoByFileId(fileId);

        //文件磁盘路径
        String localPath = RuoYiConfig.getProfile();
        //映射路径
        String filePath = dmsFileInfo.getFilePath();
        // 删除路径
        String deletaPath = localPath + StringUtils.substringAfter(filePath, Constants.RESOURCE_PREFIX );
        // 调用文件删除方法，删除实际文件
        
        //System.out.println("Deleting file with path: " + deletaPath);
        FileUtils.deleteFile(deletaPath);
        
     // 删除文件信息记录->删除评审信息->删除定稿信息
    	dmsFileInfoService.deleteDmsFileInfoByFileId(dmsFileInfo.getFileId());
    	// 删除文件ID对应评审信息
    	dmsFileReviewService.deleteDmsFileReviewByFileId(dmsFileInfo.getFileId());
    	// 删除文件ID对应定稿信息
    	dmsFilePublishService.deleteDmsFilePublishByFileId(dmsFileInfo.getFileId());
    	return AjaxResult.success("删除成功");
    }
}

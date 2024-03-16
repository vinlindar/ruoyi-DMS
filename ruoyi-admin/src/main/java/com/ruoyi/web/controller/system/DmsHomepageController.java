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
import com.ruoyi.system.domain.DmsDownloadRecords;
import com.ruoyi.system.domain.DmsFileInfo;
import com.ruoyi.system.domain.DmsFilePermissions;
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
import com.ruoyi.system.service.IDmsDownloadRecordsService;
import com.ruoyi.system.domain.DmsDeptfileNum;
import com.ruoyi.system.domain.DmsBasicNum;

/**
 * 首页Controller
 * 
 * @author HYZ
 * @date 2024-03-15
 */
@RestController
@RequestMapping("/system/homepage")
public class DmsHomepageController extends BaseController
{
    @Autowired
    private IDmsFilePublishService dmsFilePublishService;
    @Autowired
    private IDmsDownloadRecordsService dmsDownloadRecordsService;
    @Autowired
    private IDmsFileInfoService dmsFileInfoService;
    /**
     * 获取用户的已发布文档、待审阅、待定稿、待修改数量和信息
     */
    @PreAuthorize("@ss.hasPermi('system:homepage:query')")
    @GetMapping(value = "/{userid}")
    public AjaxResult getInfo(@PathVariable("userid") Long userid)
    {
    	// 查询文件信息表,获得该用户已发布文档、待审阅文档、待定稿文档、待修改文档数量
    	DmsBasicNum result = dmsFileInfoService.selectBacisnumByuserId(userid);
    	System.out.println("返回的 JSON 格式报文：" + result.toString());
    	 return success(result);
    }
    
    /**
     * 查询最新发布的文件信息列表（10个）
     */
    @PreAuthorize("@ss.hasPermi('system:homepage:list')")
    @GetMapping("/list")
    public TableDataInfo list( )
    {
    	// 查询文档发布表，获得最新发布的10条文档信息
    	List<DmsFilePublish> publishlist = dmsFilePublishService.selectDmsFilePublishlatestList();
    	return getDataTable(publishlist);
    }
    
    /**
     * 查询最多下载的文档信息（10个）
     */
    @PreAuthorize("@ss.hasPermi('system:homepage:popularfile')")
    @GetMapping("/popularfile")
    public TableDataInfo  popularfile( )
    {
    	// 查询文档下载列表，获得最多下载的10条文档信息
    	List<DmsDownloadRecords> popularfiles = dmsDownloadRecordsService.selectMostPopularFile();
    	return getDataTable(popularfiles);
    }
    /**
     * 获取各个部门已发布的文档数量
     */
    @PreAuthorize("@ss.hasPermi('system:homepage:deptfilenum')")
    @GetMapping("/deptfilenum")
    public TableDataInfo  deptfilenum( )
    {
    	// 查询文件信息表，获取各个部门已发布的文档数量
    	List<DmsDeptfileNum> deptfilenum= dmsFileInfoService.selectdeptfilenum();
    	return getDataTable(deptfilenum);
    }

}



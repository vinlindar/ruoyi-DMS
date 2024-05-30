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
import com.ruoyi.system.domain.DmsfileNumbyMonth;
import com.ruoyi.system.domain.DmsfileNumbyYear;
import com.ruoyi.system.service.IDmsFilePublishService;
import com.ruoyi.system.service.IDmsDownloadRecordsService;
import com.ruoyi.system.domain.DmsDeptfileNum;
import com.ruoyi.system.domain.DmsBasicNum;
import com.ruoyi.system.domain.DmsClassifiedfileNum;

/**
 * 首页Controller
 * 
 * @author HYZ
 * @date 2024-03-15
 */
@RestController
@RequestMapping("/system/filestatistics")
public class DmsFileStatisticsController extends BaseController
{
    @Autowired
    private IDmsDownloadRecordsService dmsDownloadRecordsService;
    @Autowired
    private IDmsFileInfoService dmsFileInfoService;
    
    /**
     * 获取各个团队已发布的文档数量
     */

    @GetMapping("/deptfilenum")
    public TableDataInfo  deptfilenum( )
    {
    	// 查询文件信息表，获取各个部门已发布的文档数量
    	List<DmsDeptfileNum> deptfilenum= dmsFileInfoService.selectdeptfilenum();
    	return getDataTable(deptfilenum);
    }
    /**
     * 获取分类文件数
     */

    @GetMapping("/classifiedfilenum")
    public TableDataInfo  classifiedfilenum( )
    {
    	// 查询文件信息表，获取各分类下的已发布的文档数量
    	List<DmsClassifiedfileNum> classifiedfilenum= dmsFileInfoService.selectclassifiedfilenum();
    	return getDataTable(classifiedfilenum);
    }
    /**
    * 获取各年的已发布文件数
     */
    @GetMapping("/yearfilenum")
    public TableDataInfo yearfilenum( )
    {
    	List<DmsfileNumbyYear> yearfilenum = dmsFileInfoService.selectfilenumbyYear();
    	return getDataTable(yearfilenum);
    }
    /**
    * 获取近12个月各个月的已发布文件数
     */
    @GetMapping("/monthfilenum")
    public TableDataInfo monthfilenum( )
    {
    	List<DmsfileNumbyMonth> monthfilenum = dmsFileInfoService.selectfilenumbyMonth();
    	return getDataTable(monthfilenum);
    }
    /**
    * 获取各团队下载文件数
     */
    @GetMapping("/detpdownloadfilenum")
    public TableDataInfo  detpdownloadfilenum( )
    {
    	List<DmsDeptfileNum> detpdownloadfilenum = dmsDownloadRecordsService.selectdetpdownloadfilenum();
    	return getDataTable(detpdownloadfilenum);
    }
    
    /**
    * 获取各分类下载文件数
     */
    @GetMapping("/classifieddownloadfilenum")
    public TableDataInfo  classifieddownloadfilenum( )
    {
    	List<DmsClassifiedfileNum> classifieddownloadfilenum=dmsDownloadRecordsService.selectclassifieddownloadfilenum();
    	return getDataTable(classifieddownloadfilenum);
    }

}



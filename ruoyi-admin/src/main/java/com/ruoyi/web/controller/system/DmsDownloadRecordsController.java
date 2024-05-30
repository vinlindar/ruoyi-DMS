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
import com.ruoyi.system.domain.DmsDownloadRecords;
import com.ruoyi.system.service.IDmsDownloadRecordsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 下载记录Controller
 * 
 * @author HYZ
 * @date 2024-03-12
 */
@RestController
@RequestMapping("/system/records")
public class DmsDownloadRecordsController extends BaseController
{
    @Autowired
    private IDmsDownloadRecordsService dmsDownloadRecordsService;

    /**
     * 查询下载记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:records:list')")
    @GetMapping("/list")
    public TableDataInfo list(DmsDownloadRecords dmsDownloadRecords)
    {
        startPage();
        List<DmsDownloadRecords> list = dmsDownloadRecordsService.selectDmsDownloadRecordsList(dmsDownloadRecords);
        return getDataTable(list);
    }

    /**
     * 导出下载记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:records:export')")
    @Log(title = "下载记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DmsDownloadRecords dmsDownloadRecords)
    {
        List<DmsDownloadRecords> list = dmsDownloadRecordsService.selectDmsDownloadRecordsList(dmsDownloadRecords);
        ExcelUtil<DmsDownloadRecords> util = new ExcelUtil<DmsDownloadRecords>(DmsDownloadRecords.class);
        util.exportExcel(response, list, "下载记录数据");
    }

    /**
     * 获取下载记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:records:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(dmsDownloadRecordsService.selectDmsDownloadRecordsById(id));
    }
    
    /**
     * 获取文件下载次数
     */
    @PreAuthorize("@ss.hasPermi('system:records:query')")
    @GetMapping(value = "/fileId/{fileId}")
    public AjaxResult getInfo1(@PathVariable("fileId") Long fileId)
    {
        return success(dmsDownloadRecordsService.selectDownloadNumbyFileid(fileId));
    }
    
    /**
     * 新增下载记录
     */
    @Log(title = "下载记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DmsDownloadRecords dmsDownloadRecords)
    {
        return toAjax(dmsDownloadRecordsService.insertDmsDownloadRecords(dmsDownloadRecords));
    }

    /**
     * 修改下载记录
     */
    @PreAuthorize("@ss.hasPermi('system:records:edit')")
    @Log(title = "下载记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DmsDownloadRecords dmsDownloadRecords)
    {
        return toAjax(dmsDownloadRecordsService.updateDmsDownloadRecords(dmsDownloadRecords));
    }

    /**
     * 删除下载记录
     */
    @PreAuthorize("@ss.hasPermi('system:records:remove')")
    @Log(title = "下载记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dmsDownloadRecordsService.deleteDmsDownloadRecordsByIds(ids));
    }
}

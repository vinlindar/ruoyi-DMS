package com.ruoyi.web.controller.dms;

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
import com.ruoyi.dms.domain.DmsFile;
import com.ruoyi.dms.service.IDmsFileService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 文件上传Controller
 * 
 * @author HYZ
 * @date 2024-01-30
 */
@RestController
@RequestMapping("/dms/fileupload")
public class DmsFileController extends BaseController
{
    @Autowired
    private IDmsFileService dmsFileService;

    /**
     * 查询文件上传列表
     */
    @PreAuthorize("@ss.hasPermi('dms:fileupload:list')")
    @GetMapping("/list")
    public TableDataInfo list(DmsFile dmsFile)
    {
        startPage();
        List<DmsFile> list = dmsFileService.selectDmsFileList(dmsFile);
        return getDataTable(list);
    }

    /**
     * 导出文件上传列表
     */
    @PreAuthorize("@ss.hasPermi('dms:fileupload:export')")
    @Log(title = "文件上传", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DmsFile dmsFile)
    {
        List<DmsFile> list = dmsFileService.selectDmsFileList(dmsFile);
        ExcelUtil<DmsFile> util = new ExcelUtil<DmsFile>(DmsFile.class);
        util.exportExcel(response, list, "文件上传数据");
    }

    /**
     * 获取文件上传详细信息
     */
    @PreAuthorize("@ss.hasPermi('dms:fileupload:query')")
    @GetMapping(value = "/{FileID}")
    public AjaxResult getInfo(@PathVariable("FileID") Long FileID)
    {
        return success(dmsFileService.selectDmsFileByFileID(FileID));
    }

    /**
     * 新增文件上传
     */
    @PreAuthorize("@ss.hasPermi('dms:fileupload:add')")
    @Log(title = "文件上传", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DmsFile dmsFile)
    {
        return toAjax(dmsFileService.insertDmsFile(dmsFile));
    }

    /**
     * 修改文件上传
     */
    @PreAuthorize("@ss.hasPermi('dms:fileupload:edit')")
    @Log(title = "文件上传", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DmsFile dmsFile)
    {
        return toAjax(dmsFileService.updateDmsFile(dmsFile));
    }

    /**
     * 删除文件上传
     */
    @PreAuthorize("@ss.hasPermi('dms:fileupload:remove')")
    @Log(title = "文件上传", businessType = BusinessType.DELETE)
	@DeleteMapping("/{FileIDs}")
    public AjaxResult remove(@PathVariable Long[] FileIDs)
    {
        return toAjax(dmsFileService.deleteDmsFileByFileIDs(FileIDs));
    }
}

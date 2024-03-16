package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 下载记录对象 dms_download_records
 * 
 * @author HYZ
 * @date 2024-03-12
 */
public class DmsDownloadRecords extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 文件ID */
    @Excel(name = "文件ID")
    private String fileId;

    /** 文件名 */
    @Excel(name = "文件名")
    private String fileName;

    /** 归属部门ID */
    private Long deptId;

    /** 归属部门 */
    @Excel(name = "归属部门")
    private String belongteam;

    /** 下载人ID */
    private Long downloadUserid;

    /** 下载人 */
    @Excel(name = "下载人")
    private String downloadUser;

    /** 下载时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "下载时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date downloadTime;
    /** 下载数量 */
    @Excel(name = "下载量")
    private Long downloadCount;
   

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFileId(String fileId) 
    {
        this.fileId = fileId;
    }

    public String getFileId() 
    {
        return fileId;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }
    public void setBelongteam(String belongteam) 
    {
        this.belongteam = belongteam;
    }

    public String getBelongteam() 
    {
        return belongteam;
    }
    public void setDownloadUserid(Long downloadUserid) 
    {
        this.downloadUserid = downloadUserid;
    }

    public Long getDownloadUserid() 
    {
        return downloadUserid;
    }
    public void setDownloadUser(String downloadUser) 
    {
        this.downloadUser = downloadUser;
    }

    public String getDownloadUser() 
    {
        return downloadUser;
    }
    public void setDownloadTime(Date downloadTime) 
    {
        this.downloadTime = downloadTime;
    }

    public Date getDownloadTime() 
    {
        return downloadTime;
    }

    public void setDownloadCount(Long downloadCount) 
    {
        this.downloadCount = downloadCount;
    }

    public Long getDownloadCount() 
    {
        return downloadCount;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("fileId", getFileId())
            .append("fileName", getFileName())
            .append("deptId", getDeptId())
            .append("belongteam", getBelongteam())
            .append("downloadUserid", getDownloadUserid())
            .append("downloadUser", getDownloadUser())
            .append("downloadTime", getDownloadTime())
            .toString();
    }
}

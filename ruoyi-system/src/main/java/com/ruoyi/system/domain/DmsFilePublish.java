package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 定稿对象 dms_file_publish
 * 
 * @author HYZ
 * @date 2024-02-25
 */
public class DmsFilePublish extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 文件ID */
    private String fileId;

    /** 定稿人ID */
    private Long publishId;

    /** 定稿意见 */
    @Excel(name = "定稿意见")
    private String comment;

    /** 定稿结果 */
    @Excel(name = "定稿结果")
    private Long isPassed;

    /** 定稿时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "定稿时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;
    
    /** 文件名 */
    @Excel(name = "文件名")
    private String fileName;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    /** 作者 */
    @Excel(name = "作者")
    private String author;

    /** 审稿人 */
    @Excel(name = "审稿人")
    private String reviewer;

    /** 文件类型 */
    @Excel(name = "文件类型")
    private Long fileType;

    /** 文件大小 */
    @Excel(name = "文件大小")
    private String fileSize;

    /** 文件状态 */
    @Excel(name = "文件状态")
    private Long fileStatus;

    /** 归属团队 */
    @Excel(name = "归属团队")
    private String belongteam;

    /** 文件描述 */
    @Excel(name = "文件描述")
    private String description;
    
    /** 文件权限 */
    @Excel(name = "文件部门权限")
    private Long[] deptIds;
    

    public void setFileId(String fileId) 
    {
        this.fileId = fileId;
    }

    public String getFileId() 
    {
        return fileId;
    }
    public void setPublishId(Long publishId) 
    {
        this.publishId = publishId;
    }

    public Long getPublishId() 
    {
        return publishId;
    }
    public void setComment(String comment) 
    {
        this.comment = comment;
    }

    public String getComment() 
    {
        return comment;
    }
    public void setIsPassed(Long isPassed) 
    {
        this.isPassed = isPassed;
    }

    public Long getIsPassed() 
    {
        return isPassed;
    }
    public void setPublishTime(Date publishTime) 
    {
        this.publishTime = publishTime;
    }

    public Date getPublishTime() 
    {
        return publishTime;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileName() 
    {
        return fileName;
    }
    public void setFilePath(String filePath) 
    {
        this.filePath = filePath;
    }
    public String getFilePath() 
    {
        return filePath;
    }
    public void setAuthor(String author) 
    {
        this.author = author;
    }
    public String getAuthor() 
    {
        return author;
    }
    public void setReviewer(String reviewer) 
    {
        this.reviewer = reviewer;
    }
    public String getReviewer() 
    {
        return reviewer;
    }
    public void setFileType(Long fileType) 
    {
        this.fileType = fileType;
    }
    public Long getFileType() 
    {
        return fileType;
    }
    public void setFileSize(String fileSize) 
    {
        this.fileSize = fileSize;
    }
    public String getFileSize() 
    {
        return fileSize;
    }
    public void setFileStatus(Long fileStatus) 
    {
        this.fileStatus = fileStatus;
    }
    public Long getFileStatus() 
    {
        return fileStatus;
    }
    public void setBelongteam(String belongteam) 
    {
        this.belongteam = belongteam;
    }
    public String getBelongteam() 
    {
        return belongteam;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }
    
    public String getDescription() 
    {
        return description;
    }
    public Long[] getDeptIds() 
    {
        return deptIds;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fileId", getFileId())
            .append("publishId", getPublishId())
            .append("comment", getComment())
            .append("isPassed", getIsPassed())
            .append("publishTime", getPublishTime())
            .toString();
    }
}

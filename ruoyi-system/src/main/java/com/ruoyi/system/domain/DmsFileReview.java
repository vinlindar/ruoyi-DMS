package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 评阅对象 dms_file_review
 * 
 * @author ruoyi
 * @date 2024-02-18
 */
public class DmsFileReview extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 文件ID */
    private String fileId;

    /** 评阅人ID */
    private Long reviewerId;

    /** 评阅意见 */
    @Excel(name = "评阅意见")
    private String comment;

    /** 是否通过 */
    @Excel(name = "是否通过")
    private Integer isPassed;

    /** 评阅时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "评阅时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewTime;
    
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
    

    public void setFileId(String fileId) 
    {
        this.fileId = fileId;
    }

    public String getFileId() 
    {
        return fileId;
    }
    public void setReviewerId(Long reviewerId) 
    {
        this.reviewerId = reviewerId;
    }

    public Long getReviewerId() 
    {
        return reviewerId;
    }
    public void setComment(String comment) 
    {
        this.comment = comment;
    }

    public String getComment() 
    {
        return comment;
    }
    public void setIsPassed(Integer isPassed) 
    {
        this.isPassed = isPassed;
    }

    public Integer getIsPassed() 
    {
        return isPassed;
    }
    public void setReviewTime(Date reviewTime) 
    {
        this.reviewTime = reviewTime;
    }

    public Date getReviewTime() 
    {
        return reviewTime;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fileId", getFileId())
            .append("reviewerId", getReviewerId())
            .append("comment", getComment())
            .append("isPassed", getIsPassed())
            .append("reviewTime", getReviewTime())
            .toString();
    }
}

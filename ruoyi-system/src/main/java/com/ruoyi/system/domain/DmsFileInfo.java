package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 文件信息对象 dms_file_info
 * 
 * @author HYZ
 * @date 2024-01-31
 */
public class DmsFileInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 文件ID */
    @Excel(name = "文件ID")
    private String fileId;

    /** 文件名 */
    @Excel(name = "文件名")
    private String fileName;
    
    /** 关键字 */
    @Excel(name = "文件名")
    private String keywords;

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
    
    /** 定稿人ID */
    @Excel(name = "定稿人ID")
    private Long publishId;
    
    /** 定稿时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "定稿时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;
    
    /** 评阅人ID列表 */
    @Excel(name = "评阅人ID列表")
    private List<Long> reviewerIds;
    
    /** 查询种类 */
    @Excel(name = "查询来源")
    private Long querykind;
    
    /** 当前用户Id */
    @Excel(name = "查询用户ID")
    private Long queryuserId;
    
    /** 当前用户部门 */
    @Excel(name = "查询用户部门ID")
    private Long queryuserDept;
    
    /** 当前用户角色 */
    @Excel(name = "查询用户角色ID")
    private Long queryuserRole;

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
    
    public void setKeywords(String keywords) 
    {
        this.keywords = keywords;
    }

    public String getKeywords() 
    {
        return keywords;
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
    public void setPublishId(Long publishId) 
    {
        this.publishId = publishId;
    }

    public Long getPublishId() 
    {
        return publishId;
    }
    
    public List<Long> getReviewerIds() {
        return reviewerIds;
    }

    public void setReviewerIds(List<Long> reviewerIds) {
        this.reviewerIds = reviewerIds;
    }
    

    public Long getQuerykind() 
    {
        return querykind;
    }
    public void setQuerykind(Long querykind) 
    {
        this.querykind = querykind;
    }
    
    public Long getQueryuserId() 
    {
        return queryuserId;
    }
    public void setQueryuserId(Long queryuserId) 
    {
        this.queryuserId = queryuserId;
    }
    public void setQueryuserDept(Long queryuserDept) 
    {
        this.queryuserDept = queryuserDept;
    }
    
    public Long getQueryuserDept() 
    {
        return queryuserDept;
    }
    public Long getQueryuserRole() 
    {
        return queryuserRole;
    }
	public void setQueryuserRole(Long userRole) {
		this.queryuserRole = userRole;
	}
    public void setPublishTime(Date publishTime) 
    {
        this.publishTime = publishTime;
    }

    public Date getPublishTime() 
    {
        return publishTime;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fileId", getFileId())
            .append("fileName", getFileName())
            .append("keywords",getKeywords())
            .append("filePath", getFilePath())
            .append("author", getAuthor())
            .append("reviewer", getReviewer())
            .append("fileType", getFileType())
            .append("fileSize", getFileSize())
            .append("fileStatus", getFileStatus())
            .append("belongteam", getBelongteam())
            .append("description", getDescription())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("publishId", getPublishId())
            .append("querykind", getQuerykind())
            .append("queryuserId", getQueryuserId())
            .append("queryuserDept", getQueryuserDept())
            .append("queryuserRole", getQueryuserRole())
            .toString();
    }
}

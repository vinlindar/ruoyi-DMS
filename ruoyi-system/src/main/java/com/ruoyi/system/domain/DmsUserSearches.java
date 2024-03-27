package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户自定义搜索对象 dms_user_searches
 * 
 * @author HYZ
 * @date 2024-03-27
 */
public class DmsUserSearches extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 文件ID */
    @Excel(name = "文件ID")
    private String fileId;

    /** 作者 */
    @Excel(name = "作者")
    private String author;

    /** 定稿人ID */
    @Excel(name = "定稿人ID")
    private Long publishId;

    /** 文件类型 */
    @Excel(name = "文件类型")
    private Long fileType;

    /** 归属团队 */
    @Excel(name = "归属团队")
    private String belongteam;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publishTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setFileId(String fileId) 
    {
        this.fileId = fileId;
    }

    public String getFileId() 
    {
        return fileId;
    }
    public void setAuthor(String author) 
    {
        this.author = author;
    }

    public String getAuthor() 
    {
        return author;
    }
    public void setPublishId(Long publishId) 
    {
        this.publishId = publishId;
    }

    public Long getPublishId() 
    {
        return publishId;
    }
    public void setFileType(Long fileType) 
    {
        this.fileType = fileType;
    }

    public Long getFileType() 
    {
        return fileType;
    }
    public void setBelongteam(String belongteam) 
    {
        this.belongteam = belongteam;
    }

    public String getBelongteam() 
    {
        return belongteam;
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
            .append("id", getId())
            .append("userId", getUserId())
            .append("fileId", getFileId())
            .append("author", getAuthor())
            .append("updateBy", getUpdateBy())
            .append("publishId", getPublishId())
            .append("fileType", getFileType())
            .append("belongteam", getBelongteam())
            .append("publishTime", getPublishTime())
            .toString();
    }
}

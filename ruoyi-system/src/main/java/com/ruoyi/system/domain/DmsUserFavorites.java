package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户收藏文件对象 dms_user_favorites
 * 
 * @author HYZ
 * @date 2024-03-26
 */
public class DmsUserFavorites extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 文件ID */
    @Excel(name = "文件ID")
    private String fileId;

    /** 收藏时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "收藏时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date collectTime;
    
    /** 文件名 */
    @Excel(name = "文件名")
    private String fileName;

    /** 文件类型 */
    @Excel(name = "文件类型")
    private Long fileType;
    
    /** 归属团队 */
    @Excel(name = "归属团队")
    private String belongteam;
    
    /** 定稿时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "定稿时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;
    
    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;
    
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
    public void setCollectTime(Date collectTime) 
    {
        this.collectTime = collectTime;
    }

    public Date getCollectTime() 
    {
        return collectTime;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }
    public String getFileName() 
    {
        return fileName;
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
    public void setFilePath(String filePath) 
    {
        this.filePath = filePath;
    }
    public String getFilePath() 
    {
        return filePath;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("fileId", getFileId())
            .append("collectTime", getCollectTime())
            .toString();
    }
}

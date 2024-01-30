package com.ruoyi.dms.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 文件上传对象 dms_file
 * 
 * @author HYZ
 * @date 2024-01-30
 */
public class DmsFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 文件ID */
    @Excel(name = "文件ID")
    private Long FileID;

    /** 文件名 */
    @Excel(name = "文件名")
    private String FileName;

    /** 文件路径 */
    private String FilePath;

    /** 作者 */
    @Excel(name = "作者")
    private String author;

    /** 审稿人 */
    @Excel(name = "审稿人")
    private String ReviewerID;

    /** 文件类型 */
    @Excel(name = "文件类型")
    private Long FileType;

    /** 文件大小 */
    private Long FileSize;

    /** 文件状态 */
    @Excel(name = "文件状态")
    private Long Status;

    /** 归属团队 */
    @Excel(name = "归属团队")
    private String Team;

    /** 文件描述 */
    private String Description;

    public void setFileID(Long FileID) 
    {
        this.FileID = FileID;
    }

    public Long getFileID() 
    {
        return FileID;
    }
    public void setFileName(String FileName) 
    {
        this.FileName = FileName;
    }

    public String getFileName() 
    {
        return FileName;
    }
    public void setFilePath(String FilePath) 
    {
        this.FilePath = FilePath;
    }

    public String getFilePath() 
    {
        return FilePath;
    }
    public void setAuthor(String author) 
    {
        this.author = author;
    }

    public String getAuthor() 
    {
        return author;
    }
    public void setReviewerID(String ReviewerID) 
    {
        this.ReviewerID = ReviewerID;
    }

    public String getReviewerID() 
    {
        return ReviewerID;
    }
    public void setFileType(Long FileType) 
    {
        this.FileType = FileType;
    }

    public Long getFileType() 
    {
        return FileType;
    }
    public void setFileSize(Long FileSize) 
    {
        this.FileSize = FileSize;
    }

    public Long getFileSize() 
    {
        return FileSize;
    }
    public void setStatus(Long Status) 
    {
        this.Status = Status;
    }

    public Long getStatus() 
    {
        return Status;
    }
    public void setTeam(String Team) 
    {
        this.Team = Team;
    }

    public String getTeam() 
    {
        return Team;
    }
    public void setDescription(String Description) 
    {
        this.Description = Description;
    }

    public String getDescription() 
    {
        return Description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("FileID", getFileID())
            .append("FileName", getFileName())
            .append("FilePath", getFilePath())
            .append("author", getAuthor())
            .append("ReviewerID", getReviewerID())
            .append("FileType", getFileType())
            .append("FileSize", getFileSize())
            .append("Status", getStatus())
            .append("Team", getTeam())
            .append("Description", getDescription())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

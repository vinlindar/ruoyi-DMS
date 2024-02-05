package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 文档评阅对象 dms_file_review
 * 
 * @author HYZ
 * @date 2024-02-05
 */
public class DmsFileReview extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 文件ID */
    @Excel(name = "文件ID")
    private String fileId;

    /** 评阅人ID */
    @Excel(name = "评阅人ID")
    private Long reviewerId;

    /** 评阅意见 */
    @Excel(name = "评阅意见")
    private String comment;

    /** 评阅意见 */
    @Excel(name = "评阅意见")
    private Integer isPassed;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fileId", getFileId())
            .append("reviewerId", getReviewerId())
            .append("comment", getComment())
            .append("isPassed", getIsPassed())
            .toString();
    }
}

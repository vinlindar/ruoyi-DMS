package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.List;

/**
 * 用户的已发布文档、待审阅、待定稿、待修改数量
 * 
 * @author HYZ
 * @date 2024-03-16
 */
public class DmsBasicNum extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 已发布文档数量 */
    @Excel(name = "已发布文档数量")
    private Long PublishNum;

    /** 待审阅文档数量 */
    @Excel(name = "待审阅文档数量")
    private Long waitReviewNum;

    /** 待定稿文档数量 */
    @Excel(name = "待定稿文档数量")
    private Long waitPublishNum;
    
    /** 待修改文档数量 */
    @Excel(name = "待修改文档数量")
    private Long waitModifyNum;
    
    /** 待查看的文档数量（该用户上传的文档待评阅和待定稿数量） */
    @Excel(name = "待修改文档数量")
    private Long waitViewNum;


    public void setPublishNum(Long PublishNum) 
    {
        this.PublishNum= PublishNum;
    }

    public Long getPublishNum() 
    {
        return PublishNum;
    }
    
    public void setwaitReviewNum(Long waitReviewNum) 
    {
        this.waitReviewNum= waitReviewNum;
    }

    public Long getwaitReviewNum() 
    {
        return waitReviewNum;
    }
    
    public void setwaitPublishNum(Long waitPublishNum) 
    {
        this.waitPublishNum= waitPublishNum;
    }

    public Long getwaitPublishNum() 
    {
        return waitPublishNum;
    }
    
    public void setwaitModifyNum(Long waitModifyNum) 
    {
        this.waitModifyNum= waitModifyNum;
    }

    public Long getwaitModifyNum() 
    {
        return waitModifyNum;
    }
    
    public void setwaitViewNum(Long waitViewNum) 
    {
        this.waitViewNum= waitViewNum;
    }

    public Long getwaitViewNum() 
    {
        return waitViewNum;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("PublishNum", getPublishNum() )
            .append("waitReviewNum", getwaitReviewNum())
            .append("waitPublishNum", getwaitPublishNum())
            .append("waitwaitModifyNum", getwaitModifyNum())
            .append("waitwaitViewNum", getwaitModifyNum())
            .toString();
    }
}

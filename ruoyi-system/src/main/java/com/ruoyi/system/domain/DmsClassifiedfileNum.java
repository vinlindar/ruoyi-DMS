package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 部门已发布文件数量
 * 
 * @author HYZ
 * @date 2024-03-16
 */
public class DmsClassifiedfileNum extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 分类ID */
    @Excel(name = "分类ID")
    private Long classifiedId;

    /** 文件数 */
    @Excel(name = "文件数")
    private Long filenum;

    public void setClassifiedId(Long classifiedId) 
    {
        this.classifiedId= classifiedId;
    }

    public Long getClassifiedId() 
    {
        return classifiedId;
    }
    
    public void setFileNum(Long filenum) 
    {
        this.filenum= filenum;
    }

    public Long getFileNum() 
    {
        return filenum;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("classifiedname", getClassifiedId() )
            .append("filenum", getFileNum())
            .toString();
    }
}

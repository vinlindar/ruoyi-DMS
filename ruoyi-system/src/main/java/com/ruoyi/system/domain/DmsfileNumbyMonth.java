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
public class DmsfileNumbyMonth extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 月份 */
    @Excel(name = "月份")
    private String label;

    /** 已发布文件数 */
    @Excel(name = "已发布文件数")
    private Long filenum;

    public void setlabel(String label) 
    {
        this.label= label;
    }

    public String getlabel() 
    {
        return label;
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
            .append("label", getlabel() )
            .append("filenum", getFileNum())
            .toString();
    }
}

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
public class DmsfileNumbyYear extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 年份 */
    @Excel(name = "年份")
    private Long year;

    /** 已发布文件数 */
    @Excel(name = "已发布文件数")
    private Long filenum;

    public void setYear(Long year) 
    {
        this.year= year;
    }

    public Long getYear() 
    {
        return year;
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
            .append("teamname", getYear() )
            .append("filenum", getFileNum())
            .toString();
    }
}

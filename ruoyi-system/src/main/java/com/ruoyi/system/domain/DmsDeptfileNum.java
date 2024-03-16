package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.List;

/**
 * 部门已发布文件数量
 * 
 * @author HYZ
 * @date 2024-03-16
 */
public class DmsDeptfileNum extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 团队名称 */
    @Excel(name = "团队名称")
    private String teamname;

    /** 团队ID */
    @Excel(name = "团队ID")
    private Long teamid;

    /** 已发布文件数 */
    @Excel(name = "已发布文件数")
    private Long filenum;

    public void setTeamName(String teamname) 
    {
        this.teamname= teamname;
    }

    public String getTeamName() 
    {
        return teamname;
    }
    
    public void setTeamId(Long teamid) 
    {
        this.teamid= teamid;
    }

    public Long getTeamId() 
    {
        return teamid;
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
            .append("teamname", getTeamName() )
            .append("teamid", getTeamId())
            .append("filenum", getFileNum())
            .toString();
    }
}

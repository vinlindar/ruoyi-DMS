package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 新闻照片对象 dms_news_images
 * 
 * @author HYZ
 * @date 2024-06-02
 */
public class DmsNewsImages extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 存储路径 */
    @Excel(name = "存储路径")
    private String path;

    /** 照片标题 */
    @Excel(name = "照片标题")
    private String title;

    /** 详细描述 */
    @Excel(name = "详细描述")
    private String description;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date creatTime;

    /** 是否展示 */
    @Excel(name = "是否展示")
    private Integer isShow;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPath(String path) 
    {
        this.path = path;
    }

    public String getPath() 
    {
        return path;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setCreatTime(Date creatTime) 
    {
        this.creatTime = creatTime;
    }

    public Date getCreatTime() 
    {
        return creatTime;
    }
    public void setIsShow(Integer isShow) 
    {
        this.isShow = isShow;
    }

    public Integer getIsShow() 
    {
        return isShow;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("path", getPath())
            .append("title", getTitle())
            .append("description", getDescription())
            .append("creatTime", getCreatTime())
            .append("isShow", getIsShow())
            .toString();
    }
}

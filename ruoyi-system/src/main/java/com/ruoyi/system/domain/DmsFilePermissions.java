package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 文档权限对象 dms_file_permissions
 * 
 * @author ruoyi
 * @date 2024-07-14
 */
public class DmsFilePermissions extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 文件ID */
    @Excel(name = "文件ID")
    private String fileId;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 共享类型 */
    @Excel(name = "共享类型")
    private String shareType;
    
    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;
    
    /** 部门名 */
    @Excel(name = "部门名")
    private String deptName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFileId(String fileId) 
    {
        this.fileId = fileId;
    }

    public String getFileId() 
    {
        return fileId;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setShareType(String shareType) 
    {
        this.shareType = shareType;
    }

    public String getShareType() 
    {
        return shareType;
    }
    public String getUserName() 
    {
    	return userName;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }
    
    public String  getDeptName() 
    {
    	return deptName;
    }
    public void setDeptName(String deptName) 
    {
        this.deptName = deptName;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("fileId", getFileId())
            .append("deptId", getDeptId())
            .append("userId", getUserId())
            .append("shareType", getShareType())
            .toString();
    }
}

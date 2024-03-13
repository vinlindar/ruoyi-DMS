package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DmsFilePermissions;

/**
 * 文档权限Mapper接口
 * 
 * @author hyz
 * @date 2024-03-08
 */
public interface DmsFilePermissionsMapper 
{
    /**
     * 查询文档权限
     * 
     * @param fileId 文档权限主键
     * @return 文档权限
     */
    public List<DmsFilePermissions> selectDmsFilePermissionsByFileId(String fileId);

    /**
     * 查询文档权限列表
     * 
     * @param dmsFilePermissions 文档权限
     * @return 文档权限集合
     */
    public List<DmsFilePermissions> selectDmsFilePermissionsList(DmsFilePermissions dmsFilePermissions);

    /**
     * 新增文档权限
     * 
     * @param dmsFilePermissions 文档权限
     * @return 结果
     */
    public int insertDmsFilePermissions(DmsFilePermissions dmsFilePermissions);

    /**
     * 修改文档权限
     * 
     * @param dmsFilePermissions 文档权限
     * @return 结果
     */
    public int updateDmsFilePermissions(DmsFilePermissions dmsFilePermissions);

    /**
     * 删除文档权限
     * 
     * @param fileId 文档权限主键
     * @return 结果
     */
    public int deleteDmsFilePermissionsByFileId(String fileId);

}

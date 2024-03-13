package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DmsFilePermissionsMapper;
import com.ruoyi.system.domain.DmsFilePermissions;
import com.ruoyi.system.service.IDmsFilePermissionsService;

/**
 * 文档权限Service业务层处理
 * 
 * @author hyz
 * @date 2024-03-08
 */
@Service
public class DmsFilePermissionsServiceImpl implements IDmsFilePermissionsService 
{
    @Autowired
    private DmsFilePermissionsMapper dmsFilePermissionsMapper;

    /**
     * 查询文档权限
     * 
     * @param fileId 文档权限主键
     * @return 文档权限
     */
    @Override
    public List<DmsFilePermissions> selectDmsFilePermissionsByFileId(String fileId)
    {
        return dmsFilePermissionsMapper.selectDmsFilePermissionsByFileId(fileId);
    }

    /**
     * 查询文档权限列表
     * 
     * @param dmsFilePermissions 文档权限
     * @return 文档权限
     */
    @Override
    public List<DmsFilePermissions> selectDmsFilePermissionsList(DmsFilePermissions dmsFilePermissions)
    {
        return dmsFilePermissionsMapper.selectDmsFilePermissionsList(dmsFilePermissions);
    }

    /**
     * 新增文档权限
     * 
     * @param dmsFilePermissions 文档权限
     * @return 结果
     */
    @Override
    public int insertDmsFilePermissions(DmsFilePermissions dmsFilePermissions)
    {
        return dmsFilePermissionsMapper.insertDmsFilePermissions(dmsFilePermissions);
    }

    /**
     * 修改文档权限
     * 
     * @param dmsFilePermissions 文档权限
     * @return 结果
     */
    @Override
    public int updateDmsFilePermissions(DmsFilePermissions dmsFilePermissions)
    {
        return dmsFilePermissionsMapper.updateDmsFilePermissions(dmsFilePermissions);
    }

    /**
     * 删除文档权限信息
     * 
     * @param fileId 文档权限主键
     * @return 结果
     */
    @Override
    public int deleteDmsFilePermissionsByFileId(String fileId)
    {
        return dmsFilePermissionsMapper.deleteDmsFilePermissionsByFileId(fileId);
    }
}

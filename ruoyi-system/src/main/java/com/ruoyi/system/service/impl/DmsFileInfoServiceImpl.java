package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DmsFileInfoMapper;
import com.ruoyi.system.domain.DmsFileInfo;
import com.ruoyi.system.service.IDmsFileInfoService;

/**
 * 文件信息Service业务层处理
 * 
 * @author HYZ
 * @date 2024-01-31
 */
@Service
public class DmsFileInfoServiceImpl implements IDmsFileInfoService 
{
    @Autowired
    private DmsFileInfoMapper dmsFileInfoMapper;

    /**
     * 查询文件信息
     * 
     * @param fileId 文件信息主键
     * @return 文件信息
     */
    @Override
    public DmsFileInfo selectDmsFileInfoByFileId(Long fileId)
    {
        return dmsFileInfoMapper.selectDmsFileInfoByFileId(fileId);
    }

    /**
     * 查询文件信息列表
     * 
     * @param dmsFileInfo 文件信息
     * @return 文件信息
     */
    @Override
    public List<DmsFileInfo> selectDmsFileInfoList(DmsFileInfo dmsFileInfo)
    {
        return dmsFileInfoMapper.selectDmsFileInfoList(dmsFileInfo);
    }

    /**
     * 新增文件信息
     * 
     * @param dmsFileInfo 文件信息
     * @return 结果
     */
    @Override
    public int insertDmsFileInfo(DmsFileInfo dmsFileInfo)
    {
        return dmsFileInfoMapper.insertDmsFileInfo(dmsFileInfo);
    }

    /**
     * 修改文件信息
     * 
     * @param dmsFileInfo 文件信息
     * @return 结果
     */
    @Override
    public int updateDmsFileInfo(DmsFileInfo dmsFileInfo)
    {
        dmsFileInfo.setUpdateTime(DateUtils.getNowDate());
        return dmsFileInfoMapper.updateDmsFileInfo(dmsFileInfo);
    }

    /**
     * 批量删除文件信息
     * 
     * @param fileIds 需要删除的文件信息主键
     * @return 结果
     */
    @Override
    public int deleteDmsFileInfoByFileIds(Long[] fileIds)
    {
        return dmsFileInfoMapper.deleteDmsFileInfoByFileIds(fileIds);
    }

    /**
     * 删除文件信息信息
     * 
     * @param fileId 文件信息主键
     * @return 结果
     */
    @Override
    public int deleteDmsFileInfoByFileId(Long fileId)
    {
        return dmsFileInfoMapper.deleteDmsFileInfoByFileId(fileId);
    }
}

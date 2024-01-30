package com.ruoyi.dms.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.dms.mapper.DmsFileMapper;
import com.ruoyi.dms.domain.DmsFile;
import com.ruoyi.dms.service.IDmsFileService;

/**
 * 文件上传Service业务层处理
 * 
 * @author HYZ
 * @date 2024-01-30
 */
@Service
public class DmsFileServiceImpl implements IDmsFileService 
{
    @Autowired
    private DmsFileMapper dmsFileMapper;

    /**
     * 查询文件上传
     * 
     * @param FileID 文件上传主键
     * @return 文件上传
     */
    @Override
    public DmsFile selectDmsFileByFileID(Long FileID)
    {
        return dmsFileMapper.selectDmsFileByFileID(FileID);
    }

    /**
     * 查询文件上传列表
     * 
     * @param dmsFile 文件上传
     * @return 文件上传
     */
    @Override
    public List<DmsFile> selectDmsFileList(DmsFile dmsFile)
    {
        return dmsFileMapper.selectDmsFileList(dmsFile);
    }

    /**
     * 新增文件上传
     * 
     * @param dmsFile 文件上传
     * @return 结果
     */
    @Override
    public int insertDmsFile(DmsFile dmsFile)
    {
        return dmsFileMapper.insertDmsFile(dmsFile);
    }

    /**
     * 修改文件上传
     * 
     * @param dmsFile 文件上传
     * @return 结果
     */
    @Override
    public int updateDmsFile(DmsFile dmsFile)
    {
        dmsFile.setUpdateTime(DateUtils.getNowDate());
        return dmsFileMapper.updateDmsFile(dmsFile);
    }

    /**
     * 批量删除文件上传
     * 
     * @param FileIDs 需要删除的文件上传主键
     * @return 结果
     */
    @Override
    public int deleteDmsFileByFileIDs(Long[] FileIDs)
    {
        return dmsFileMapper.deleteDmsFileByFileIDs(FileIDs);
    }

    /**
     * 删除文件上传信息
     * 
     * @param FileID 文件上传主键
     * @return 结果
     */
    @Override
    public int deleteDmsFileByFileID(Long FileID)
    {
        return dmsFileMapper.deleteDmsFileByFileID(FileID);
    }
}

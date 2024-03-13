package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DmsDownloadRecordsMapper;
import com.ruoyi.system.domain.DmsDownloadRecords;
import com.ruoyi.system.service.IDmsDownloadRecordsService;

/**
 * 下载记录Service业务层处理
 * 
 * @author HYZ
 * @date 2024-03-12
 */
@Service
public class DmsDownloadRecordsServiceImpl implements IDmsDownloadRecordsService 
{
    @Autowired
    private DmsDownloadRecordsMapper dmsDownloadRecordsMapper;

    /**
     * 查询下载记录
     * 
     * @param id 下载记录主键
     * @return 下载记录
     */
    @Override
    public DmsDownloadRecords selectDmsDownloadRecordsById(Long id)
    {
        return dmsDownloadRecordsMapper.selectDmsDownloadRecordsById(id);
    }

    /**
     * 查询下载记录列表
     * 
     * @param dmsDownloadRecords 下载记录
     * @return 下载记录
     */
    @Override
    public List<DmsDownloadRecords> selectDmsDownloadRecordsList(DmsDownloadRecords dmsDownloadRecords)
    {
        return dmsDownloadRecordsMapper.selectDmsDownloadRecordsList(dmsDownloadRecords);
    }

    /**
     * 新增下载记录
     * 
     * @param dmsDownloadRecords 下载记录
     * @return 结果
     */
    @Override
    public int insertDmsDownloadRecords(DmsDownloadRecords dmsDownloadRecords)
    {
        return dmsDownloadRecordsMapper.insertDmsDownloadRecords(dmsDownloadRecords);
    }

    /**
     * 修改下载记录
     * 
     * @param dmsDownloadRecords 下载记录
     * @return 结果
     */
    @Override
    public int updateDmsDownloadRecords(DmsDownloadRecords dmsDownloadRecords)
    {
        return dmsDownloadRecordsMapper.updateDmsDownloadRecords(dmsDownloadRecords);
    }

    /**
     * 批量删除下载记录
     * 
     * @param ids 需要删除的下载记录主键
     * @return 结果
     */
    @Override
    public int deleteDmsDownloadRecordsByIds(Long[] ids)
    {
        return dmsDownloadRecordsMapper.deleteDmsDownloadRecordsByIds(ids);
    }

    /**
     * 删除下载记录信息
     * 
     * @param id 下载记录主键
     * @return 结果
     */
    @Override
    public int deleteDmsDownloadRecordsById(Long id)
    {
        return dmsDownloadRecordsMapper.deleteDmsDownloadRecordsById(id);
    }
}

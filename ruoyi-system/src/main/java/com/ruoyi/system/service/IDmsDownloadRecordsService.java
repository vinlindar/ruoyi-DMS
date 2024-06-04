package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.system.domain.DmsClassifiedfileNum;
import com.ruoyi.system.domain.DmsDeptfileNum;
import com.ruoyi.system.domain.DmsDownloadRecords;

/**
 * 下载记录Service接口
 * 
 * @author HYZ
 * @date 2024-03-12
 */
public interface IDmsDownloadRecordsService 
{
    /**
     * 查询下载记录
     * 
     * @param id 下载记录主键
     * @return 下载记录
     */
    public DmsDownloadRecords selectDmsDownloadRecordsById(Long id);

    /**
     * 查询下载记录列表
     * 
     * @param dmsDownloadRecords 下载记录
     * @return 下载记录集合
     */
    public List<DmsDownloadRecords> selectDmsDownloadRecordsList(DmsDownloadRecords dmsDownloadRecords);
    
    /**
     * 查询下载次数最多的文件列表（10个）
     * 
     * @param dmsDownloadRecords 下载记录
     * @return 下载记录集合
     */
    public List<DmsDownloadRecords> selectMostPopularFile();
    
    /**
     * 查询文件下载次数
     * 
     * @param fileId
     * @return 
     */
	public int selectDownloadNumbyFileid(Long fileId);
    
    /**
     * 新增下载记录
     * 
     * @param dmsDownloadRecords 下载记录
     * @return 结果
     */
    public int insertDmsDownloadRecords(DmsDownloadRecords dmsDownloadRecords);

    /**
     * 修改下载记录
     * 
     * @param dmsDownloadRecords 下载记录
     * @return 结果
     */
    public int updateDmsDownloadRecords(DmsDownloadRecords dmsDownloadRecords);

    /**
     * 批量删除下载记录
     * 
     * @param ids 需要删除的下载记录主键集合
     * @return 结果
     */
    public int deleteDmsDownloadRecordsByIds(Long[] ids);

    /**
     * 删除下载记录信息
     * 
     * @param id 下载记录主键
     * @return 结果
     */
    public int deleteDmsDownloadRecordsById(Long id);
    
    /**
     * 获取分类下的下载文件数
     * 
     * @param fileId 文件信息主键
     * @return 结果
     */
	public List<DmsClassifiedfileNum> selectclassifieddownloadfilenum();
    
	/**
     * 查询所有部门下载的文档数量
     * 
     * @param fileId 文件信息主键
     * @return 文件信息
     */
	public List<DmsDeptfileNum> selectdetpdownloadfilenum();
}

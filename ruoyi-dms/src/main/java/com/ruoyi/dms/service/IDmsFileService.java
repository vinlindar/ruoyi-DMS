package com.ruoyi.dms.service;

import java.util.List;
import com.ruoyi.dms.domain.DmsFile;

/**
 * 文件上传Service接口
 * 
 * @author HYZ
 * @date 2024-01-30
 */
public interface IDmsFileService 
{
    /**
     * 查询文件上传
     * 
     * @param FileID 文件上传主键
     * @return 文件上传
     */
    public DmsFile selectDmsFileByFileID(Long FileID);

    /**
     * 查询文件上传列表
     * 
     * @param dmsFile 文件上传
     * @return 文件上传集合
     */
    public List<DmsFile> selectDmsFileList(DmsFile dmsFile);

    /**
     * 新增文件上传
     * 
     * @param dmsFile 文件上传
     * @return 结果
     */
    public int insertDmsFile(DmsFile dmsFile);

    /**
     * 修改文件上传
     * 
     * @param dmsFile 文件上传
     * @return 结果
     */
    public int updateDmsFile(DmsFile dmsFile);

    /**
     * 批量删除文件上传
     * 
     * @param FileIDs 需要删除的文件上传主键集合
     * @return 结果
     */
    public int deleteDmsFileByFileIDs(Long[] FileIDs);

    /**
     * 删除文件上传信息
     * 
     * @param FileID 文件上传主键
     * @return 结果
     */
    public int deleteDmsFileByFileID(Long FileID);
}

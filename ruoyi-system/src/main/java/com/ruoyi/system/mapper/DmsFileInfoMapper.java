package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DmsFileInfo;

/**
 * 文件信息Mapper接口
 * 
 * @author HYZ
 * @date 2024-01-31
 */
public interface DmsFileInfoMapper 
{
    /**
     * 查询文件信息
     * 
     * @param fileId 文件信息主键
     * @return 文件信息
     */
    public DmsFileInfo selectDmsFileInfoByFileId(String fileId);

    /**
     * 查询文件信息列表
     * 
     * @param dmsFileInfo 文件信息
     * @return 文件信息集合
     */
    public List<DmsFileInfo> selectDmsFileInfoList(DmsFileInfo dmsFileInfo);

    /**
     * 新增文件信息
     * 
     * @param dmsFileInfo 文件信息
     * @return 结果
     */
    public int insertDmsFileInfo(DmsFileInfo dmsFileInfo);

    /**
     * 修改文件信息
     * 
     * @param dmsFileInfo 文件信息
     * @return 结果
     */
    public int updateDmsFileInfo(DmsFileInfo dmsFileInfo);

    /**
     * 删除文件信息
     * 
     * @param fileId 文件信息主键
     * @return 结果
     */
    public int deleteDmsFileInfoByFileId(String fileId);

    /**
     * 批量删除文件信息
     * 
     * @param fileIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDmsFileInfoByFileIds(String fileIds);
}

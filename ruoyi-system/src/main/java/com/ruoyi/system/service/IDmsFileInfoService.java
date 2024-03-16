package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.system.domain.DmsDeptfileNum;
import com.ruoyi.system.domain.DmsFileInfo;
import com.ruoyi.system.domain.DmsBasicNum;
/**
 * 文件信息Service接口
 * 
 * @author HYZ
 * @date 2024-01-31
 */
public interface IDmsFileInfoService 
{
    /**
     * 查询文件信息
     * 
     * @param fileId 文件信息主键
     * @return 文件信息
     */
    public DmsFileInfo selectDmsFileInfoByFileId(String fileId);

    /**
     * 查询所有部门已发布文档数量
     * 
     * @param fileId 文件信息主键
     * @return 文件信息
     */
    public List<DmsDeptfileNum> selectdeptfilenum();
    
    /**
     * 查询用户的已发布文档、待审阅、待定稿、待修改数量
     * 
     * @param fileId 文件信息主键
     * @return 文件信息
     */
    public DmsBasicNum selectBacisnumByuserId(Long userid);
    /**
     * 查询文件信息列表
     * 
     * @param dmsFileInfo 文件信息
     * @return 文件信息集合
     */
    public List<DmsFileInfo> selectDmsFileInfoList(DmsFileInfo dmsFileInfo);
  
    /**
     * 根据用户权限查询文件信息列表
     * 
     * @param dmsFileInfo 文件信息
     * @return 文件信息集合
     */
    public List<DmsFileInfo> selectDmsFileInfoListByPremission(DmsFileInfo dmsFileInfo);

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
     * 修改文件状态
     * 
     * @param dmsFileInfo 文件信息
     * @return 结果
     */
    public int updateDmsFileStatus(String fileId, Long fileStatus);
    
    /**
     * 批量删除文件信息
     * 
     * @param fileIds 需要删除的文件信息主键集合
     * @return 结果
     */
    public int deleteDmsFileInfoByFileIds(String fileIds);

    /**
     * 删除文件信息信息
     * 
     * @param fileId 文件信息主键
     * @return 结果
     */
    public int deleteDmsFileInfoByFileId(String fileId);
}

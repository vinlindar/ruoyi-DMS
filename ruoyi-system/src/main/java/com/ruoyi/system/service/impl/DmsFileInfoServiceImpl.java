package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DmsFileInfoMapper;
import com.ruoyi.system.domain.DmsFileInfo;
import com.ruoyi.system.service.IDmsFileInfoService;
import com.ruoyi.system.domain.DmsBasicNum;
import com.ruoyi.system.domain.DmsClassifiedfileNum;
import com.ruoyi.system.domain.DmsDeptfileNum;
import com.ruoyi.system.domain.DmsfileNumbyMonth;
import com.ruoyi.system.domain.DmsfileNumbyYear;

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
    public DmsFileInfo selectDmsFileInfoByFileId(String fileId)
    {
        return dmsFileInfoMapper.selectDmsFileInfoByFileId(fileId);
    }

    /**
     * 查询所有部门已发布文档数量
     * 
     * @param fileId 文件信息主键
     * @return 文件信息
     */
    public List<DmsDeptfileNum> selectdeptfilenum() 
    {
    	return dmsFileInfoMapper.selectdeptfilenum();
    }
    
    /**
     * 查询用户的已发布文档、待审阅、待定稿、待修改数量
     * 
     * @param fileId 文件信息主键
     * @return 文件信息
     */
    public DmsBasicNum selectBacisnumByuserId(Long userid)
    {
    	return dmsFileInfoMapper.selectBacisnumByuserId(userid);
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
     * 根据用户权限查询文件信息列表
     * 
     * @param dmsFileInfo 文件信息
     * @return 文件信息集合
     */
    public List<DmsFileInfo> selectDmsFileInfoListByPremission(DmsFileInfo dmsFileInfo)
    {
        return dmsFileInfoMapper.selectDmsFileInfoListByPremission(dmsFileInfo);
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
     * 修改文件状态
     * 
     * @param fileId, fileStatus
     * @return 结果
     */
    public int updateDmsFileStatus(String fileId, Long fileStatus)
    {
    	DmsFileInfo fileInfo = new DmsFileInfo();
        fileInfo.setFileId(fileId);
        fileInfo.setFileStatus(fileStatus); 
    	return dmsFileInfoMapper.updateDmsFileStatus(fileInfo);
    }
    
    /**
     * 批量删除文件信息
     * 
     * @param fileIds 需要删除的文件信息主键
     * @return 结果
     */
    @Override
    public int deleteDmsFileInfoByFileIds(String fileIds)
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
    public int deleteDmsFileInfoByFileId(String fileId)
    {
        return dmsFileInfoMapper.deleteDmsFileInfoByFileId(fileId);
    }
    /**
     * 获取分类下的已发布文件数
     * 
     * @param 
     * @return 结果
     */
    @Override
	public List<DmsClassifiedfileNum> selectclassifiedfilenum()
    {
    	return dmsFileInfoMapper.selectclassifiedfilenum();
    }
    
    /**
    * 获取各年的已发布文件数
     * 
     * @param 
     * @return 结果
     */
    @Override
	public List<DmsfileNumbyYear> selectfilenumbyYear()
    {
    	return dmsFileInfoMapper.selectfilenumbyYear();
    }
    
    /**
    * 获取近12个月各个月的已发布文件数
     * 
     * @param 
     * @return 结果
     */
    @Override
	public List<DmsfileNumbyMonth> selectfilenumbyMonth()
    {
    	return dmsFileInfoMapper.selectfilenumbyMonth();
    }
    
}

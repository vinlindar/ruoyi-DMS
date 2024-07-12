package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.DmsFilePublish;
import com.ruoyi.system.domain.DmsFileReview;

/**
 * 定稿Service接口
 * 
 * @author HYZ
 * @date 2024-02-25
 */
public interface IDmsFilePublishService 
{
	
    /**
     * 查询定稿
     * 
     * @param id 主键
     * @return 
     */
    public DmsFilePublish selectDmsFilePublishById(Long id);
    /**
     * 查询fileId所有定稿
     * 
     * @param fileId 定稿
     * @return 定稿
     */
    public List<DmsFilePublish> selectDmsFilePublishByFileId(String fileId);

    /**
     * 查询定稿列表
     * 
     * @param dmsFilePublish 定稿
     * @return 定稿集合
     */
    public List<DmsFilePublish> selectDmsFilePublishList(DmsFilePublish dmsFilePublish);

    /**
     * 查询最近10个定稿的信息
     * 
     * @param dmsFilePublish 定稿
     * @return 定稿集合
     */
    public List<DmsFilePublish> selectDmsFilePublishlatestList();
    
    /**
     * 新增定稿
     * 
     * @param dmsFilePublish 定稿
     * @return 结果
     */
    public int insertDmsFilePublish(DmsFilePublish dmsFilePublish);

    /**
     * 修改定稿
     * 
     * @param dmsFilePublish 定稿
     * @return 结果
     */
    public int updateDmsFilePublish(DmsFilePublish dmsFilePublish);

    /**
     * 批量删除定稿
     * 
     * @param fileIds 需要删除的定稿主键集合
     * @return 结果
     */
    public int deleteDmsFilePublishByIds(Long[] ids);

    /**
     * 删除定稿信息
     * 
     * @param fileId 定稿主键
     * @return 结果
     */
    public int deleteDmsFilePublishByFileId(String fileId);
    /**
     * 修改定稿
     * 
     * @param dmsFilePublish 定稿
     * @return 结果
     */
	public int updateDmsFilePublishByFileId(DmsFilePublish dmsFilePublish);
}

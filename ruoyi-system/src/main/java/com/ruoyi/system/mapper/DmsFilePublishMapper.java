package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DmsFilePublish;
import com.ruoyi.system.domain.DmsFileReview;

/**
 * 定稿Mapper接口
 * 
 * @author HYZ
 * @date 2024-02-25
 */
public interface DmsFilePublishMapper 
{
    /**
     * 查询定稿
     * 
     * @param id 评阅主键
     * @return 评阅
     */
    public DmsFilePublish selectDmsFilePublishById(Long id);
    /**
     * 根据fileID查询定稿(可能多个)
     * 
     * @param fileId 
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
     * 查询最新定稿的10个列表
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
     * 修改定稿
     * 
     * @param dmsFilePublish 定稿
     * @return 结果
     */
    public int updateDmsFilePublishByFileId(DmsFilePublish dmsFilePublish);
    /**
     * 删除定稿
     * 
     * @param fileId 定稿主键
     * @return 结果
     */
    public int deleteDmsFilePublishByFileId(String fileId);

    /**
     * 批量删除定稿
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDmsFilePublishByIds(Long[] ids);

}

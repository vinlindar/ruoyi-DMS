package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DmsFilePublish;

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
     * @param fileId 定稿主键
     * @return 定稿
     */
    public DmsFilePublish selectDmsFilePublishByFileId(String fileId);

    /**
     * 查询定稿列表
     * 
     * @param dmsFilePublish 定稿
     * @return 定稿集合
     */
    public List<DmsFilePublish> selectDmsFilePublishList(DmsFilePublish dmsFilePublish);

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
     * 删除定稿
     * 
     * @param fileId 定稿主键
     * @return 结果
     */
    public int deleteDmsFilePublishByFileId(String fileId);

    /**
     * 批量删除定稿
     * 
     * @param fileIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDmsFilePublishByFileIds(String[] fileIds);
}

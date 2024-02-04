package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DmsFileReview;

/**
 * 评审Mapper接口
 * 
 * @author HYZ
 * @date 2024-02-01
 */
public interface DmsFileReviewMapper 
{
    /**
     * 查询评审
     * 
     * @param id 评审主键
     * @return 评审
     */
    public DmsFileReview selectDmsFileReviewById(String id);

    /**
     * 查询评审列表
     * 
     * @param dmsFileReview 评审
     * @return 评审集合
     */
    public List<DmsFileReview> selectDmsFileReviewList(DmsFileReview dmsFileReview);

    /**
     * 新增评审
     * 
     * @param dmsFileReview 评审
     * @return 结果
     */
    public int insertDmsFileReview(DmsFileReview dmsFileReview);

    /**
     * 修改评审
     * 
     * @param dmsFileReview 评审
     * @return 结果
     */
    public int updateDmsFileReview(DmsFileReview dmsFileReview);

    /**
     * 删除评审
     * 
     * @param id 评审主键
     * @return 结果
     */
    public int deleteDmsFileReviewById(String id);

    /**
     * 批量删除评审
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDmsFileReviewByIds(String[] ids);
}

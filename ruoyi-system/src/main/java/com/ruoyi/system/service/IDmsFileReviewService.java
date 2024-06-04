package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.DmsFileReview;

/**
 * 评阅Service接口
 * 
 * @author hyz
 * @date 2024-05-11
 */
public interface IDmsFileReviewService 
{
    /**
     * 查询评阅
     * 
     * @param id 评阅主键
     * @return 评阅
     */
    public DmsFileReview selectDmsFileReviewById(Long id);

    /**
     * 查询评阅列表
     * 
     * @param dmsFileReview 评阅
     * @return 评阅集合
     */
    public List<DmsFileReview> selectDmsFileReviewList(DmsFileReview dmsFileReview);

    /**
     * 新增评阅
     * 
     * @param dmsFileReview 评阅
     * @return 结果
     */
    public int insertDmsFileReview(DmsFileReview dmsFileReview);

    /**
     * 修改评阅
     * 
     * @param dmsFileReview 评阅
     * @return 结果
     */
    public int updateDmsFileReview(DmsFileReview dmsFileReview);

    /**
     * 批量删除评阅
     * 
     * @param ids 需要删除的评阅主键集合
     * @return 结果
     */
    public int deleteDmsFileReviewByIds(Long[] ids);

    /**
     * 删除评阅信息
     * 
     * @param id 评阅主键
     * @return 结果
     */
    public int deleteDmsFileReviewById(Long id);
}

package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.DmsFileReview;

/**
 * 文档评阅Service接口
 * 
 * @author HYZ
 * @date 2024-02-05
 */
public interface IDmsFileReviewService 
{
    /**
     * 查询文档评阅
     * 
     * @param fileId 和reviewerId 文档评阅主键
     * @return 文档评阅
     */
    public DmsFileReview selectDmsFileReviewByFileIdAndReviewerId(DmsFileReview dmsFileReview);

    /**
     * 查询文档评阅列表
     * 
     * @param dmsFileReview 文档评阅
     * @return 文档评阅集合
     */
    public List<DmsFileReview> selectDmsFileReviewList(DmsFileReview dmsFileReview);
    
    /**
     * 查询文档所有的评阅结果
     * 
     * @param dmsFileReview 文档评阅
     * @return 文档评阅
     */
    public List<DmsFileReview> getAllReviewsResultByFileId(String fileId);
    
    /**
     * 新增文档评阅
     * 
     * @param dmsFileReview 文档评阅
     * @return 结果
     */
    public int insertDmsFileReview(DmsFileReview dmsFileReview);

    /**
     * 修改文档评阅
     * 
     * @param dmsFileReview 文档评阅
     * @return 结果
     */
    public int updateDmsFileReview(DmsFileReview dmsFileReview);

    /**
     * 批量删除文档评阅
     * 
     * @param fileIds 需要删除的文档评阅主键集合
     * @return 结果
     */
    public int deleteDmsFileReviewByFileIds(String[] fileIds);

    /**
     * 删除文档评阅信息
     * 
     * @param fileId 文档评阅主键
     * @return 结果
     */
    public int deleteDmsFileReviewByFileId(String fileId);
}

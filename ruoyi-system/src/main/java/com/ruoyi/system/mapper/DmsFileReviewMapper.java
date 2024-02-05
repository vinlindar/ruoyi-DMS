package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DmsFileReview;

/**
 * 文档评阅Mapper接口
 * 
 * @author HYZ
 * @date 2024-02-05
 */
public interface DmsFileReviewMapper 
{
    /**
     * 查询文档评阅
     * 
     * @param fileId 文档评阅主键
     * @return 文档评阅
     */
    public DmsFileReview selectDmsFileReviewByFileId(String fileId);

    /**
     * 查询文档评阅列表
     * 
     * @param dmsFileReview 文档评阅
     * @return 文档评阅集合
     */
    public List<DmsFileReview> selectDmsFileReviewList(DmsFileReview dmsFileReview);

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
     * 删除文档评阅
     * 
     * @param fileId 文档评阅主键
     * @return 结果
     */
    public int deleteDmsFileReviewByFileId(String fileId);

    /**
     * 批量删除文档评阅
     * 
     * @param fileIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDmsFileReviewByFileIds(String[] fileIds);
}

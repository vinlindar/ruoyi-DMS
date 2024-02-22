package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DmsFileReviewMapper;
import com.ruoyi.system.domain.DmsFileReview;
import com.ruoyi.system.service.IDmsFileReviewService;

/**
 * 文档评阅Service业务层处理
 * 
 * @author HYZ
 * @date 2024-02-05
 */
@Service
public class DmsFileReviewServiceImpl implements IDmsFileReviewService 
{
    @Autowired
    private DmsFileReviewMapper dmsFileReviewMapper;

    /**
     * 查询文档评阅
     * 
     * @param fileId 文档评阅主键
     * @return 文档评阅
     */
    @Override
    public DmsFileReview selectDmsFileReviewByFileIdAndReviewerId(DmsFileReview dmsFileReview)
    {
        return dmsFileReviewMapper.selectDmsFileReviewByFileIdAndReviewerId(dmsFileReview);
    }

    /**
     * 查询文档评阅列表
     * 
     * @param dmsFileReview 文档评阅
     * @return 文档评阅
     */
    @Override
    public List<DmsFileReview> selectDmsFileReviewList(DmsFileReview dmsFileReview)
    {
        return dmsFileReviewMapper.selectDmsFileReviewList(dmsFileReview);
    }

    /**
     * 新增文档评阅
     * 
     * @param dmsFileReview 文档评阅
     * @return 结果
     */
    @Override
    public int insertDmsFileReview(DmsFileReview dmsFileReview)
    {
        return dmsFileReviewMapper.insertDmsFileReview(dmsFileReview);
    }

    /**
     * 修改文档评阅
     * 
     * @param dmsFileReview 文档评阅
     * @return 结果
     */
    @Override
    public int updateDmsFileReview(DmsFileReview dmsFileReview)
    {
        return dmsFileReviewMapper.updateDmsFileReview(dmsFileReview);
    }

    /**
     * 批量删除文档评阅
     * 
     * @param fileIds 需要删除的文档评阅主键
     * @return 结果
     */
    @Override
    public int deleteDmsFileReviewByFileIds(String[] fileIds)
    {
        return dmsFileReviewMapper.deleteDmsFileReviewByFileIds(fileIds);
    }

    /**
     * 删除文档评阅信息
     * 
     * @param fileId 文档评阅主键
     * @return 结果
     */
    @Override
    public int deleteDmsFileReviewByFileId(String fileId)
    {
        return dmsFileReviewMapper.deleteDmsFileReviewByFileId(fileId);
    }
}

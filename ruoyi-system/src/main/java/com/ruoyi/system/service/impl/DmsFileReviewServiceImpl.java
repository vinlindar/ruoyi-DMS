package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DmsFileReviewMapper;
import com.ruoyi.system.domain.DmsFileReview;
import com.ruoyi.system.service.IDmsFileReviewService;

/**
 * 评审Service业务层处理
 * 
 * @author HYZ
 * @date 2024-02-01
 */
@Service
public class DmsFileReviewServiceImpl implements IDmsFileReviewService 
{
    @Autowired
    private DmsFileReviewMapper dmsFileReviewMapper;

    /**
     * 查询评审
     * 
     * @param id 评审主键
     * @return 评审
     */
    @Override
    public DmsFileReview selectDmsFileReviewById(String id)
    {
        return dmsFileReviewMapper.selectDmsFileReviewById(id);
    }

    /**
     * 查询评审列表
     * 
     * @param dmsFileReview 评审
     * @return 评审
     */
    @Override
    public List<DmsFileReview> selectDmsFileReviewList(DmsFileReview dmsFileReview)
    {
        return dmsFileReviewMapper.selectDmsFileReviewList(dmsFileReview);
    }

    /**
     * 新增评审
     * 
     * @param dmsFileReview 评审
     * @return 结果
     */
    @Override
    public int insertDmsFileReview(DmsFileReview dmsFileReview)
    {
        return dmsFileReviewMapper.insertDmsFileReview(dmsFileReview);
    }

    /**
     * 修改评审
     * 
     * @param dmsFileReview 评审
     * @return 结果
     */
    @Override
    public int updateDmsFileReview(DmsFileReview dmsFileReview)
    {
        return dmsFileReviewMapper.updateDmsFileReview(dmsFileReview);
    }

    /**
     * 批量删除评审
     * 
     * @param ids 需要删除的评审主键
     * @return 结果
     */
    @Override
    public int deleteDmsFileReviewByIds(String[] ids)
    {
        return dmsFileReviewMapper.deleteDmsFileReviewByIds(ids);
    }

    /**
     * 删除评审信息
     * 
     * @param id 评审主键
     * @return 结果
     */
    @Override
    public int deleteDmsFileReviewById(String id)
    {
        return dmsFileReviewMapper.deleteDmsFileReviewById(id);
    }
}

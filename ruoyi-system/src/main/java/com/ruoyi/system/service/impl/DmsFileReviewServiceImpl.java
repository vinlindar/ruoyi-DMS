package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DmsFileReviewMapper;
import com.ruoyi.system.domain.DmsFileReview;
import com.ruoyi.system.service.IDmsFileReviewService;

/**
 * 评阅Service业务层处理
 * 
 * @author hyz
 * @date 2024-05-11
 */
@Service
public class DmsFileReviewServiceImpl implements IDmsFileReviewService 
{
    @Autowired
    private DmsFileReviewMapper dmsFileReviewMapper;

    /**
     * 查询评阅
     * 
     * @param id 评阅主键
     * @return 评阅
     */
    @Override
    public DmsFileReview selectDmsFileReviewById(Long id)
    {
        return dmsFileReviewMapper.selectDmsFileReviewById(id);
    }

    /**
     * 查询评阅列表
     * 
     * @param dmsFileReview 评阅
     * @return 评阅
     */
    @Override
    public List<DmsFileReview> selectDmsFileReviewList(DmsFileReview dmsFileReview)
    {
        return dmsFileReviewMapper.selectDmsFileReviewList(dmsFileReview);
    }

    /**
     * 新增评阅
     * 
     * @param dmsFileReview 评阅
     * @return 结果
     */
    @Override
    public int insertDmsFileReview(DmsFileReview dmsFileReview)
    {
        dmsFileReview.setCreateTime(DateUtils.getNowDate());
        return dmsFileReviewMapper.insertDmsFileReview(dmsFileReview);
    }

    /**
     * 修改评阅
     * 
     * @param dmsFileReview 评阅
     * @return 结果
     */
    @Override
    public int updateDmsFileReview(DmsFileReview dmsFileReview)
    {
        return dmsFileReviewMapper.updateDmsFileReview(dmsFileReview);
    }

    /**
     * 批量删除评阅
     * 
     * @param ids 需要删除的评阅主键
     * @return 结果
     */
    @Override
    public int deleteDmsFileReviewByIds(Long[] ids)
    {
        return dmsFileReviewMapper.deleteDmsFileReviewByIds(ids);
    }

    /**
     * 删除评阅信息
     * 
     * @param id 评阅主键
     * @return 结果
     */
    @Override
    public int deleteDmsFileReviewById(Long id)
    {
        return dmsFileReviewMapper.deleteDmsFileReviewById(id);
    }
}

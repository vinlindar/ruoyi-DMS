package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DmsFilePublishMapper;
import com.ruoyi.system.mapper.DmsFileReviewMapper;
import com.ruoyi.system.domain.DmsFilePublish;
import com.ruoyi.system.domain.DmsFileReview;
import com.ruoyi.system.service.IDmsFilePublishService;

/**
 * 定稿Service业务层处理
 * 
 * @author HYZ
 * @date 2024-02-25
 */
@Service
public class DmsFilePublishServiceImpl implements IDmsFilePublishService 
{
    @Autowired
    private DmsFilePublishMapper dmsFilePublishMapper;
    /**
     * 查询评阅
     * 
     * @param id 定稿主键
     * @return 定稿
     */
    @Override
    public DmsFilePublish selectDmsFilePublishById(Long id)
    {
        return dmsFilePublishMapper.selectDmsFilePublishById(id);
    }
    /**
     * 查询定稿
     * 
     * @param fileId 定稿
     * @return 定稿
     */
    @Override
    public List<DmsFilePublish> selectDmsFilePublishByFileId(String fileId)
    {
        return dmsFilePublishMapper.selectDmsFilePublishByFileId(fileId);
    }

    /**
     * 查询定稿列表
     * 
     * @param dmsFilePublish 定稿
     * @return 定稿
     */
    @Override
    public List<DmsFilePublish> selectDmsFilePublishList(DmsFilePublish dmsFilePublish)
    {
        return dmsFilePublishMapper.selectDmsFilePublishList(dmsFilePublish);
    }

    /**
     * 查询最近10个定稿的信息
     * 
     * @param dmsFilePublish 定稿
     * @return 定稿集合
     */
    public List<DmsFilePublish> selectDmsFilePublishlatestList()
    {
        return dmsFilePublishMapper.selectDmsFilePublishlatestList();
    }
    
    /**
     * 新增定稿
     * 
     * @param dmsFilePublish 定稿
     * @return 结果
     */
    @Override
    public int insertDmsFilePublish(DmsFilePublish dmsFilePublish)
    {
        return dmsFilePublishMapper.insertDmsFilePublish(dmsFilePublish);
    }

    /**
     * 修改定稿
     * 
     * @param dmsFilePublish 定稿
     * @return 结果
     */
    @Override
    public int updateDmsFilePublish(DmsFilePublish dmsFilePublish)
    {
        return dmsFilePublishMapper.updateDmsFilePublish(dmsFilePublish);
    }

    /**
     * 修改定稿
     * 
     * @param dmsFilePublish 定稿
     * @return 结果
     */
    @Override
    public int updateDmsFilePublishByFileId(DmsFilePublish dmsFilePublish)
    {
        return dmsFilePublishMapper.updateDmsFilePublishByFileId(dmsFilePublish);
    }
    /**
     * 批量删除定稿
     * 
     * @param fileIds 需要删除的定稿主键
     * @return 结果
     */
    @Override
    public int deleteDmsFilePublishByIds(Long[] ids)
    {
        return dmsFilePublishMapper.deleteDmsFilePublishByIds(ids);
    }

    /**
     * 删除定稿信息
     * 
     * @param fileId 定稿主键
     * @return 结果
     */
    @Override
    public int deleteDmsFilePublishByFileId(String fileId)
    {
        return dmsFilePublishMapper.deleteDmsFilePublishByFileId(fileId);
    }
}

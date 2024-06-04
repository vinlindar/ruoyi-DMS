package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DmsNewsImagesMapper;
import com.ruoyi.system.domain.DmsNewsImages;
import com.ruoyi.system.service.IDmsNewsImagesService;

/**
 * 新闻照片Service业务层处理
 * 
 * @author HYZ
 * @date 2024-06-02
 */
@Service
public class DmsNewsImagesServiceImpl implements IDmsNewsImagesService 
{
    @Autowired
    private DmsNewsImagesMapper dmsNewsImagesMapper;

    /**
     * 查询新闻照片
     * 
     * @param id 新闻照片主键
     * @return 新闻照片
     */
    @Override
    public DmsNewsImages selectDmsNewsImagesById(Long id)
    {
        return dmsNewsImagesMapper.selectDmsNewsImagesById(id);
    }

    /**
     * 查询新闻照片列表
     * 
     * @param dmsNewsImages 新闻照片
     * @return 新闻照片
     */
    @Override
    public List<DmsNewsImages> selectDmsNewsImagesList(DmsNewsImages dmsNewsImages)
    {
        return dmsNewsImagesMapper.selectDmsNewsImagesList(dmsNewsImages);
    }

    /**
     * 新增新闻照片
     * 
     * @param dmsNewsImages 新闻照片
     * @return 结果
     */
    @Override
    public int insertDmsNewsImages(DmsNewsImages dmsNewsImages)
    {
        return dmsNewsImagesMapper.insertDmsNewsImages(dmsNewsImages);
    }

    /**
     * 修改新闻照片
     * 
     * @param dmsNewsImages 新闻照片
     * @return 结果
     */
    @Override
    public int updateDmsNewsImages(DmsNewsImages dmsNewsImages)
    {
        return dmsNewsImagesMapper.updateDmsNewsImages(dmsNewsImages);
    }

    /**
     * 批量删除新闻照片
     * 
     * @param ids 需要删除的新闻照片主键
     * @return 结果
     */
    @Override
    public int deleteDmsNewsImagesByIds(Long[] ids)
    {
        return dmsNewsImagesMapper.deleteDmsNewsImagesByIds(ids);
    }

    /**
     * 删除新闻照片信息
     * 
     * @param id 新闻照片主键
     * @return 结果
     */
    @Override
    public int deleteDmsNewsImagesById(Long id)
    {
        return dmsNewsImagesMapper.deleteDmsNewsImagesById(id);
    }
}

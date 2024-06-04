package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.DmsNewsImages;

/**
 * 新闻照片Service接口
 * 
 * @author HYZ
 * @date 2024-06-02
 */
public interface IDmsNewsImagesService 
{
    /**
     * 查询新闻照片
     * 
     * @param id 新闻照片主键
     * @return 新闻照片
     */
    public DmsNewsImages selectDmsNewsImagesById(Long id);

    /**
     * 查询新闻照片列表
     * 
     * @param dmsNewsImages 新闻照片
     * @return 新闻照片集合
     */
    public List<DmsNewsImages> selectDmsNewsImagesList(DmsNewsImages dmsNewsImages);

    /**
     * 新增新闻照片
     * 
     * @param dmsNewsImages 新闻照片
     * @return 结果
     */
    public int insertDmsNewsImages(DmsNewsImages dmsNewsImages);

    /**
     * 修改新闻照片
     * 
     * @param dmsNewsImages 新闻照片
     * @return 结果
     */
    public int updateDmsNewsImages(DmsNewsImages dmsNewsImages);

    /**
     * 批量删除新闻照片
     * 
     * @param ids 需要删除的新闻照片主键集合
     * @return 结果
     */
    public int deleteDmsNewsImagesByIds(Long[] ids);

    /**
     * 删除新闻照片信息
     * 
     * @param id 新闻照片主键
     * @return 结果
     */
    public int deleteDmsNewsImagesById(Long id);
}

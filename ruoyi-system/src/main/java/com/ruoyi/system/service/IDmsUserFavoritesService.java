package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.DmsUserFavorites;

/**
 * 用户收藏文件Service接口
 * 
 * @author HYZ
 * @date 2024-03-26
 */
public interface IDmsUserFavoritesService 
{
    /**
     * 查询用户收藏文件
     * 
     * @param userId 用户收藏文件主键
     * @return 用户收藏文件
     */
    public DmsUserFavorites selectDmsUserFavoritesByUserId(Long userId);

    /**
     * 查询用户收藏文件列表
     * 
     * @param dmsUserFavorites 用户收藏文件
     * @return 用户收藏文件集合
     */
    public List<DmsUserFavorites> selectDmsUserFavoritesList(DmsUserFavorites dmsUserFavorites);

    /**
     * 新增用户收藏文件
     * 
     * @param dmsUserFavorites 用户收藏文件
     * @return 结果
     */
    public int insertDmsUserFavorites(DmsUserFavorites dmsUserFavorites);

    /**
     * 修改用户收藏文件
     * 
     * @param dmsUserFavorites 用户收藏文件
     * @return 结果
     */
    public int updateDmsUserFavorites(DmsUserFavorites dmsUserFavorites);

    /**
     * 删除用户收藏文件信息
     * 
     * @param userId 用户收藏文件主键
     * @return 结果
     */
    public int deleteDmsUserFavorites(DmsUserFavorites dmsUserFavorites);
}

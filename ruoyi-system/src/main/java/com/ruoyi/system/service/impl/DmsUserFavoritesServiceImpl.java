package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DmsUserFavoritesMapper;
import com.ruoyi.system.domain.DmsUserFavorites;
import com.ruoyi.system.service.IDmsUserFavoritesService;

/**
 * 用户收藏文件Service业务层处理
 * 
 * @author HYZ
 * @date 2024-03-26
 */
@Service
public class DmsUserFavoritesServiceImpl implements IDmsUserFavoritesService 
{
    @Autowired
    private DmsUserFavoritesMapper dmsUserFavoritesMapper;

    /**
     * 查询用户收藏文件
     * 
     * @param userId 用户收藏文件主键
     * @return 用户收藏文件
     */
    @Override
    public DmsUserFavorites selectDmsUserFavoritesByUserId(Long userId)
    {
        return dmsUserFavoritesMapper.selectDmsUserFavoritesByUserId(userId);
    }

    /**
     * 查询用户收藏文件列表
     * 
     * @param dmsUserFavorites 用户收藏文件
     * @return 用户收藏文件
     */
    @Override
    public List<DmsUserFavorites> selectDmsUserFavoritesList(DmsUserFavorites dmsUserFavorites)
    {
        return dmsUserFavoritesMapper.selectDmsUserFavoritesList(dmsUserFavorites);
    }

    /**
     * 新增用户收藏文件
     * 
     * @param dmsUserFavorites 用户收藏文件
     * @return 结果
     */
    @Override
    public int insertDmsUserFavorites(DmsUserFavorites dmsUserFavorites)
    {
        return dmsUserFavoritesMapper.insertDmsUserFavorites(dmsUserFavorites);
    }

    /**
     * 修改用户收藏文件
     * 
     * @param dmsUserFavorites 用户收藏文件
     * @return 结果
     */
    @Override
    public int updateDmsUserFavorites(DmsUserFavorites dmsUserFavorites)
    {
        return dmsUserFavoritesMapper.updateDmsUserFavorites(dmsUserFavorites);
    }

    /**
     * 删除用户收藏文件信息
     * 
     * @param userId 用户收藏文件主键
     * @return 结果
     */
    @Override
    public int deleteDmsUserFavorites(DmsUserFavorites dmsUserFavorites)
    {
        return dmsUserFavoritesMapper.deleteDmsUserFavorites(dmsUserFavorites);
    }
}

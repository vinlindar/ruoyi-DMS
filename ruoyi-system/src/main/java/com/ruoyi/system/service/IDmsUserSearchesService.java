package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.DmsUserSearches;

/**
 * 用户自定义搜索Service接口
 * 
 * @author HYZ
 * @date 2024-03-27
 */
public interface IDmsUserSearchesService 
{
    /**
     * 查询用户自定义搜索
     * 
     * @param id 用户自定义搜索主键
     * @return 用户自定义搜索
     */
    public DmsUserSearches selectDmsUserSearchesById(Long id);

    /**
     * 查询用户自定义搜索列表
     * 
     * @param dmsUserSearches 用户自定义搜索
     * @return 用户自定义搜索集合
     */
    public List<DmsUserSearches> selectDmsUserSearchesList(DmsUserSearches dmsUserSearches);

    /**
     * 新增用户自定义搜索
     * 
     * @param dmsUserSearches 用户自定义搜索
     * @return 结果
     */
    public int insertDmsUserSearches(DmsUserSearches dmsUserSearches);

    /**
     * 修改用户自定义搜索
     * 
     * @param dmsUserSearches 用户自定义搜索
     * @return 结果
     */
    public int updateDmsUserSearches(DmsUserSearches dmsUserSearches);

    /**
     * 删除用户自定义搜索信息
     * 
     * @param id 用户自定义搜索主键
     * @return 结果
     */
    public int deleteDmsUserSearchesByuserId(Long userId);
}

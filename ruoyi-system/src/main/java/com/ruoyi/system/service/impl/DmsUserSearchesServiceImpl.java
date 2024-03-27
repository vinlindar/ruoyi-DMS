package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DmsUserSearchesMapper;
import com.ruoyi.system.domain.DmsUserSearches;
import com.ruoyi.system.service.IDmsUserSearchesService;

/**
 * 用户自定义搜索Service业务层处理
 * 
 * @author HYZ
 * @date 2024-03-27
 */
@Service
public class DmsUserSearchesServiceImpl implements IDmsUserSearchesService 
{
    @Autowired
    private DmsUserSearchesMapper dmsUserSearchesMapper;

    /**
     * 查询用户自定义搜索
     * 
     * @param id 用户自定义搜索主键
     * @return 用户自定义搜索
     */
    @Override
    public DmsUserSearches selectDmsUserSearchesById(Long id)
    {
        return dmsUserSearchesMapper.selectDmsUserSearchesById(id);
    }

    /**
     * 查询用户自定义搜索列表
     * 
     * @param dmsUserSearches 用户自定义搜索
     * @return 用户自定义搜索
     */
    @Override
    public List<DmsUserSearches> selectDmsUserSearchesList(DmsUserSearches dmsUserSearches)
    {
        return dmsUserSearchesMapper.selectDmsUserSearchesList(dmsUserSearches);
    }

    /**
     * 新增用户自定义搜索
     * 
     * @param dmsUserSearches 用户自定义搜索
     * @return 结果
     */
    @Override
    public int insertDmsUserSearches(DmsUserSearches dmsUserSearches)
    {
        return dmsUserSearchesMapper.insertDmsUserSearches(dmsUserSearches);
    }

    /**
     * 修改用户自定义搜索
     * 
     * @param dmsUserSearches 用户自定义搜索
     * @return 结果
     */
    @Override
    public int updateDmsUserSearches(DmsUserSearches dmsUserSearches)
    {
        return dmsUserSearchesMapper.updateDmsUserSearches(dmsUserSearches);
    }

    /**
     * 删除用户自定义搜索信息
     * 
     * @param id 用户自定义搜索主键
     * @return 结果
     */
    @Override
    public int deleteDmsUserSearchesByuserId(Long userId)
    {
        return dmsUserSearchesMapper.deleteDmsUserSearchesByuserId(userId);
    }
}

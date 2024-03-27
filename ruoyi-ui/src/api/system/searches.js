import request from '@/utils/request'

// 查询用户自定义搜索列表
export function listSearches(query) {
  return request({
    url: '/system/searches/list',
    method: 'get',
    params: query
  })
}

// 新增用户自定义搜索
export function addSearches(data) {
  return request({
    url: '/system/searches',
    method: 'post',
    data: data
  })
}

// 修改用户自定义搜索
export function updateSearches(data) {
  return request({
    url: '/system/searches',
    method: 'put',
    data: data
  })
}

// 删除用户自定义搜索
export function delSearches(id) {
  return request({
    url: '/system/searches/' + id,
    method: 'delete'
  })
}

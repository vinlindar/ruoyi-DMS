import request from '@/utils/request'

// 查询用户收藏文件列表
export function listFavorites(query) {
  return request({
    url: '/system/favorites/list',
    method: 'get',
    params: query
  })
}

// 查询用户收藏文件详细
export function getFavorites(userId) {
  return request({
    url: '/system/favorites/' + userId,
    method: 'get'
  })
}

// 新增用户收藏文件
export function addFavorites(data) {
  return request({
    url: '/system/favorites',
    method: 'post',
    data: data
  })
}

// 修改用户收藏文件
export function updateFavorites(data) {
  return request({
    url: '/system/favorites',
    method: 'put',
    data: data
  })
}

// 删除用户收藏文件
export function delFavorites(data) {
  return request({
    url: '/system/favorites/',
    method: 'delete',
    data: data
  })
}

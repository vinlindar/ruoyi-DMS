import request from '@/utils/request'

// 查询文档权限列表
export function listPermissions(query) {
  return request({
    url: '/system/permissions/list',
    method: 'get',
    params: query
  })
}

// 查询文档权限详细
export function getPermissions(fileId) {
  return request({
    url: '/system/permissions/' + fileId,
    method: 'get'
  })
}

// 新增文档权限
export function addPermissions(data) {
  return request({
    url: '/system/permissions',
    method: 'post',
    data: data
  })
}

// 修改文档权限
export function updatePermissions(data) {
  return request({
    url: '/system/permissions',
    method: 'put',
    data: data
  })
}

// 删除文档权限
export function delPermissions(fileId) {
  return request({
    url: '/system/permissions/' + fileId,
    method: 'delete'
  })
}

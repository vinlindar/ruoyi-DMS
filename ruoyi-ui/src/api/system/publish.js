import request from '@/utils/request'

// 查询定稿列表
export function listPublish(query) {
  return request({
    url: '/system/publish/list',
    method: 'get',
    params: query
  })
}

// 查询定稿详细
export function getPublish(fileId) {
  return request({
    url: '/system/publish/' + fileId,
    method: 'get'
  })
}

// 新增定稿
export function addPublish(data) {
  return request({
    url: '/system/publish',
    method: 'post',
    data: data
  })
}

// 修改定稿
export function updatePublish(data) {
  return request({
    url: '/system/publish',
    method: 'put',
    data: data
  })
}

// 删除定稿
export function delPublish(fileId) {
  return request({
    url: '/system/publish/' + fileId,
    method: 'delete'
  })
}

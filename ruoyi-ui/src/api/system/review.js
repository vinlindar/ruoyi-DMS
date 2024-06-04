import request from '@/utils/request'

// 查询评阅列表
export function listReview(query) {
  return request({
    url: '/system/review/list',
    method: 'get',
    params: query
  })
}

// 查询评阅详细
export function getReview(id) {
  return request({
    url: '/system/review/' + id,
    method: 'get'
  })
}

// 新增评阅
export function addReview(data) {
  return request({
    url: '/system/review',
    method: 'post',
    data: data
  })
}

// 修改评阅
export function updateReview(data) {
  return request({
    url: '/system/review',
    method: 'put',
    data: data
  })
}

// 删除评阅
export function delReview(id) {
  return request({
    url: '/system/review/' + id,
    method: 'delete'
  })
}

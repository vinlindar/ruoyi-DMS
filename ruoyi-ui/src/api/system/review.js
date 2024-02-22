import request from '@/utils/request'

// 查询文档评阅列表
export function listReview(query) {
  return request({
    url: '/system/review/list',
    method: 'get',
    params: query
  })
}

// 查询文档评阅详细(根据fileId和reviewerId共同查询)
export function getReview(fileId,reviewerId) {
  return request({
    url: '/system/review/'+fileId + '/' + reviewerId,
    method: 'get',
  })
}

// 新增文档评阅
export function addReview(data) {
  return request({
    url: '/system/review',
    method: 'post',
    data: data
  })
}

// 修改文档评阅
export function updateReview(data) {
  return request({
    url: '/system/review',
    method: 'put',
    data: data
  })
}

// 删除文档评阅
export function delReview(fileId) {
  return request({
    url: '/system/review/' + fileId,
    method: 'delete'
  })
}

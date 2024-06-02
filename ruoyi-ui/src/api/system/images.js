import request from '@/utils/request'

// 查询新闻照片列表
export function listImages(query) {
  return request({
    url: '/system/images/list',
    method: 'get',
    params: query
  })
}

// 查询新闻照片详细
export function getImages(id) {
  return request({
    url: '/system/images/' + id,
    method: 'get'
  })
}

// 新增新闻照片
export function addImages(data) {
  return request({
    url: '/system/images',
    method: 'post',
    data: data
  })
}

// 修改新闻照片
export function updateImages(data) {
  return request({
    url: '/system/images',
    method: 'put',
    data: data
  })
}

// 删除新闻照片
export function delImages(id) {
  return request({
    url: '/system/images/' + id,
    method: 'delete'
  })
}

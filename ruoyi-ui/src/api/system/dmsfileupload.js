import request from '@/utils/request'

// 查询文件信息列表
export function listDmsfileupload(query) {
  return request({
    url: '/system/dmsfileupload/list',
    method: 'get',
    params: query
  })
}

// 查询文件信息详细
export function getDmsfileupload(fileId) {
  return request({
    url: '/system/dmsfileupload/' + fileId,
    method: 'get'
  })
}

// 新增文件信息
export function addDmsfileupload(data) {
  return request({
    url: '/system/dmsfileupload',
    method: 'post',
    data: data
  })
}

// 修改文件信息
export function updateDmsfileupload(data) {
  return request({
    url: '/system/dmsfileupload',
    method: 'put',
    data: data
  })
}

// 删除文件信息
export function delDmsfileupload(fileId) {
  return request({
    url: '/system/dmsfileupload/' + fileId,
    method: 'delete'
  })
}

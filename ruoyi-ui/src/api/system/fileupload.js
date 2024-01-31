import request from '@/utils/request'

// 查询上传文档列表
export function listFileupload(query) {
  return request({
    url: '/system/fileupload/list',
    method: 'get',
    params: query
  })
}

// 查询上传文档详细
export function getFileupload(FileID) {
  return request({
    url: '/system/fileupload/' + FileID,
    method: 'get'
  })
}

// 新增上传文档
export function addFileupload(data) {
  return request({
    url: '/system/fileupload',
    method: 'post',
    data: data
  })
}

// 修改上传文档
export function updateFileupload(data) {
  return request({
    url: '/system/fileupload',
    method: 'put',
    data: data
  })
}

// 删除上传文档
export function delFileupload(FileID) {
  return request({
    url: '/system/fileupload/' + FileID,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询文件上传列表
export function listFileupload(query) {
  return request({
    url: '/dms/fileupload/list',
    method: 'get',
    params: query
  })
}

// 查询文件上传详细
export function getFileupload(FileID) {
  return request({
    url: '/dms/fileupload/' + FileID,
    method: 'get'
  })
}

// 新增文件上传
export function addFileupload(data) {
  return request({
    url: '/dms/fileupload',
    method: 'post',
    data: data
  })
}

// 修改文件上传
export function updateFileupload(data) {
  return request({
    url: '/dms/fileupload',
    method: 'put',
    data: data
  })
}

// 删除文件上传
export function delFileupload(FileID) {
  return request({
    url: '/dms/fileupload/' + FileID,
    method: 'delete'
  })
}

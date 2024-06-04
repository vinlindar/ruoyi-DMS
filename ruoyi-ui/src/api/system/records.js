import request from '@/utils/request'

// 查询下载记录列表
export function listRecords(query) {
  return request({
    url: '/system/records/list',
    method: 'get',
    params: query
  })
}

// 查询下载记录详细
export function getRecords(id) {
  return request({
    url: '/system/records/' + id,
    method: 'get'
  })
}
// 查询文件id下载次数
export function getDownloadNumbyfileId(fileId) {
  return request({
    url: '/system/records/fileId/' + fileId,
    method: 'get'
  })
}

// 新增下载记录
export function addRecords(data) {
  return request({
    url: '/system/records',
    method: 'post',
    data: data
  })
}

// 修改下载记录
export function updateRecords(data) {
  return request({
    url: '/system/records',
    method: 'put',
    data: data
  })
}

// 删除下载记录
export function delRecords(id) {
  return request({
    url: '/system/records/' + id,
    method: 'delete'
  })
}

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
    url: '/system/dmsfileupload/edit',
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
// 查询部门下拉树结构
export function deptTreeSelect() {
  return request({
    url: '/system/user/deptTree',
    method: 'get'
  })
}

//修改基本信息和发布范围（管理员）
export function manageDmsfile(data) {
  return request({
    url: '/system/dmsfileupload/manage',
    method: 'put',
    data: data
  })
}

//查询非发布的文件信息
export function listUnpublishedFile(query) {
  return request({
    url: '/system/dmsfileupload/unpublishedlist',
    method: 'get',
    params: query
  })
}

//文件预览
export function filepreview(fileId){
  return request({
    url: '/system/filepreview/' + fileId,
    method: 'get'
  })
}
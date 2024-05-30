import request from '@/utils/request'

// 获取各个部门发布的文档数量
export function getdeptfilenum() {
  return request({
    url: '/system/filestatistics/deptfilenum',
    method: 'get',
  })
}
// 获取各个分类下的文档数量
export function getclassifiedfilenum() {
  return request({
    url: '/system/filestatistics/classifiedfilenum',
    method: 'get',
  })
}
//获取各年的已发布文件数
export function getyearfilenum() {
  return request({
    url: '/system/filestatistics/yearfilenum',
    method: 'get',
  })
}
//获取近12个月各个月的已发布文件数
export function getmonthfilenum() {
  return request({
    url: '/system/filestatistics/monthfilenum',
    method: 'get',
  })
}
//获取各团队下载文件数
export function getdetpdownloadfilenum() {
  return request({
    url: '/system/filestatistics/detpdownloadfilenum',
    method: 'get',
  })
}
//获取各分类下载文件数
export function getclassifieddownloadfilenum() {
  return request({
    url: '/system/filestatistics/classifieddownloadfilenum',
    method: 'get',
  })
}

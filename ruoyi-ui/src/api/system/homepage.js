import request from '@/utils/request'

// 获取用户的已办文档、待审阅、待定稿、待修改数量和信息
export function userhomepagebasicinfo(userId) {
  return request({
    url: '/system/homepage/'+userId,
    method: 'get',
  })
}
// 获取最新发布的文档信息(10个)
export function listlatestfileinfo() {
  return request({
    url: '/system/homepage/list',
    method: 'get',
  })
}
// 获取下载量最多的文档信息（10个）
export function getmostpopularfileinfo() {
  return request({
    url: '/system/homepage/popularfile',
    method: 'get',
  })
}
// 获取各个部门发布的文档数量
export function getdeptfilenum() {
  return request({
    url: '/system/homepage/deptfilenum',
    method: 'get',
  })
}
//获取首页走马灯图片
export function getlistimages() {
  return request({
    url: '/system/homepage/images',
    method: 'get',
  })
}
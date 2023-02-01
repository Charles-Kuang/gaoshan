import request from '@/utils/request'

// 查询小程序用户列表
export function listGs_user(query) {
  return request({
    url: '/core/gs_user/list',
    method: 'get',
    params: query
  })
}

// 查询小程序用户详细
export function getGs_user(userId) {
  return request({
    url: '/core/gs_user/' + userId,
    method: 'get'
  })
}

// 新增小程序用户
export function addGs_user(data) {
  return request({
    url: '/core/gs_user',
    method: 'post',
    data: data
  })
}

// 修改小程序用户
export function updateGs_user(data) {
  return request({
    url: '/core/gs_user',
    method: 'put',
    data: data
  })
}

// 删除小程序用户
export function delGs_user(userId) {
  return request({
    url: '/core/gs_user/' + userId,
    method: 'delete'
  })
}

// 导出小程序用户
export function exportGs_user(query) {
  return request({
    url: '/core/gs_user/export',
    method: 'get',
    params: query
  })
}
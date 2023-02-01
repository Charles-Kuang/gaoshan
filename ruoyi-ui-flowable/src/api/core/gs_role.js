import request from '@/utils/request'

// 查询前台角色列表
export function listGs_role(query) {
  return request({
    url: '/core/gs_role/list',
    method: 'get',
    params: query
  })
}

// 查询前台角色详细
export function getGs_role(roleId) {
  return request({
    url: '/core/gs_role/' + roleId,
    method: 'get'
  })
}

// 新增前台角色
export function addGs_role(data) {
  return request({
    url: '/core/gs_role',
    method: 'post',
    data: data
  })
}

// 修改前台角色
export function updateGs_role(data) {
  return request({
    url: '/core/gs_role',
    method: 'put',
    data: data
  })
}

// 删除前台角色
export function delGs_role(roleId) {
  return request({
    url: '/core/gs_role/' + roleId,
    method: 'delete'
  })
}

// 导出前台角色
export function exportGs_role(query) {
  return request({
    url: '/core/gs_role/export',
    method: 'get',
    params: query
  })
}
import request from '@/utils/request'

// 查询街路巷管理列表
export function listStreet(query) {
  return request({
    url: '/core/street/list',
    method: 'get',
    params: query
  })
}

// 查询街路巷管理详细
export function getStreet(streetId) {
  return request({
    url: '/core/street/' + streetId,
    method: 'get'
  })
}

// 新增街路巷管理
export function addStreet(data) {
  return request({
    url: '/core/street',
    method: 'post',
    data: data
  })
}

// 修改街路巷管理
export function updateStreet(data) {
  return request({
    url: '/core/street',
    method: 'put',
    data: data
  })
}


// 审核街路巷管理
export function auditStreet(data) {
  return request({
    url: '/core/street/audit',
    method: 'put',
    data: data
  })
}


// 删除街路巷管理
export function delStreet(streetId) {
  return request({
    url: '/core/street/' + streetId,
    method: 'delete'
  })
}

// 导出街路巷管理
export function exportStreet(query) {
  return request({
    url: '/core/street/export',
    method: 'get',
    params: query
  })
}

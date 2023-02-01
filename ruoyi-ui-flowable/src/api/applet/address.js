import request from '@/utils/request'

// 查询标准详址列表
export function listAddress(query) {
  return request({
    url: '/applet/address/list',
    method: 'get',
    params: query
  })
}

// 查询标准详址详细
export function getAddress(addressId) {
  return request({
    url: '/applet/address/' + addressId,
    method: 'get'
  })
}

// 新增标准详址
export function addAddress(data) {
  return request({
    url: '/applet/address',
    method: 'post',
    data: data
  })
}

// 修改标准详址
export function updateAddress(data) {
  return request({
    url: '/applet/address',
    method: 'put',
    data: data
  })
}

// 删除标准详址
export function delAddress(addressId) {
  return request({
    url: '/applet/address/' + addressId,
    method: 'delete'
  })
}

// 导出标准详址
export function exportAddress(query) {
  return request({
    url: '/applet/address/export',
    method: 'get',
    params: query
  })
}

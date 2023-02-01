import request from '@/utils/request'

// 查询实有房屋列表
export function listHouse(query) {
  return request({
    url: '/applet/house/list',
    method: 'get',
    params: query
  })
}

// 查询实有房屋详细
export function getHouse(houseId) {
  return request({
    url: '/applet/house/' + houseId,
    method: 'get'
  })
}

// 新增实有房屋
export function addHouse(data) {
  return request({
    url: '/applet/house',
    method: 'post',
    data: data
  })
}

// 修改实有房屋
export function updateHouse(data) {
  return request({
    url: '/applet/house',
    method: 'put',
    data: data
  })
}

// 修改实有房屋
export function updateHouseOne(data) {
  return request({
    url: '/applet/house/edit',
    method: 'put',
    data: data
  })
}

// 删除实有房屋
export function delHouse(houseId) {
  return request({
    url: '/applet/house/' + houseId,
    method: 'delete'
  })
}

// 导出实有房屋
export function exportHouse(query) {
  return request({
    url: '/applet/house/export',
    method: 'get',
    params: query
  })
}

import request from '@/utils/request'

// 查询addressHouse列表
export function listAddress_house(query) {
  return request({
    url: '/applet/address_house/list',
    method: 'get',
    params: query
  })
}

// 查询addressHouse详细
export function getAddress_house(id) {
  return request({
    url: '/applet/address_house/' + id,
    method: 'get'
  })
}

// 新增addressHouse
export function addAddress_house(data) {
  return request({
    url: '/applet/address_house',
    method: 'post',
    data: data
  })
}

// 修改addressHouse
export function updateAddress_house(data) {
  return request({
    url: '/applet/address_house',
    method: 'put',
    data: data
  })
}

// 修改addressHouse
export function updateAddress_houseOne(data) {
  return request({
    url: '/applet/address_house/edit',
    method: 'put',
    data: data
  })
}
const baseURL = process.env.VUE_APP_BASE_API
// 修改addressHouse
export function updateAddress_houseTwo(number) {
 window.location.href = baseURL + "/applet/address_house/createQrcode/" + number;

  // return request({
  //   url: '/applet/address_house/createQrcode/' + number,
  //   method: 'get',
  // })
}

// 删除addressHouse
export function delAddress_house(id) {
  return request({
    url: '/applet/address_house/' + id,
    method: 'delete'
  })
}

// 导出addressHouse
export function exportAddress_house(query) {
  return request({
    url: '/applet/address_house/export',
    method: 'get',
    params: query
  })
}

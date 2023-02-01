import request from '@/utils/request'

// 查询行政区列表
export function listRegion(query) {
  return request({
    url: '/system/region/list',
    method: 'get',
    params: query
  })
}

// 查询行政区列表
export function listRegionOne(query) {
  return request({
    url: '/system/region/listOne',
    method: 'get',
    params: query
  })
}

// 查询行政区列表
export function listRegionThree(query) {
  return request({
    url: '/system/region/listTwo',
    method: 'get',
    params: query
  })
}

// 查询行政区详细
export function getRegion(code) {
  return request({
    url: '/system/region/' + code,
    method: 'get'
  })
}

// 查询子行政区
export function getChild(code) {
  return request({
    url: '/system/region/getChild/' + code,
    method: 'get'
  })
}

// 新增行政区
export function addRegion(data) {
  return request({
    url: '/system/region',
    method: 'post',
    data: data
  })
}

// 修改行政区
export function updateRegion(data) {
  return request({
    url: '/system/region',
    method: 'put',
    data: data
  })
}

// 修改行政区
export function updateRegionOne(data) {
  return request({
    url: '/system/region/edit',
    method: 'put',
    data: data
  })
}

// 删除行政区
export function delRegion(regionId) {
  return request({
    url: '/system/region/' + regionId,
    method: 'delete'
  })
}

// 将该行政机关的子行政机关重新拉取到数据库
export function loadChildRegion(code) {
  return request({
    url: '/system/region/getChildRegion/'+code,
    method: 'get',
  })
}

// 查询菜单下拉树结构
export function cityTree() {
  return request({
    url: '/system/region/cityTree',
    method: 'get'
  })
}

// 查询行政区列表
export function listRegionTwo(query) {
  return request({
    url: '/system/region/city/list',
    method: 'get',
    params: query
  })
}


// 查询子行政区
export function getChildOne(code) {
  return request({
    url: '/system/region/getChildOne/' + code,
    method: 'get'
  })
}

// 查询子行政区
export function listGsRegion(query) {
  return request({
    url: '/system/region/listGsRegion',
    method: 'get',
    params: query
  })
}

// 用户状态修改
export function changeInvalidFlag(regionId, status) {
  const data = {
    regionId,
    status
  }
  return request({
    url: '/system/region/changeInvalidFlag',
    method: 'put',
    data: data
  })
}



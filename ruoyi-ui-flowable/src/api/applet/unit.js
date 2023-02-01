import request from '@/utils/request'
//查询行政区划列表
export function listArea(query) {
  return request({
    url: '/system/region/list',
    method: 'get',
    params: query
  })
}
// 查询实有单位列列表
export function listUnit(query) {
  return request({
    url: '/applet/unit/list',
    method: 'get',
    params: query
  })
}

// 查询实有单位列详细
export function getUnit(unitId) {
  return request({
    url: '/applet/unit/' + unitId,
    method: 'get'
  })
}

// 新增实有单位列
export function addUnit(data) {
  return request({
    url: '/applet/unit',
    method: 'post',
    data: data
  })
}

// 修改实有单位列
export function updateUnit(data) {
  return request({
    url: '/applet/unit',
    method: 'put',
    data: data
  })
}

// 修改实有单位列
export function updateUnitOne(data) {
  return request({
    url: '/applet/unit/edit',
    method: 'put',
    data: data
  })
}

// 删除实有单位列
export function delUnit(unitId) {
  return request({
    url: '/applet/unit/' + unitId,
    method: 'delete'
  })
}

// 导出实有单位列
export function exportUnit(query) {
  return request({
    url: '/applet/unit/export',
    method: 'get',
    params: query
  })
}

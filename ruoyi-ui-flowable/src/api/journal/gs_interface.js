import request from '@/utils/request'

// 查询接口日志列表
export function listGs_interface(query) {
  return request({
    url: '/journal/gs_interface/list',
    method: 'get',
    params: query
  })
}

// 查询接口日志详细
export function getGs_interface(id) {
  return request({
    url: '/journal/gs_interface/' + id,
    method: 'get'
  })
}

// 新增接口日志
export function addGs_interface(data) {
  return request({
    url: '/journal/gs_interface',
    method: 'post',
    data: data
  })
}

// 修改接口日志
export function updateGs_interface(data) {
  return request({
    url: '/journal/gs_interface',
    method: 'put',
    data: data
  })
}

// 删除接口日志
export function delGs_interface(id) {
  return request({
    url: '/journal/gs_interface/' + id,
    method: 'delete'
  })
}

// 导出接口日志
export function exportGs_interface(query) {
  return request({
    url: '/journal/gs_interface/export',
    method: 'get',
    params: query
  })
}
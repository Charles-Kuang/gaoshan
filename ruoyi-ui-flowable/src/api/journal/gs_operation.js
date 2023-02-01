import request from '@/utils/request'

// 查询操作日志列表
export function listGs_operation(query) {
  return request({
    url: '/journal/gs_operation/list',
    method: 'get',
    params: query
  })
}

// 查询操作日志详细
export function getGs_operation(id) {
  return request({
    url: '/journal/gs_operation/' + id,
    method: 'get'
  })
}

// 新增操作日志
export function addGs_operation(data) {
  return request({
    url: '/journal/gs_operation',
    method: 'post',
    data: data
  })
}

// 修改操作日志
export function updateGs_operation(data) {
  return request({
    url: '/journal/gs_operation',
    method: 'put',
    data: data
  })
}

// 删除操作日志
export function delGs_operation(id) {
  return request({
    url: '/journal/gs_operation/' + id,
    method: 'delete'
  })
}

// 导出操作日志
export function exportGs_operation(query) {
  return request({
    url: '/journal/gs_operation/export',
    method: 'get',
    params: query
  })
}
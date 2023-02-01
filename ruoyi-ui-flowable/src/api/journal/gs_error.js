import request from '@/utils/request'

// 查询错误日志列表
export function listGs_error(query) {
  return request({
    url: '/journal/gs_error/list',
    method: 'get',
    params: query
  })
}

// 查询错误日志详细
export function getGs_error(id) {
  return request({
    url: '/journal/gs_error/' + id,
    method: 'get'
  })
}

// 新增错误日志
export function addGs_error(data) {
  return request({
    url: '/journal/gs_error',
    method: 'post',
    data: data
  })
}

// 修改错误日志
export function updateGs_error(data) {
  return request({
    url: '/journal/gs_error',
    method: 'put',
    data: data
  })
}

// 删除错误日志
export function delGs_error(id) {
  return request({
    url: '/journal/gs_error/' + id,
    method: 'delete'
  })
}

// 导出错误日志
export function exportGs_error(query) {
  return request({
    url: '/journal/gs_error/export',
    method: 'get',
    params: query
  })
}
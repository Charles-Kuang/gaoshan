import request from '@/utils/request'

// 查询服务咨询列表
export function listConsult(query) {
  return request({
    url: '/applet/consult/list',
    method: 'get',
    params: query
  })
}

// 查询服务咨询详细
export function getConsult(id) {
  return request({
    url: '/applet/consult/' + id,
    method: 'get'
  })
}

// 新增服务咨询
export function addConsult(data) {
  return request({
    url: '/applet/consult',
    method: 'post',
    data: data
  })
}

// 修改服务咨询
export function updateConsult(data) {
  return request({
    url: '/applet/consult',
    method: 'put',
    data: data
  })
}

// 删除服务咨询
export function delConsult(id) {
  return request({
    url: '/applet/consult/' + id,
    method: 'delete'
  })
}

// 导出服务咨询
export function exportConsult(query) {
  return request({
    url: '/applet/consult/export',
    method: 'get',
    params: query
  })
}

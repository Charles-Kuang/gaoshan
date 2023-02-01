import request from '@/utils/request'

// 查询积分记录列表
export function listGs_record(query) {
  return request({
    url: '/core/gs_record/list',
    method: 'get',
    params: query
  })
}

// 查询积分记录详细
export function getGs_record(id) {
  return request({
    url: '/core/gs_record/' + id,
    method: 'get'
  })
}

// 新增积分记录
export function addGs_record(data) {
  return request({
    url: '/core/gs_record',
    method: 'post',
    data: data
  })
}

// 修改积分记录
export function updateGs_record(data) {
  return request({
    url: '/core/gs_record',
    method: 'put',
    data: data
  })
}

// 删除积分记录
export function delGs_record(id) {
  return request({
    url: '/core/gs_record/' + id,
    method: 'delete'
  })
}

// 导出积分记录
export function exportGs_record(query) {
  return request({
    url: '/core/gs_record/export',
    method: 'get',
    params: query
  })
}

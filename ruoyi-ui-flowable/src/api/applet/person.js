import request from '@/utils/request'

// 查询实有人口登记列表
export function listPerson(query) {
  return request({
    url: '/applet/person/list',
    method: 'get',
    params: query
  })
}

// 查询实有人口登记详细
export function getPerson(personId) {
  return request({
    url: '/applet/person/' + personId,
    method: 'get'
  })
}

// 新增实有人口登记
export function addPerson(data) {
  return request({
    url: '/applet/person',
    method: 'post',
    data: data
  })
}

// 修改实有人口登记
export function updatePerson(data) {
  return request({
    url: '/applet/person',
    method: 'put',
    data: data
  })
}

// 修改实有房屋
export function updatePersonOne(data) {
  return request({
    url: '/applet/person/edit',
    method: 'put',
    data: data
  })
}

// 删除实有人口登记
export function delPerson(personId) {
  return request({
    url: '/applet/person/' + personId,
    method: 'delete'
  })
}

// 导出实有人口登记
export function exportPerson(query) {
  return request({
    url: '/applet/person/export',
    method: 'get',
    params: query
  })
}

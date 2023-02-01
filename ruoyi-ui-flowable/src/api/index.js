import request from '@/utils/request'
// 获取用户详细信息
export function getMap() {
  return request({
    url: 'https://geo.datav.aliyun.com/areas_v3/bound/520000_full.json',
    method: 'get'
  })
}

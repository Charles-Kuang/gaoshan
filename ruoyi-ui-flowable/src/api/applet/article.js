import request from '@/utils/request'

// 查询新闻资讯列表
export function listArticle(query) {
  return request({
    url: '/applet/article/list',
    method: 'get',
    params: query
  })
}

// 查询新闻资讯详细
export function getArticle(id) {
  return request({
    url: '/applet/article/' + id,
    method: 'get'
  })
}

// 新增新闻资讯
export function addArticle(data) {
  return request({
    url: '/applet/article',
    method: 'post',
    data: data
  })
}

// 修改新闻资讯
export function updateArticle(data) {
  return request({
    url: '/applet/article',
    method: 'put',
    data: data
  })
}

// 删除新闻资讯
export function delArticle(id) {
  return request({
    url: '/applet/article/' + id,
    method: 'delete'
  })
}

// 导出新闻资讯
export function exportArticle(query) {
  return request({
    url: '/applet/article/export',
    method: 'get',
    params: query
  })
}

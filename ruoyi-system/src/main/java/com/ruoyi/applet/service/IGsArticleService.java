package com.ruoyi.applet.service;

import com.ruoyi.applet.domain.GsArticle;

import java.util.List;

/**
 * 新闻资讯Service接口
 * 
 * @author ruoyi
 * @date 2022-11-01
 */
public interface IGsArticleService 
{
    /**
     * 查询新闻资讯
     * 
     * @param id 新闻资讯ID
     * @return 新闻资讯
     */
    public GsArticle selectGsArticleById(Long id);

    /**
     * 查询新闻资讯列表
     * 
     * @param gsArticle 新闻资讯
     * @return 新闻资讯集合
     */
    public List<GsArticle> selectGsArticleList(GsArticle gsArticle);

    /**
     * 新增新闻资讯
     * 
     * @param gsArticle 新闻资讯
     * @return 结果
     */
    public int insertGsArticle(GsArticle gsArticle);

    /**
     * 修改新闻资讯
     * 
     * @param gsArticle 新闻资讯
     * @return 结果
     */
    public int updateGsArticle(GsArticle gsArticle);

    /**
     * 批量删除新闻资讯
     * 
     * @param ids 需要删除的新闻资讯ID
     * @return 结果
     */
    public int deleteGsArticleByIds(Long[] ids);

    /**
     * 删除新闻资讯信息
     * 
     * @param id 新闻资讯ID
     * @return 结果
     */
    public int deleteGsArticleById(Long id);
}

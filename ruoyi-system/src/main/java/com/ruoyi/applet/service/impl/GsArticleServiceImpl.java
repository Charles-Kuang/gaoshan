package com.ruoyi.applet.service.impl;

import java.util.List;

import com.ruoyi.applet.domain.GsArticle;
import com.ruoyi.applet.service.IGsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.applet.mapper.GsArticleMapper;

/**
 * 新闻资讯Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-11-01
 */
@Service
public class GsArticleServiceImpl implements IGsArticleService
{
    @Autowired
    private GsArticleMapper gsArticleMapper;

    /**
     * 查询新闻资讯
     * 
     * @param id 新闻资讯ID
     * @return 新闻资讯
     */
    @Override
    public GsArticle selectGsArticleById(Long id)
    {
        return gsArticleMapper.selectGsArticleById(id);
    }

    /**
     * 查询新闻资讯列表
     * 
     * @param gsArticle 新闻资讯
     * @return 新闻资讯
     */
    @Override
    public List<GsArticle> selectGsArticleList(GsArticle gsArticle)
    {
        return gsArticleMapper.selectGsArticleList(gsArticle);
    }

    /**
     * 新增新闻资讯
     * 
     * @param gsArticle 新闻资讯
     * @return 结果
     */
    @Override
    public int insertGsArticle(GsArticle gsArticle)
    {
        return gsArticleMapper.insertGsArticle(gsArticle);
    }

    /**
     * 修改新闻资讯
     * 
     * @param gsArticle 新闻资讯
     * @return 结果
     */
    @Override
    public int updateGsArticle(GsArticle gsArticle)
    {
        return gsArticleMapper.updateGsArticle(gsArticle);
    }

    /**
     * 批量删除新闻资讯
     * 
     * @param ids 需要删除的新闻资讯ID
     * @return 结果
     */
    @Override
    public int deleteGsArticleByIds(Long[] ids)
    {
        return gsArticleMapper.deleteGsArticleByIds(ids);
    }

    /**
     * 删除新闻资讯信息
     * 
     * @param id 新闻资讯ID
     * @return 结果
     */
    @Override
    public int deleteGsArticleById(Long id)
    {
        return gsArticleMapper.deleteGsArticleById(id);
    }
}

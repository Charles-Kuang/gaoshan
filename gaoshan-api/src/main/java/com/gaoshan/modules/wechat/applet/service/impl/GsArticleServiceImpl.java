package com.gaoshan.modules.wechat.applet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaoshan.modules.wechat.applet.domain.GsArticle;
import com.gaoshan.modules.wechat.applet.mapper.GsArticleMapper;
import com.gaoshan.modules.wechat.applet.service.IGsArticleService;
import org.springframework.stereotype.Service;

/**
 * 新闻资讯Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-11-01
 */
@Service
public class GsArticleServiceImpl extends ServiceImpl<GsArticleMapper, GsArticle> implements IGsArticleService
{
}

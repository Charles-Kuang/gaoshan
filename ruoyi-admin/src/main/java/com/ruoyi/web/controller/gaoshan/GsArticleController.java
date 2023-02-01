package com.ruoyi.web.controller.gaoshan;

import com.ruoyi.applet.domain.GsArticle;
import com.ruoyi.applet.service.IGsArticleService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 新闻资讯Controller
 * 
 * @author ruoyi
 * @date 2022-11-01
 */
@RestController
@RequestMapping("/applet/article")
public class GsArticleController extends BaseController
{
    @Autowired
    private IGsArticleService gsArticleService;

    /**
     * 查询新闻资讯列表
     */
    @PreAuthorize("@ss.hasPermi('applet:article:list')")
    @GetMapping("/list")
    public TableDataInfo list(GsArticle gsArticle)
    {
        startPage();
        List<GsArticle> list = gsArticleService.selectGsArticleList(gsArticle);
        return getDataTable(list);
    }

    /**
     * 导出新闻资讯列表
     */
    @PreAuthorize("@ss.hasPermi('applet:article:export')")
    @Log(title = "新闻资讯", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GsArticle gsArticle)
    {
        List<GsArticle> list = gsArticleService.selectGsArticleList(gsArticle);
        ExcelUtil<GsArticle> util = new ExcelUtil<GsArticle>(GsArticle.class);
        return util.exportExcel(list, "article");
    }

    /**
     * 获取新闻资讯详细信息
     */
    @PreAuthorize("@ss.hasPermi('applet:article:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(gsArticleService.selectGsArticleById(id));
    }

    /**
     * 新增新闻资讯
     */
    @PreAuthorize("@ss.hasPermi('applet:article:add')")
    @Log(title = "新闻资讯", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GsArticle gsArticle)
    {
        return toAjax(gsArticleService.insertGsArticle(gsArticle));
    }

    /**
     * 修改新闻资讯
     */
    @PreAuthorize("@ss.hasPermi('applet:article:edit')")
    @Log(title = "新闻资讯", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GsArticle gsArticle)
    {
        return toAjax(gsArticleService.updateGsArticle(gsArticle));
    }

    /**
     * 删除新闻资讯
     */
    @PreAuthorize("@ss.hasPermi('applet:article:remove')")
    @Log(title = "新闻资讯", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(gsArticleService.deleteGsArticleByIds(ids));
    }
}

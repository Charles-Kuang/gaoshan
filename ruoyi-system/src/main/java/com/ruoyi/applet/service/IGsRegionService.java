package com.ruoyi.applet.service;

import com.ruoyi.applet.domain.vo.CityRegionVo;
import com.ruoyi.common.core.domain.entity.GsRegion;

import java.util.List;

/**
 * 行政区Service接口
 * 
 * @author ruoyi
 * @date 2022-11-02
 */
public interface IGsRegionService {

    /**
     * 查询行政区列表
     *
     * @param gsRegion 行政区
     * @return 行政区集合
     */
    public List<GsRegion> selectGsRegionList(GsRegion gsRegion);

    /**
     * 新增行政区
     *
     * @param gsRegion 行政区
     * @return 结果
     */
    public int insertGsRegion(GsRegion gsRegion);

    /**
     * 修改行政区
     *
     * @param gsRegion 行政区
     * @return 结果
     */
    public int updateGsRegion(GsRegion gsRegion);

    /**
     * 批量删除行政区
     *
     * @param regionIds 需要删除的行政区ID
     * @return 结果
     */
    public int deleteGsRegionByIds(Long[] regionIds);

    GsRegion selectGsRegionByCode(Long parentCode);

    List<GsRegion> getChild(Long code);

    List<GsRegion> selectGsRegionListOne(GsRegion gsRegion);

    int updateGsRegionOne(GsRegion gsRegion);

    List<CityRegionVo> selectRegionList();

    List<CityRegionVo> getChildOne(Long code);

    List<CityRegionVo> listGsRegion(GsRegion region);

    List<GsRegion> selectGsRegionListTwo(GsRegion gsRegion);

    int updateInvalidFlag(GsRegion region);
}

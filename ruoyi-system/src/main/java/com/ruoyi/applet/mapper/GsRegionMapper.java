package com.ruoyi.applet.mapper;

import com.ruoyi.applet.domain.vo.CityRegionVo;
import com.ruoyi.common.core.domain.entity.GsRegion;

import java.util.List;

/**
 * 行政区Mapper接口
 *
 * @author ruoyi
 * @date 2022-11-02
 */
public interface GsRegionMapper {

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
     * @param regionIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteGsRegionByIds(Long[] regionIds);

    GsRegion selectGsRegionByCode(Long code);

    List<GsRegion> getChild(Long code);

    List<GsRegion> selectGsRegionListOne(GsRegion gsRegion);

    void updateGsRegionOne(GsRegion parentRegion);

    List<GsRegion> selectRegionListByRegionIds(Long[] codes);

    List<CityRegionVo>  selectGsRegionListTwo();

    List<CityRegionVo> getChildOne(Long code);

    List<CityRegionVo> listGsRegion(GsRegion region);

    int updateInvalidFlag(GsRegion region);
}

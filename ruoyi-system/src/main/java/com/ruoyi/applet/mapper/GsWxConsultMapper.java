package com.ruoyi.applet.mapper;

import java.util.List;
import com.ruoyi.applet.domain.GsWxConsult;

/**
 * 服务咨询Mapper接口
 * 
 * @author ruoyi
 * @date 2022-11-18
 */
public interface GsWxConsultMapper 
{
    /**
     * 查询服务咨询
     * 
     * @param id 服务咨询ID
     * @return 服务咨询
     */
    public GsWxConsult selectGsWxConsultById(Long id);

    /**
     * 查询服务咨询列表
     * 
     * @param gsWxConsult 服务咨询
     * @return 服务咨询集合
     */
    public List<GsWxConsult> selectGsWxConsultList(GsWxConsult gsWxConsult);

    /**
     * 新增服务咨询
     * 
     * @param gsWxConsult 服务咨询
     * @return 结果
     */
    public int insertGsWxConsult(GsWxConsult gsWxConsult);

    /**
     * 修改服务咨询
     * 
     * @param gsWxConsult 服务咨询
     * @return 结果
     */
    public int updateGsWxConsult(GsWxConsult gsWxConsult);

    /**
     * 删除服务咨询
     * 
     * @param id 服务咨询ID
     * @return 结果
     */
    public int deleteGsWxConsultById(Long id);

    /**
     * 批量删除服务咨询
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGsWxConsultByIds(Long[] ids);
}

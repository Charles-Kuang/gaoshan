package com.ruoyi.applet.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.applet.mapper.GsWxConsultMapper;
import com.ruoyi.applet.domain.GsWxConsult;
import com.ruoyi.applet.service.IGsWxConsultService;

/**
 * 服务咨询Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-11-18
 */
@Service
public class GsWxConsultServiceImpl implements IGsWxConsultService 
{
    @Autowired
    private GsWxConsultMapper gsWxConsultMapper;

    /**
     * 查询服务咨询
     * 
     * @param id 服务咨询ID
     * @return 服务咨询
     */
    @Override
    public GsWxConsult selectGsWxConsultById(Long id)
    {
        return gsWxConsultMapper.selectGsWxConsultById(id);
    }

    /**
     * 查询服务咨询列表
     * 
     * @param gsWxConsult 服务咨询
     * @return 服务咨询
     */
    @Override
    public List<GsWxConsult> selectGsWxConsultList(GsWxConsult gsWxConsult)
    {
        return gsWxConsultMapper.selectGsWxConsultList(gsWxConsult);
    }

    /**
     * 新增服务咨询
     * 
     * @param gsWxConsult 服务咨询
     * @return 结果
     */
    @Override
    public int insertGsWxConsult(GsWxConsult gsWxConsult)
    {
        return gsWxConsultMapper.insertGsWxConsult(gsWxConsult);
    }

    /**
     * 修改服务咨询
     * 
     * @param gsWxConsult 服务咨询
     * @return 结果
     */
    @Override
    public int updateGsWxConsult(GsWxConsult gsWxConsult)
    {
        return gsWxConsultMapper.updateGsWxConsult(gsWxConsult);
    }

    /**
     * 批量删除服务咨询
     * 
     * @param ids 需要删除的服务咨询ID
     * @return 结果
     */
    @Override
    public int deleteGsWxConsultByIds(Long[] ids)
    {
        return gsWxConsultMapper.deleteGsWxConsultByIds(ids);
    }

    /**
     * 删除服务咨询信息
     * 
     * @param id 服务咨询ID
     * @return 结果
     */
    @Override
    public int deleteGsWxConsultById(Long id)
    {
        return gsWxConsultMapper.deleteGsWxConsultById(id);
    }
}

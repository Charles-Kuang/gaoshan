package com.ruoyi.applet.service;

import java.util.List;
import com.ruoyi.applet.domain.GsPerson;
import com.ruoyi.applet.domain.vo.VerifyVo;

/**
 * 实有人口登记Service接口
 * 
 * @author ruoyi
 * @date 2022-11-26
 */
public interface IGsPersonService 
{
    /**
     * 查询实有人口登记
     * 
     * @param personId 实有人口登记ID
     * @return 实有人口登记
     */
    public GsPerson selectGsPersonById(Long personId);

    /**
     * 查询实有人口登记列表
     * 
     * @param gsPerson 实有人口登记
     * @return 实有人口登记集合
     */
    public List<GsPerson> selectGsPersonList(GsPerson gsPerson);

    /**
     * 新增实有人口登记
     * 
     * @param gsPerson 实有人口登记
     * @return 结果
     */
    public int insertGsPerson(GsPerson gsPerson);

    /**
     * 修改实有人口登记
     * 
     * @param gsPerson 实有人口登记
     * @return 结果
     */
    public int updateGsPerson(GsPerson gsPerson);

    /**
     * 批量删除实有人口登记
     * 
     * @param personIds 需要删除的实有人口登记ID
     * @return 结果
     */
    public int deleteGsPersonByIds(Long[] personIds);

    /**
     * 删除实有人口登记信息
     * 
     * @param personId 实有人口登记ID
     * @return 结果
     */
    public int deleteGsPersonById(Long personId);

    int updateGsPersonOne(GsPerson gsPerson);
}

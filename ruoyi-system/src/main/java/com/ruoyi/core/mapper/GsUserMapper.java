package com.ruoyi.core.mapper;

import java.util.List;
import com.ruoyi.core.domain.GsUser;
import com.ruoyi.core.domain.GsUserIntegralLog;

/**
 * 小程序用户Mapper接口
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
public interface GsUserMapper
{
    /**
     * 查询小程序用户
     * 
     * @param userId 小程序用户ID
     * @return 小程序用户
     */
    public GsUser selectGsUserById(Long userId);

    /**
     * 查询小程序用户列表
     * 
     * @param gsUser 小程序用户
     * @return 小程序用户集合
     */
    public List<GsUser> selectGsUserList(GsUser gsUser);

    /**
     * 新增小程序用户
     * 
     * @param gsUser 小程序用户
     * @return 结果
     */
    public int insertGsUser(GsUser gsUser);

    /**
     * 修改小程序用户
     * 
     * @param gsUser 小程序用户
     * @return 结果
     */
    public int updateGsUser(GsUser gsUser);

    /**
     * 删除小程序用户
     * 
     * @param userId 小程序用户ID
     * @return 结果
     */
    public int deleteGsUserById(Long userId);

    /**
     * 批量删除小程序用户
     * 
     * @param userIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteGsUserByIds(Long[] userIds);

    /**
     * 批量删除积分记录
     * 
     * @param customerIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteGsUserIntegralLogByUserIds(Long[] userIds);
    
    /**
     * 批量新增积分记录
     * 
     * @param gsUserIntegralLogList 积分记录列表
     * @return 结果
     */
    public int batchGsUserIntegralLog(List<GsUserIntegralLog> gsUserIntegralLogList);
    

    /**
     * 通过小程序用户ID删除积分记录信息
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteGsUserIntegralLogByUserId(Long userId);
}

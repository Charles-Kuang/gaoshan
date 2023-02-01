package com.ruoyi.core.service;

import java.util.List;
import com.ruoyi.core.domain.GsUser;

/**
 * 小程序用户Service接口
 * 
 * @author ruoyi
 * @date 2022-12-02
 */
public interface IGsUserService 
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
     * 批量删除小程序用户
     * 
     * @param userIds 需要删除的小程序用户ID
     * @return 结果
     */
    public int deleteGsUserByIds(Long[] userIds);

    /**
     * 删除小程序用户信息
     * 
     * @param userId 小程序用户ID
     * @return 结果
     */
    public int deleteGsUserById(Long userId);
}

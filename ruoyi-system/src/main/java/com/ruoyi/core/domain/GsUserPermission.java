package com.ruoyi.core.domain;



public class GsUserPermission {

  private long userId;
  private long roleId;


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }


  public long getRoleId() {
    return roleId;
  }

  public void setRoleId(long roleId) {
    this.roleId = roleId;
  }

  @Override
  public String toString() {
    return "GsUserPermission{" +
            "userId=" + userId +
            ", roleId=" + roleId +
            '}';
  }
}

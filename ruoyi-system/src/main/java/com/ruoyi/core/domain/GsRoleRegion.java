package com.ruoyi.core.domain;

public class GsRoleRegion {

  private long actorId;
  private long regionId;


  public long getActorId() {
    return actorId;
  }

  public void setActorId(long actorId) {
    this.actorId = actorId;
  }


  public long getRegionId() {
    return regionId;
  }

  public void setRegionId(long regionId) {
    this.regionId = regionId;
  }

  @Override
  public String toString() {
    return "GsRoleRegion{" +
            "actorId=" + actorId +
            ", regionId=" + regionId +
            '}';
  }
}

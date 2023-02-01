package com.gaoshan.modules.wechat.addressHouse.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gaoshan.modules.wechat.common.domain.Common;
import com.gaoshan.modules.wechat.person.domain.Person;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.List;

@Data
@TableName("gs_address_house")
public class AddressHouse extends Common {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String qrCode;
    private String addressType;
    private String address;
    private String addressGroup;
    private String doorplate;
    private String doorPhoto;
    private Double latitude;
    private Double longitude;
    private String adcode;
    private String cityCode;
    private String townCode;
    private String nationCode;
    private String propertyType;
    private String propertyUnit;
    private String propertyPerson;
    private String identityNumber;
    private String phoneNumber;
    private String houseType;
    private String buildingStructure;
    private Double roomArea;
    private Integer houseNumber;
    private String houseUse;//
    @TableField(exist = false)
    private List<String> houseUseList;
    private String livingStatus;//
    @TableField(exist = false)
    private List<String> livingStatusList;
    @TableField(exist = false)
    private List<Person> personList;
    private String remark;

    public String getHouseUse() {
        return houseUse;
    }

    public void setHouseUse(String houseUse) {
        this.houseUseList=JSON.parseArray(houseUse,String.class);
        this.houseUse = houseUse;
    }

    public List<String> getHouseUseList() {
        return houseUseList;
    }

    public void setHouseUseList(List<String> houseUseList) {
        this.houseUse=JSON.toJSONString(houseUseList);
        this.houseUseList = houseUseList;
    }

    public String getLivingStatus() {
        return livingStatus;
    }

    public void setLivingStatus(String livingStatus) {
        this.livingStatusList=JSON.parseArray(livingStatus,String.class);
        this.livingStatus = livingStatus;
    }

    public List<String> getLivingStatusList() {
        return livingStatusList;
    }

    public void setLivingStatusList(List<String> livingStatusList) {
        this.livingStatus=JSON.toJSONString(livingStatusList);
        this.livingStatusList = livingStatusList;
    }
}

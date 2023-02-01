package com.gaoshan.modules.wechat.address.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gaoshan.modules.wechat.address.domain.Address;
import com.gaoshan.util.Result;

public interface AddressService extends IService<Address> {

    Result addressSearch(String keyword, Integer page, Integer pageSize,Integer regionId);
}

package com.gaoshan.modules.wechat.house.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaoshan.modules.wechat.house.domain.House;
import com.gaoshan.modules.wechat.house.mapping.HouseMapper;
import com.gaoshan.modules.wechat.house.service.HouseService;
import org.springframework.stereotype.Service;

@Service
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {
}

package com.gaoshan.modules.wechat.region.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaoshan.modules.wechat.region.domain.Region;
import com.gaoshan.modules.wechat.region.mapping.RegionMapper;
import com.gaoshan.modules.wechat.region.service.RegionService;
import org.springframework.stereotype.Service;

@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {
}

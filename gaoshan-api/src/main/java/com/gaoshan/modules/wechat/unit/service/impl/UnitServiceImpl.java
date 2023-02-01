package com.gaoshan.modules.wechat.unit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaoshan.modules.wechat.unit.domain.Unit;
import com.gaoshan.modules.wechat.unit.mapping.UnitMapper;
import com.gaoshan.modules.wechat.unit.service.UnitService;
import org.springframework.stereotype.Service;

@Service
public class UnitServiceImpl extends ServiceImpl<UnitMapper, Unit> implements UnitService {
}

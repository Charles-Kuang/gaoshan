package com.gaoshan.aspect.flow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaoshan.aspect.flow.dao.ExceptionLogDao;
import com.gaoshan.aspect.flow.entity.ExceptionLogParam;
import com.gaoshan.aspect.flow.service.ExceptionLogService;
import org.springframework.stereotype.Service;

/**
 * @author lumberer
 */
@Service
public class ExceptionLogServiceImpl  extends ServiceImpl<ExceptionLogDao, ExceptionLogParam> implements ExceptionLogService {
}

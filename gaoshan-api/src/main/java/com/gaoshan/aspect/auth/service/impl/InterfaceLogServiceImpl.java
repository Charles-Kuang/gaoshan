package com.gaoshan.aspect.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaoshan.aspect.auth.dao.InterfaceLogDao;
import com.gaoshan.aspect.auth.entity.InterfaceLog;
import com.gaoshan.aspect.auth.service.InterfaceLogService;
import org.springframework.stereotype.Service;

/**
 * @author lumberer
 */
@Service
public class InterfaceLogServiceImpl extends ServiceImpl<InterfaceLogDao, InterfaceLog> implements InterfaceLogService {
}

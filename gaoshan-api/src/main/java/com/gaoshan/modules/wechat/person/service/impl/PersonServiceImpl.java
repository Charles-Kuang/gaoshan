package com.gaoshan.modules.wechat.person.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaoshan.modules.wechat.person.domain.Person;
import com.gaoshan.modules.wechat.person.mapping.PersonMapper;
import com.gaoshan.modules.wechat.person.service.PersonService;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements PersonService {
}

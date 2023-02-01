package com.gaoshan.modules.wechat.address.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaoshan.modules.wechat.address.domain.Address;
import com.gaoshan.modules.wechat.address.mapping.AddressDao;
import com.gaoshan.modules.wechat.address.service.AddressService;
import com.gaoshan.util.Result;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressDao, Address> implements AddressService {
    @Override
    public Result addressSearch(String keyword, Integer page, Integer pageSize,Integer regionId) {
        QueryWrapper<Address> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("address_detail",keyword);
        if(null!=regionId){
            queryWrapper.like("region_Id",regionId);
        }
        Page pages=new Page();
        pages.setPages(page.longValue());
        pages.setSize(pageSize.longValue());
        IPage<Address> data=this.pageMaps(pages,queryWrapper);
        return Result.SUCCESS(data);
    }
}

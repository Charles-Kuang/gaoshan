package com.gaoshan.modules.wechat.dict;

import com.alibaba.fastjson.JSON;
import com.gaoshan.config.RedisCache;
import com.gaoshan.enumeration.SYS_DICT_TYPE;
import com.gaoshan.modules.wechat.dict.model.Dict;
import com.gaoshan.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取字典集合
 * @author lumberer
 * @date 2022-11-25
 */
@Api(tags="获取字典集合")
@RestController
@RequestMapping("/dict")
public class SysDictController {
    @Autowired
    private RedisCache cache;

    /**
     * 根据key获取缓存里面的集合值
     * @param key
     * @return
     */
    @ApiOperation("根据key获取缓存里面的集合值")
    @GetMapping("/{key}")
    public Result getDict(@PathVariable("key") String key){
        StringBuffer keyDict=new StringBuffer(SYS_DICT_TYPE.SYS_DICT.getValue());
        keyDict.append(key);
        List<Dict> data=JSON.parseArray(JSON.toJSONString(cache.getCacheObject(keyDict.toString())),Dict.class) ;
        return Result.SUCCESS(data);
    }
}

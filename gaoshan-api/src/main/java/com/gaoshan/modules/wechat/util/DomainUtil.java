package com.gaoshan.modules.wechat.util;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.util.*;

public class DomainUtil {
    public static <T> List<String> difference(T t1, T t2)  {
        try{
            Field[] fields =  t1.getClass().getDeclaredFields();
            Field[] fields2 =  t2.getClass().getDeclaredFields();
            List<String> result=new ArrayList<>();
            Map<String,Object> prevMap=new HashMap<>();
            Map<String,Object> nowMap=new HashMap<>();

            for( int i = 0; i < fields.length; i++){
                Field f = fields[i];
                f.setAccessible(true);
                String prevName=f.getName();
                Object prveData=f.get(t1);
                for( int j= 0; j < fields2.length; j++){
                    Field f1 = fields[j];
                    f1.setAccessible(true);
                    String nowName=f1.getName();
                    Object nowData=f1.get(t2);
                    if(prveData instanceof String && nowData instanceof String){
                        if(prevName.equals(nowName)&&!prveData.equals(nowData)){
                            //值不相等
                            prevMap.put(prevName,prveData);
                            nowMap.put(nowName,nowData);
                            break;
                        }
                    }
                    if(prveData instanceof Integer && nowData instanceof Integer){
                        if(prevName.equals(nowName)&&!(prveData==nowData)){
                            //值不相等
                            prevMap.put(prevName,prveData);
                            nowMap.put(nowName,nowData);
                            break;
                        }
                    }
                    if(prveData instanceof Date && nowData instanceof Date){
                        if(prevName.equals(nowName)&& !(((Date) prveData).getTime() ==((Date) nowData).getTime())){
                            //值不相等
                            prevMap.put(prevName,prveData);
                            nowMap.put(nowName,nowData);
                            break;
                        }
                    }
                    if(prveData instanceof Long && nowData instanceof Long){
                        if(prevName.equals(nowName)&&!(((Long) prveData).longValue()==((Long) nowData).longValue())){
                            //值不相等
                            prevMap.put(prevName,prveData);
                            nowMap.put(nowName,nowData);
                            break;
                        }
                    }
                }
            }
            result.add(JSON.toJSONString(prevMap));
            result.add(JSON.toJSONString(nowMap));
            return result;
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
        return null;
    }
}

package com.gaoshan.aspect;

import com.alibaba.fastjson.JSON;
import com.gaoshan.aspect.auth.LoginAuth;
import com.gaoshan.aspect.auth.entity.InterfaceLog;
import com.gaoshan.aspect.auth.service.InterfaceLogService;
import com.gaoshan.aspect.flow.entity.ExceptionLogParam;
import com.gaoshan.aspect.flow.service.ExceptionLogService;
import com.gaoshan.config.RedisCache;
import com.gaoshan.enumeration.HTTP_REQUEST_TYPE;
import com.gaoshan.enumeration.HTTP_STATUS_CODE_ENUM;
import com.gaoshan.enumeration.TOKEN_TYPE;
import com.gaoshan.modules.wechat.user.domain.User;
import com.gaoshan.modules.wechat.user.service.WxMiniUserService;
import com.gaoshan.util.Result;
import com.ruoyi.common.utils.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * @author lumber
 *
 */
@Aspect
@Component
public class ExceptionAspect {
    @Autowired(required = false)
    ExceptionLogService exceptionLogService;
    @Autowired(required = false)
    InterfaceLogService interfaceLogService;
    @Autowired(required = false)
    WxMiniUserService wxMiniUserService;
    @Autowired
    RedisCache cache;
    @Value("${token.time}")
    private Integer cacheTime;
    @Pointcut("execution(* com.gaoshan.modules.*.*.controller..*(..))|| execution(* com.gaoshan.modules.*.*.service.impl.*(..) )")
    public void logPointCut() {
    }
    @Pointcut("@annotation(com.gaoshan.aspect.auth.LoginAuth)")
    public void loginPointcut() {
    }
    @Before("loginPointcut()")
    public void loginAuth(JoinPoint point){
        // ??????????????????????????????????????????????????????????????????
        MethodSignature signature = (MethodSignature) point.getSignature();
        // ??????????????????????????????
        Method method = signature.getMethod();
        // ????????????
        LoginAuth loginAuth = method.getAnnotation(LoginAuth.class);
        if(loginAuth.flag()){
            //??????????????????????????????
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            String token=request.getHeader(TOKEN_TYPE.TOKEN.name());
            if(StringUtils.isEmpty(token)){
                //token??????
                response.setStatus(HTTP_STATUS_CODE_ENUM.NO_PERMISSION.getStatus());
                throw new RuntimeException(HTTP_STATUS_CODE_ENUM.NO_PERMISSION.getMessage());
            }
            StringBuffer tokenStr=new StringBuffer(TOKEN_TYPE.TOKEN.getValue());
            tokenStr.append(token);
            if(cache.isContainsKey(tokenStr.toString())&&cache.isTimeOut(tokenStr.toString())){
                //????????????
                if(loginAuth.roleFlag()){
                    Result result=wxMiniUserService.getUserPermission();
                    if(result.getState()!=HTTP_STATUS_CODE_ENUM.SUCCESS_STATUS.getStatus()) {
                        response.setStatus(HTTP_STATUS_CODE_ENUM.NO_PERMISSION.getStatus());
                        throw new RuntimeException(HTTP_STATUS_CODE_ENUM.NO_PERMISSION.getMessage());
                    }
                }
                return;
            }else{
                response.setStatus(HTTP_STATUS_CODE_ENUM.TOKEN_NOT_FIND.getStatus());
                throw new RuntimeException(HTTP_STATUS_CODE_ENUM.TOKEN_NOT_FIND.getMessage());
            }

        }
    }
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point){
        long time=0;
        long beginTime = System.currentTimeMillis();
        Result result = new Result();
        try{
            // ????????????
            Object objRes=point.proceed();
            if(objRes instanceof Result){
                result = (Result)objRes;
            }

            // ??????????????????????????????????????????????????????????????????
            MethodSignature signature = (MethodSignature) point.getSignature();
            // ??????????????????????????????
            Method method = signature.getMethod();
            // ????????????
            LoginAuth loginAuth = method.getAnnotation(LoginAuth.class);
            time = System.currentTimeMillis() - beginTime;
            if(null!=loginAuth){
                saveLog(point, time,JSON.toJSONString(result),loginAuth);
            }
            // ????????????(??????)
            return result;
        }catch (Throwable e){
            e.printStackTrace();
            time = System.currentTimeMillis() - beginTime;
            saveErrorLog(point, time,JSON.toJSONString(result),e);
            return Result.ERROR(e.getMessage(),HTTP_STATUS_CODE_ENUM.ERROR_STATUS.getStatus());
        }
    }

    /**
     * ??????????????????
     */
    @Async
    void saveLog(ProceedingJoinPoint joinPoint, long time,String result,LoginAuth loginAuth ){
        // ??????request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        InterfaceLog interfaceLog = new InterfaceLog();

        String token=request.getHeader(TOKEN_TYPE.TOKEN.name());
        if(StringUtils.isNotEmpty(token)){
            StringBuffer tokenKey=new StringBuffer(TOKEN_TYPE.TOKEN.name());
            tokenKey.append("=");
            tokenKey.append(token);
            interfaceLog.setToken(tokenKey.toString());
            //token??????
            StringBuffer tokenStr=new StringBuffer(TOKEN_TYPE.TOKEN.getValue());
            tokenStr.append(token);
            User user=cache.getCacheObject(tokenStr.toString());
            //cache.deleteObject(tokenStr.toString());
            cache.putCacheObject(tokenStr.toString(),user,cacheTime);
        }

        interfaceLog.setResultParams(result);
        if (loginAuth != null) {
            interfaceLog.setAuthFlag(loginAuth.flag());
            // ????????????
            interfaceLog.setExecuteRemark(loginAuth.remark());
        }
        // ??????????????????
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        interfaceLog.setReqMethod(className + "." + methodName);
        // ???????????????,?????????GET?????????????????????
        if(HTTP_REQUEST_TYPE.GET.name().equals(request.getMethod().toUpperCase())){
            Enumeration<String> enumeration=request.getParameterNames();
            String params="";
            while(enumeration.hasMoreElements()){
                StringBuffer stringBuffer=new StringBuffer();
                String name=enumeration.nextElement();
                String value=request.getParameter(name);
                stringBuffer.append("&");
                stringBuffer.append(name);
                stringBuffer.append("=");
                stringBuffer.append(value);

                params+=stringBuffer.toString();
            }
            if (params.length() != 0) {
                params = params.substring(1, params.length());
            }
            interfaceLog.setReqParams(params);
        }else{
            Object[] args = joinPoint.getArgs();
            try {
                String params = JSON.toJSONString(args[0]);
                if(params.length()==0){
                    params=JSON.toJSONString(request.getParameterMap());
                }
                if(params.length()==0){
                    params=request.getMethod();
                }
                interfaceLog.setReqParams(params);
            } catch (Exception e2) {
                interfaceLog.setExcetionRemark(JSON.toJSONString(e2));
            }
        }
        interfaceLog.setReqUrl(request.getRequestURL().toString());
        // ??????IP??????
        interfaceLog.setReqIp(getIpAddr(request));
        interfaceLog.setCreateTime(new Date());
        interfaceLog.setExecuteTime(time/1000.0);
        interfaceLogService.save(interfaceLog);
    }

    /**
     * ??????????????????
     * @param joinPoint
     * @param time
     * @param result
     * @param e
     */
    private void saveErrorLog(ProceedingJoinPoint joinPoint, long time,String result,Throwable e) {
        // ??????request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        ExceptionLogParam interfaceLog = new ExceptionLogParam();
        String tokenKey="token";
        String token=request.getHeader(tokenKey);

        interfaceLog.setToken(tokenKey+"="+token);

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        String methodClass=className+"."+methodName;
        interfaceLog.setReqMethod(methodClass);
        interfaceLog.setExecuteRemark("??????????????????????????????");
        interfaceLog.setExceptionRemark(JSON.toJSONString(e));
        /****************************??????*****************************************/
        Map<String, Object> map = new HashMap<String, Object>();
        Object[] values = joinPoint.getArgs();
        // ???????????????,?????????GET?????????????????????
        if("GET".equals(request.getMethod().toUpperCase())){
            Enumeration<String> enumeration=request.getParameterNames();
            String params="";
            while(enumeration.hasMoreElements()){
                StringBuffer stringBuffer=new StringBuffer();
                String name=enumeration.nextElement();
                String value=request.getParameter(name);
                stringBuffer.append("&");
                stringBuffer.append(name);
                stringBuffer.append("=");
                stringBuffer.append(value);

                params+=stringBuffer.toString();
            }
            if (params.length() != 0) {
                params = params.substring(1, params.length());
            }
            interfaceLog.setReqParams(params);
        }else{
            Object[] args = joinPoint.getArgs();
            try {
                String params = JSON.toJSONString(args[0]);
                if(params.length()==0){
                    params=JSON.toJSONString(request.getParameterMap());
                }
                if(params.length()==0){
                    params=request.getMethod();
                }
                interfaceLog.setReqParams(params);
            } catch (Exception e2) {
                interfaceLog.setExceptionRemark(JSON.toJSONString(e2));
            }
        }

        /*********************************************************************/
        double times=time/1000.0;
        interfaceLog.setExecuteTime(times);
        interfaceLog.setResultParams(result);
        interfaceLog.setCreateTime(new Date());
        interfaceLog.setReqIp(getIpAddr(request));
        exceptionLogService.save(interfaceLog);
    }
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}

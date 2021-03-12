package com.company.project.common.aop.aspect;

import com.alibaba.fastjson.JSON;
import com.company.project.common.aop.annotation.BrowsingAnnotation;
import com.company.project.entity.SysBrowsingFileHistory;
import com.company.project.entity.SysBrowsingUserHistory;
import com.company.project.entity.SysUser;
import com.company.project.mapper.SysBrowsingUserHistoryMapper;
import com.company.project.mapper.SysUserMapper;
import com.company.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * 浏览历史切面
 * @author mc
 * @version V1.0
 * @date 2021/3/12
 */
@Aspect
@Component
@Slf4j
public class BrowsingAspect {
    @Lazy
    @Resource
    SysBrowsingUserHistoryMapper sysBrowsingUserHistoryMapper;

    @Lazy
    @Resource
    SysUserMapper sysUserMapper;



    @Pointcut("@annotation(com.company.project.common.aop.annotation.BrowsingAnnotation)")
    public void logPointCut() {

    }

    /**
     * 环绕增强
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        try {
            saveSysBrowsing(point, time);
        } catch (Exception e) {
            log.error("sysLog,exception:{}", e, e);
        }

        return result;
    }


    public void saveSysBrowsing(ProceedingJoinPoint joinPoint, long time){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        BrowsingAnnotation browsingAnnotation = method.getAnnotation(BrowsingAnnotation.class);
        String sign = browsingAnnotation.sign();
        Object[] args = joinPoint.getArgs();
        if(sign.equals("1")){  //用户
            SysBrowsingUserHistory sysBrowsingUserHistory = new SysBrowsingUserHistory();
            SysUser sysUser = sysUserMapper.selectByUsername(args[0].toString());
            sysBrowsingUserHistory.setUsername(sysUser.getId());
            sysBrowsingUserHistory.setNowBrowsUserID("1");
            sysBrowsingUserHistoryMapper.insert(sysBrowsingUserHistory);

        }else if(sign.equals("2")){  //文件
            SysBrowsingFileHistory sysBrowsingFileHistory = new SysBrowsingFileHistory();
            System.out.println(sysBrowsingFileHistory);

        }








    }

}

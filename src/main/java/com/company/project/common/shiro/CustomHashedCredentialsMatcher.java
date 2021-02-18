package com.company.project.common.shiro;

import com.company.project.common.exception.BusinessException;
import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;

/**
* @author machao
* @version V1.1
* @date 2021/2/4
*/
@Slf4j
public class CustomHashedCredentialsMatcher extends SimpleCredentialsMatcher {

    @Lazy  //第一次调用时进行加载 懒加载
    @Autowired
    private RedisService redisDb;
    @Value("${spring.redis.key.prefix.userToken}")
    private String userTokenPrefix;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String accessToken = (String) token.getPrincipal();
        log.info("AuthenticationToken 用户token信息:  ----> " + token.getCredentials() + " --- " +token.getPrincipal());
        log.info("AuthenticationInfo 用户info -----> " + info.getCredentials() + " --- " +info.getPrincipals());
        if (!redisDb.exists(userTokenPrefix + accessToken)) {
            SecurityUtils.getSubject().logout();
            throw new BusinessException(BaseResponseCode.TOKEN_ERROR);
        }
        return true;
    }
}

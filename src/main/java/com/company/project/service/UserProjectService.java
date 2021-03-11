package com.company.project.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 记录用户当前所浏览的项目信息ID
 * @author mc
 * @version V1.0
 * @date 2021/3/4
 */
@Service
public class UserProjectService {
    @Resource
    private RedisService redisService;
    @Value("${spring.redis.key.prefix.fileKey}")
    private String fileKeyPrefix;

    /**
     * 设置用户所在项目
     * @param username
     * @param proName
     */
    public void setFileKeyPrefix(String username, String proName){
        redisService.set(fileKeyPrefix + "#" +username, proName);
    }

    /**
     * 根据file KEY 获取value
     * @return
     */
    public String getFileValue(String username){
        String s = redisService.get(fileKeyPrefix + "#" + username);
        return s;
    }

}

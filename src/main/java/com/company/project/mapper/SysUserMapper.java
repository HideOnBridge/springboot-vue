package com.company.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.project.entity.SysUser;
import org.apache.ibatis.annotations.Select;

/**
 * 用户 Mapper
 *
 * @author
 * @version V1.0
 * @date 2020年3月18日
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("select * from sys_user where username = #{username}")
    SysUser selectByUsername(String username);
}

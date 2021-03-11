package com.company.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.project.entity.SysPermission;
import org.apache.ibatis.annotations.Select;

/**
 * 菜单权限 Mapper
 *
 * @author mc
 * @version V1.0
 * @date 2020年3月18日
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    @Select("select id from sys_permission where name = #{name}")
    SysPermission getIdByName(String name);

}

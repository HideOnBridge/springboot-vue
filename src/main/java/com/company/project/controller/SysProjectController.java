package com.company.project.controller;


import com.company.project.common.aop.annotation.LogAnnotation;
import com.company.project.common.utils.DataResult;
import com.company.project.entity.SysFileProject;
import com.company.project.entity.SysPermission;
import com.company.project.entity.SysRolePermission;
import com.company.project.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 项目管理
 * @author mc
 * @version V1.0
 * @date 2021/2/19
 */
@RestController
@RequestMapping("/sys")
@Api(tags = "项目管理模块")
@Slf4j
public class SysProjectController {

    @Resource
    SysProjectService sysProjectService;
    @Resource
    private PermissionService permissionService;
    @Resource
    HttpSessionService httpSessionService;
    @Resource
    RolePermissionService rolePermissionService;
    @Resource
    private UserProjectService userProjectService;


    @PostMapping("/project/add")
    @ApiOperation(value = "新增项目",notes = "新增项目")
    @RequiresPermissions("sys:project:add")
    @LogAnnotation(title = "项目管理", action = "新增项目")
    public DataResult addProject(@RequestBody SysFileProject sts){
        log.info("success ----> " + sts);
        int i = 4;
        sysProjectService.save(sts);
        //间接add 权限'菜单'
        SysPermission vo = new SysPermission();
        vo.setStatus(1);
        vo.setName(sts.getProjectName());
        vo.setPidName("项目列表");
        vo.setPid("1362283733155700737");
        vo.setType(2);
        vo.setUrl("/index/project");
        vo.setOrderNum(i++);
        vo.setTarget("_self");
        permissionService.save(vo);  //自动添加到权限表中
        SysPermission sysPermission = permissionService.getIdByName(sts.getProjectName());
        String currentUserId = httpSessionService.getCurrentUserId();
        SysRolePermission sysRolePermission = new SysRolePermission();
        sysRolePermission.setPermissionId(sysPermission.getId());
        sysRolePermission.setRoleId(currentUserId);
        rolePermissionService.save(sysRolePermission);

        return DataResult.success();
    }


    @PostMapping("/project/clickFile/redis")
    @ApiOperation(value = "redis 存放文件id",notes = "设置用户点击项目后，redis 存放文件id")
    public DataResult setRedisFile(@RequestBody String value){
        String [] strs = value.split("#");
        userProjectService.setFileKeyPrefix(strs[0],strs[1]);
        return DataResult.success();
    }


}

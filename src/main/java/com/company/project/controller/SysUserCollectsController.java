package com.company.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.common.aop.annotation.LogAnnotation;
import com.company.project.common.utils.DataResult;
import com.company.project.entity.SysFileCollects;
import com.company.project.entity.SysLog;
import com.company.project.entity.SysUser;
import com.company.project.mapper.SysUserMapper;
import com.company.project.service.FileService;
import com.company.project.service.SysFileCollectsService;
import com.company.project.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import sun.security.timestamp.TSRequest;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "收藏管理")
@RequestMapping("/sys")
@Slf4j
public class SysUserCollectsController {

    @Resource
    private SysFileCollectsService collectsService;
    @Resource
    SysUserMapper sysUserMapper;

    @PostMapping("/user/collects/list")
    @ApiOperation(value = "分页获取收藏列表接口")
    @RequiresPermissions("sys:user:collects:list")
    @LogAnnotation(title = "收藏管理", action = "分页获取收藏列表")
    public DataResult pageInfo(@RequestBody SysFileCollects vo,@RequestParam("userid") String userid){  //！！@RequestBody 分页 ; @RequestParam 获取用户名
        log.info("===> " + userid);
        SysUser sysUser = sysUserMapper.selectByUsername(userid);
        Page page = new Page(vo.getPage(), vo.getLimit());
        LambdaQueryWrapper<SysFileCollects> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(SysFileCollects::getCreateTime);
        queryWrapper.eq(SysFileCollects::getUserid,sysUser.getId());

        return DataResult.success(collectsService.page(page,queryWrapper));


    }

    @DeleteMapping("/user/collects/delete")
    @RequiresPermissions("sys:user:collects:delete")
    @ApiOperation(value = "删除收藏")
    @LogAnnotation(title = "收藏管理", action = "删除收藏")
    public DataResult deleteCollects(@RequestBody @ApiParam(value = "收藏id#用户名") String params){
        System.out.println("参数为 ====> " + params);
        String [] strs = params.split("#");
        collectsService.delete(strs[1],strs[0]);
        return DataResult.success();
    }


    @PostMapping("/user/collects/remarks")
    @RequiresPermissions("sys:user:collects:remarks")
    @LogAnnotation(title = "收藏管理", action = "文件收藏备注")
    @ApiOperation(value = "添加收藏文件备注")
    public DataResult collectsRemarks(@RequestBody String value){
        String [] params = value.split("#");
        SysFileCollects sfc = new SysFileCollects();
        sfc.setId(params[0]);
        sfc.setUserid(params[1]);
        sfc.setRemarks(params[2]);
        collectsService.remarksCollects(sfc);
        return DataResult.success();
    }
}

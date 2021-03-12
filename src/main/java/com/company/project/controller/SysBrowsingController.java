package com.company.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.common.aop.annotation.LogAnnotation;
import com.company.project.common.utils.DataResult;
import com.company.project.entity.SysBrowsingUserHistory;
import com.company.project.entity.SysLog;
import com.company.project.entity.SysUser;
import com.company.project.mapper.SysBrowsingUserHistoryMapper;
import com.company.project.mapper.SysUserMapper;
import com.company.project.service.SysBrowsingUserHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "系统管理-浏览历史")
@RequestMapping("/sys")
@Slf4j
public class SysBrowsingController {
    @Resource
    private SysBrowsingUserHistoryService historyUserService;
    @Resource
    private SysUserMapper userMapper;

    @PostMapping("/browsing/list")
    @ApiOperation(value = "浏览人员历史",notes = "分页获取浏览历史")
    @RequiresPermissions("sys:browsing:list")
    @LogAnnotation(title = "分页获取浏览人员历史", action = "分页获取人员pageInfo")
    public DataResult sysBrowsing(@RequestBody SysBrowsingUserHistory sys, @RequestParam String username){
        SysUser sysUser = userMapper.selectByUsername(username);
        Page page = new Page(sys.getPage(), sys.getLimit());
        LambdaQueryWrapper<SysBrowsingUserHistory> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysBrowsingUserHistory::getNowBrowsUserID, sysUser.getId());
        queryWrapper.orderByDesc(SysBrowsingUserHistory::getCreateTime);
        return DataResult.success(historyUserService.page(page, queryWrapper));
    }









    @PostMapping("/browsing/remarks")
    @LogAnnotation(title = "浏览历史-人员-备注", action = "人员-备注")
    public DataResult sysBrowsingRemarks(@RequestBody String value){
        String [] params = value.split("#");
        SysBrowsingUserHistory sysBrowsingUserHistory = new SysBrowsingUserHistory();
        sysBrowsingUserHistory.setId(params[0]);
        sysBrowsingUserHistory.setRemarks(params[2]);
        historyUserService.updateById(sysBrowsingUserHistory);
        return DataResult.success();
    }
}

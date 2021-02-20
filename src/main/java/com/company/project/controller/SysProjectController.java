package com.company.project.controller;


import com.company.project.common.utils.DataResult;
import com.company.project.service.SysProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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


    @RequestMapping("/project/add")
    @ApiOperation(value = "新增项目",notes = "新增项目")
    public DataResult addProject(){
        //
        return DataResult.success();
    }

}

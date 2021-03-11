package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.entity.SysFileCollects;
import com.company.project.entity.SysProjectsFiles;
import com.company.project.mapper.SysProjectsFilesMapper;
import com.company.project.service.SysProjectsFilesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysProjectsFilesServiceImpl extends ServiceImpl<SysProjectsFilesMapper, SysProjectsFiles> implements SysProjectsFilesService {
    @Resource
    private SysProjectsFilesMapper sysProjectsFilesMapper;
    @Override
    public void remarks(SysProjectsFiles sysProjects) {
        LambdaUpdateWrapper<SysProjectsFiles> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(SysProjectsFiles::getId, sysProjects.getId()).eq(SysProjectsFiles::getUploadUser, sysProjects.getUploadUser()).set(SysProjectsFiles::getRemarks, sysProjects.getRemarks());
        sysProjectsFilesMapper.update(null, lambdaUpdateWrapper);
    }

}

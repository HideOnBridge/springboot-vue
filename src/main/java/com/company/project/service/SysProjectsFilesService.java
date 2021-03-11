package com.company.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.entity.SysProjectsFiles;

public interface SysProjectsFilesService extends IService<SysProjectsFiles> {

    /**
     * 文件备注
     * @param sysProjects
     */
    void remarks(SysProjectsFiles sysProjects);
}

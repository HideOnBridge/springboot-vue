package com.company.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.common.utils.DataResult;
import com.company.project.entity.SysFilesEntity;
import com.company.project.entity.SysProjectsFiles;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件上传 服务类
 *
 * @author mc
 * @version V1.3
 * @date
 */
public interface SysFilesService extends IService<SysFilesEntity> {

    /**
     * 保存文件
     * @param file
     * @return
     */
    DataResult saveFile(MultipartFile file);


    /**
     * 根据Id删除文件
     * @param ids
     */
    void removeByIdsAndFiles(List<String> ids);



}


package com.company.project.service;

import com.company.project.entity.FileDocument;


import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface FileService {
    /**
     * 保存文件
     */
    FileDocument saveFile(FileDocument file);

    /*
    *
    *
    * */
    String uploadFileToGridFS(InputStream in, String contentType);

    /**
     * 删除文件
     */
    void removeFile(String id);

    /**
     * 根据id获取文件
     */
    Optional<FileDocument> getFileById(String id);

    /**
     * 分页查询，按上传时间降序
     * @return
     */
    List<FileDocument> listFilesByPage(int pageIndex, int pageSize);

    List<FileDocument> findAll();
}

package com.company.project.service.impl;


import cn.hutool.core.util.IdUtil;
import com.company.project.entity.FileDocument;
import com.company.project.mapper.FileRepository;
import com.company.project.service.FileService;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;



    @Override
    public FileDocument saveFile(FileDocument file) {
        file = mongoTemplate.save(file);
        return file;
    }


    /**
     * 上传文件到Mongodb的GridFs中
     * @param in
     * @param contentType
     * @return
     */
    @Override
    public String uploadFileToGridFS(InputStream in , String contentType){
        String gridfsId = IdUtil.simpleUUID();
        //将文件存储进GridFS中
        gridFsTemplate.store(in, gridfsId , contentType);
        return gridfsId;
    }




    /**
     * 删除文件
     * @param id
     */
    @Override
    public void removeFile(String id) {
        //根据id查询文件
        FileDocument fileDocument = mongoTemplate.findById(id , FileDocument.class );
        if(fileDocument!=null){
            //根据文件ID删除fs.files和fs.chunks中的记录
            Query deleteFileQuery = new Query().addCriteria(Criteria.where("filename").is(fileDocument.getGridfsId()));
            gridFsTemplate.delete(deleteFileQuery);
            //删除集合fileDocment中的数据
            Query deleteQuery=new Query(Criteria.where("id").is(id));
            mongoTemplate.remove(deleteQuery,FileDocument.class);

        }
    }

    /**
     * 根据id查看文件
     * @param id
     * @return
     */
    @Override
    public Optional<FileDocument> getFileById(String id){
        FileDocument fileDocument = mongoTemplate.findById(id , FileDocument.class );
        if(fileDocument != null){
            Query gridQuery = new Query().addCriteria(Criteria.where("filename").is(fileDocument.getGridfsId()));
            try {
                //根据id查询文件
                GridFSFile fsFile = gridFsTemplate.findOne(gridQuery);
                //打开流下载对象
                GridFSDownloadStream in = gridFSBucket.openDownloadStream(fsFile.getObjectId());
                log.info("gridfs 流" + in);

                if(in.getGridFSFile().getLength() > 0){
                    //获取流对象
                    GridFsResource resource = new GridFsResource(fsFile, in);
                    InputStream inStream = resource.getInputStream();
                    byte [] b = getBytes(inStream);

                    //获取数据
                    fileDocument.setContent(new String(b,"utf-8"));
                    return Optional.of(fileDocument);
                }else {
                    fileDocument = null;
                    return Optional.empty();
                }
            }catch (IOException ex){
                ex.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    /**
     * 分页列出文件
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<FileDocument> listFilesByPage(int pageIndex, int pageSize) {
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "uploadDate"));
        long skip = (pageIndex -1) * pageSize;
        query.skip(skip);
        query.limit(pageSize);
        Field field = query.fields();
        field.exclude("content");
        List<FileDocument> files = mongoTemplate.find(query , FileDocument.class );
        return files;

    }

    @Override
    public List<FileDocument> findAll() {
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "uploadDate"));
        List<FileDocument> files = mongoTemplate.find(query , FileDocument.class );

        return files;
    }


    private byte[] getBytes(InputStream inputStream) throws Exception{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int i = 0;
        while (-1!=(i=inputStream.read(b))){
        bos.write(b,0,i);
        }
        return bos.toByteArray();
       }
}

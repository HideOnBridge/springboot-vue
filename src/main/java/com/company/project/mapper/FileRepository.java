package com.company.project.mapper;


import com.company.project.entity.FileDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<FileDocument,String> {
}

package com.raghu.cloudstorage.services;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.raghu.cloudstorage.models.File;
import com.raghu.cloudstorage.models.UserEntity;
import com.raghu.cloudstorage.repository.FileRepository;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.io.IOException;

@Service
public class FileService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private FileRepository fileRepository;

    public File storeFile(MultipartFile file, UserEntity user) throws IOException {
        ObjectId fileId = gridFsTemplate.store(
                file.getInputStream(),
                file.getOriginalFilename(),
                file.getContentType());

        File fileMetadata = new File();
        fileMetadata.setFileId(fileId.toString());
        fileMetadata.setFileName(file.getOriginalFilename());
        fileMetadata.setCreatedAt(LocalDateTime.now());
        fileMetadata.setUser(user);

        fileRepository.save(fileMetadata);

        return fileMetadata;
    }

    public GridFsResource getFile(String fileId) {
        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(fileId)));
        return gridFsTemplate.getResource(gridFSFile);
    }

    public void deleteFile(String fileId) {
        gridFsTemplate.delete(new Query(Criteria.where("_id").is(fileId)));
        fileRepository.deleteById(fileId);
    }

    public List<File> getFilesByUser(UserEntity user) {
        return fileRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public List<File> searchFiles(Long userId, String searchTerm) {
        return fileRepository.searchFilesByFileName(userId, searchTerm);
    }

}
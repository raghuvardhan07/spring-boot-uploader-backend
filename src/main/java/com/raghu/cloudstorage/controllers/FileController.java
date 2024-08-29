package com.raghu.cloudstorage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import com.raghu.cloudstorage.models.File;
import com.raghu.cloudstorage.models.UserEntity;
import com.raghu.cloudstorage.services.FileService;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<File> uploadFile(@RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserEntity user) throws IOException {
        System.out.println(user);
        File newFile = fileService.storeFile(file, user);

        return ResponseEntity.ok(newFile);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String fileId) throws IOException {
        GridFsResource resource = fileService.getFile(fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.parseMediaType(resource.getContentType()))
                .body(new InputStreamResource(resource.getInputStream()));
    }

    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable String fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<File>> getFiles(@AuthenticationPrincipal UserEntity currentUser) {
        System.out.println("------------------");
        System.out.println(currentUser.getUsername());
        List<File> files = fileService.getFilesByUser(currentUser);
        return ResponseEntity.ok(files);
    }

    @GetMapping("/search")
    public ResponseEntity<List<File>> searchFiles(@AuthenticationPrincipal UserEntity currentUser,
            @RequestParam("query") String searchTerm) {
        System.out.println("---------------------------------");
        System.out.println(currentUser);
        System.out.println(searchTerm);
        List<File> files = fileService.searchFiles(currentUser.getId(), searchTerm);
        System.out.println(files.size());
        return ResponseEntity.ok(files);
    }

}
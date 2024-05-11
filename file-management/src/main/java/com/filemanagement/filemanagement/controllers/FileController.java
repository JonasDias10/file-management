package com.filemanagement.filemanagement.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.filemanagement.filemanagement.services.StorageService;
import java.io.IOException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/v1/file")
public class FileController {
      private final StorageService storageService;

      public FileController(StorageService storageService) {
            this.storageService = storageService;
      }

      @PostMapping("/upload")
      public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
            return storageService.store(file);
      }

      @GetMapping("/download/{fileName}")
      public ResponseEntity<Object> download(@PathVariable("fileName") String fileName) {
            return storageService.download(fileName);
      }

      @DeleteMapping("/delete/{fileName}")
      public ResponseEntity<String> delete(@PathVariable("fileName") String fileName) {
            return storageService.delete(fileName);
      }

      @GetMapping("/all")
      public ResponseEntity<List<String>> allFiles() throws IOException {
            return storageService.allFiles();
      }

}

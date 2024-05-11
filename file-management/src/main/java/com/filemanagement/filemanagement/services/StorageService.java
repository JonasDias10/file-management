package com.filemanagement.filemanagement.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.filemanagement.filemanagement.configs.StorageProperties;

@Service
public class StorageService {
      private final Path rootLocation;

      public StorageService(StorageProperties storageProperties) {
            this.rootLocation = Path.of(storageProperties.getLocation()).toAbsolutePath().normalize();
      }

      public ResponseEntity<String> store(MultipartFile file) {
            try {
                  if (file.isEmpty()) {
                        return ResponseEntity.badRequest().body("Failed to store empty file");
                  }

                  String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                  Path targetLocation = this.rootLocation.resolve(fileName);
                  file.transferTo(targetLocation);

                  return ResponseEntity.ok("File successfully stored");
            } catch (Exception exception) {
                  return ResponseEntity.badRequest().body("Failed to store file");
            }
      }

      public ResponseEntity<Object> download(String fileName) {
            try {
                  Path targetLocation = this.rootLocation.resolve(fileName);
                  Resource resource = new UrlResource(targetLocation.toUri());

                  if (!resource.exists() || !resource.isReadable()) {
                        return ResponseEntity.notFound().build();
                  }

                  return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                              "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);

            } catch (IOException exception) {
                  return ResponseEntity.badRequest().body("Failed to download file: " + fileName);
            }
      }

      public ResponseEntity<String> delete(String fileName) {
            try {
                  Path targetLocation = this.rootLocation.resolve(fileName);
                  Files.delete(targetLocation);

                  return ResponseEntity.ok("File successfully deleted");
            } catch (IOException e) {
                  return ResponseEntity.badRequest().body("Failed to delete file");
            }
      }

      public ResponseEntity<List<String>> allFiles() throws IOException {
            if (!Files.exists(this.rootLocation)) {
                  return ResponseEntity.notFound().build();
            }

            List<String> fileNames = Files.list(this.rootLocation)
                        .map(Path::getFileName)
                        .map(Path::toString)
                        .collect(Collectors.toList());

            return ResponseEntity.ok(fileNames);
      }

}

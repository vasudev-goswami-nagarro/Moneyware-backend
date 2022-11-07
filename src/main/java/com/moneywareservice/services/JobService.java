package com.moneywareservice.services;

import com.moneywareservice.exceptions.CustomException;
import com.moneywareservice.repository.DocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

@Slf4j
@Service
public class JobService {

    @Value("${app.file.upload-dir}")
    String fileStorageLocation;
    @Autowired
    SFTPService sftpService;

    @Autowired
    DocumentRepository documentRepository;

//    @Scheduled(cron = "0 0 */1 * * *") // 1 hour
    @Scheduled(cron = "0 * * * * *") // 1 min
    public void upload() {
        documentRepository.findAll().forEach(document -> {
            log.info("Cron job started at :: " + Calendar.getInstance().getTime());
            if (document.getStatus().equals("Completed")) {
                String filePath = fileStorageLocation + "/" + document.getFileName();
                Path path = Paths.get(filePath);
                if(Files.exists(path)) {
                    boolean isUploaded = sftpService.uploadFile(filePath, "/" + document.getFileName());
                    if (isUploaded) {
                        log.info("Uploaded successfully : "+ document.getFileName());
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            throw new CustomException(HttpStatus.NOT_FOUND,
                                    "Error deleting file: " + e.getMessage());
                        }
                    } else {
                        log.info("Uploaded failed : "+ document.getFileName());
                    }
                }
            }
        });
    }
}

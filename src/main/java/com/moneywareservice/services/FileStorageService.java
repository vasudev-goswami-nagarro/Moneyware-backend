package com.moneywareservice.services;


import com.moneywareservice.GenericResponse;
import com.moneywareservice.exceptions.CustomException;
import com.moneywareservice.model.Customer;
import com.moneywareservice.model.Document;
import com.moneywareservice.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

import static com.moneywareservice.util.Constants.COMMENT;
import static com.moneywareservice.util.Constants.COMPLETED;

@Slf4j
@Service
public class FileStorageService {
    private final Path fileStorageLocation;

    @Autowired
    DocumentService documentService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    public FileStorageService(Environment env) {
        this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir", "./uploads/files"))
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new CustomException(HttpStatus.BAD_REQUEST,
                    "Could not create the directory where the uploaded files will be stored." + ex.getMessage());
        }
    }

    public Object saveFile(MultipartFile file, String documentType) {
        String fileName = file.getOriginalFilename();
        Customer customer = getCurrentUserJWT();
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        log.info("targetLocation : {}", targetLocation);
        try {
            long fileSize = Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("copy : {}", fileSize);
            Document document = Document.builder()
                    .documentType(documentType)
                    .fileName(fileName)
                    .size(fileSize/1024)
                    .status(COMPLETED)
                    .timestamp(LocalDate.now())
                    .comment(COMMENT)
                    .customerId(customer)
                    .build();
            log.info("File uploaded successfully");
            return new GenericResponse<>(documentService.saveDocument(document));
        } catch (IOException e) {
            throw new CustomException(HttpStatus.BAD_REQUEST,
                    "Unable to save document" + e.getMessage());
        }
    }

    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user.
     */
    public Customer getCurrentUserJWT() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return customerRepository.findByUsername(userName).orElseThrow(() -> {
            throw new CustomException(HttpStatus.NOT_FOUND,
                    "User: " + userName + " not found");
        });
    }
}

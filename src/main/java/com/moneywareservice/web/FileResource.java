package com.moneywareservice.web;


import com.moneywareservice.services.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/files")
public class FileResource {

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * {@code POST  /} : Upload new files.
     */
    @Operation(summary = "Upload file in local server")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File uploaded"),
    })
    @PostMapping("/upload")
    public Object uploadFile(@RequestParam(name = "file", required = true) MultipartFile file,
                             @RequestParam("documentType") String documentType) {
        return fileStorageService.saveFile(file, documentType);
    }
}

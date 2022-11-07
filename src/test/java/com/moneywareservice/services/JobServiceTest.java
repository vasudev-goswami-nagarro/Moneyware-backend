package com.moneywareservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.moneywareservice.exceptions.CustomException;
import com.moneywareservice.model.Customer;
import com.moneywareservice.model.Document;
import com.moneywareservice.repository.DocumentRepository;

import java.time.LocalDate;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JobService.class})
@ExtendWith(SpringExtension.class)
class JobServiceTest {
    @MockBean
    private DocumentRepository documentRepository;

    @Autowired
    private JobService jobService;

    @MockBean
    private SFTPService sFTPService;

    /**
     * Method under test: {@link JobService#upload()}
     */
    @Test
    void testUpload() {
        when(documentRepository.findAll()).thenReturn(new ArrayList<>());
        jobService.upload();
        verify(documentRepository).findAll();
        assertEquals("${app.file.upload-dir}", jobService.fileStorageLocation);
    }

    /**
     * Method under test: {@link JobService#upload()}
     */
    @Test
    void testUpload2() {
        Customer customer = new Customer();
        customer.setId(123L);
        customer.setPassword("abc");
        customer.setUsername("cba");

        Document document = new Document();
        document.setComment("Comment");
        document.setCustomerId(customer);
        document.setDocumentType("Document Type");
        document.setFileName("foo.txt");
        document.setId(1);
        document.setSize(3L);
        document.setStatus("Status");
        document.setTimestamp(LocalDate.ofEpochDay(1L));

        ArrayList<Document> documentList = new ArrayList<>();
        documentList.add(document);
        when(documentRepository.findAll()).thenReturn(documentList);
        jobService.upload();
        verify(documentRepository).findAll();
        assertEquals("${app.file.upload-dir}", jobService.fileStorageLocation);
    }

    /**
     * Method under test: {@link JobService#upload()}
     */
    @Test
    void testUpload3() {
        Customer customer = new Customer();
        customer.setId(123L);
        customer.setPassword("abc");
        customer.setUsername("cba");

        Document document = new Document();
        document.setComment("Comment");
        document.setCustomerId(customer);
        document.setDocumentType("Document Type");
        document.setFileName("foo.txt");
        document.setId(1);
        document.setSize(3L);
        document.setStatus("Status");
        document.setTimestamp(LocalDate.ofEpochDay(1L));

        Customer customer1 = new Customer();
        customer1.setId(123L);
        customer1.setPassword("abc");
        customer1.setUsername("cba");

        Document document1 = new Document();
        document1.setComment("Completed");
        document1.setCustomerId(customer1);
        document1.setDocumentType("Completed");
        document1.setFileName("foo.txt");
        document1.setId(1);
        document1.setSize(3L);
        document1.setStatus("Completed");
        document1.setTimestamp(LocalDate.ofEpochDay(1L));

        ArrayList<Document> documentList = new ArrayList<>();
        documentList.add(document1);
        documentList.add(document);
        when(documentRepository.findAll()).thenReturn(documentList);
        jobService.upload();
        verify(documentRepository).findAll();
        assertEquals("${app.file.upload-dir}", jobService.fileStorageLocation);
    }

    /**
     * Method under test: {@link JobService#upload()}
     */
    @Test
    void testUpload4() {
        when(documentRepository.findAll()).thenThrow(new CustomException(HttpStatus.CONTINUE, "An error occurred"));
        assertThrows(CustomException.class, () -> jobService.upload());
        verify(documentRepository).findAll();
    }
}


package com.moneywareservice.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.moneywareservice.model.Customer;
import com.moneywareservice.model.Document;
import com.moneywareservice.repository.DocumentRepository;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DocumentService.class})
@ExtendWith(SpringExtension.class)
class DocumentServiceTest {
    @MockBean
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentService documentService;

    @Test
    void testSaveDocument() {
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
        when(documentRepository.save((Document) any())).thenReturn(document);

        Customer customer1 = new Customer();
        customer1.setId(123L);
        customer1.setPassword("abc");
        customer1.setUsername("cba");

        Document document1 = new Document();
        document1.setComment("Comment");
        document1.setCustomerId(customer1);
        document1.setDocumentType("Document Type");
        document1.setFileName("foo.txt");
        document1.setId(1);
        document1.setSize(3L);
        document1.setStatus("Status");
        document1.setTimestamp(LocalDate.ofEpochDay(1L));
        assertSame(document, documentService.saveDocument(document1));
        verify(documentRepository).save((Document) any());
    }
}


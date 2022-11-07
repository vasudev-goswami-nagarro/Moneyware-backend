package com.moneywareservice.services;

import com.moneywareservice.model.Document;
import com.moneywareservice.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
    @Autowired
    DocumentRepository documentRepository;

    public Object saveDocument(Document document) {
        return documentRepository.save(document);
    }
}

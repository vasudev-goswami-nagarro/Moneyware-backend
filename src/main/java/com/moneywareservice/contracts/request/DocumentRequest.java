package com.moneywareservice.contracts.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentRequest {
    String documentType;

    String status;
}

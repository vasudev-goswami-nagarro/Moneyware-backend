package com.moneywareservice.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = {
        @Index(name = "DS", columnList = "comment,timestamp,customer_id,document_type,filename", unique = true),
})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Integer id;

    Long size;

    @Column(name = "filename")
    String fileName;

    LocalDate timestamp;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customerId;

    @Column(name = "document_type")
    String documentType;

    String status;

    String comment;
}

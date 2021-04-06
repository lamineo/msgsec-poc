package com.serradj.lamine.securedmessagepoc.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "SECURED_MESSAGE_ATTACHMENT")
@Data
public class SecuredMessageAttachmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Lob
    @Column(name = "ATTACHMENT")
    @JsonIgnore
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private SecuredMessageEntity securedMessageEntity;



}

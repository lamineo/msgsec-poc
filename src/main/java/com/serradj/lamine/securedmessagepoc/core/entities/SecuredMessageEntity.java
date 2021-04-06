package com.serradj.lamine.securedmessagepoc.core.entities;

import lombok.Data;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SECURED_MESSAGE")
@Data
public class SecuredMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "CLIENT")
    private String client;

    @Column(name = "CONV_ID")
    private String convId;

    @OneToMany(
            mappedBy = "securedMessageEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SecuredMessageAttachmentEntity> attachment = new ArrayList<>();

    public void addAttachment(SecuredMessageAttachmentEntity securedMessageAttachmentEntity) {
        if (securedMessageAttachmentEntity == null) return;
        attachment.add(securedMessageAttachmentEntity);
        securedMessageAttachmentEntity.setSecuredMessageEntity(this);
    }

    public void removeAttachment(SecuredMessageAttachmentEntity securedMessageAttachmentEntity) {
        if (securedMessageAttachmentEntity == null) return;
        attachment.remove(securedMessageAttachmentEntity);
        securedMessageAttachmentEntity.setSecuredMessageEntity(null);
    }


}

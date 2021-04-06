package com.serradj.lamine.securedmessagepoc.core.controller;

import com.serradj.lamine.securedmessagepoc.core.entities.SecuredMessageAttachmentEntity;
import com.serradj.lamine.securedmessagepoc.core.entities.SecuredMessageEntity;
import com.serradj.lamine.securedmessagepoc.core.repo.SecuredMessageAttachmentRepo;
import com.serradj.lamine.securedmessagepoc.core.repo.SecuredMessageRepo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class SecuredMessageController {

    private final SecuredMessageRepo securedMessageRepo;
    private final SecuredMessageAttachmentRepo securedMessageAttachmentRepo;

    @Autowired
    public SecuredMessageController(SecuredMessageRepo securedMessageRepo, SecuredMessageAttachmentRepo securedMessageAttachmentRepo) {
        this.securedMessageRepo = securedMessageRepo;
        this.securedMessageAttachmentRepo = securedMessageAttachmentRepo;
    }

    @SneakyThrows
    @PostMapping(value = "/secured-message",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> createSecuredMessageWithAttachment(
            @RequestPart("files") MultipartFile files,
            @RequestPart("client") String client,
            @RequestPart("idConv") String idConv,
            @RequestPart("message") String content) {

        log.info("BINDED {}", content);
        SecuredMessageEntity msg = new SecuredMessageEntity();
        SecuredMessageAttachmentEntity attch = new SecuredMessageAttachmentEntity();
        attch.setData(files.getBytes());
        log.info("content.type {}",files.getContentType());
        msg.setMessage(content);
        msg.setClient(client);
        msg.setConvId(idConv);
        msg.addAttachment(attch);
        securedMessageRepo.saveAndFlush(msg);
        return new ResponseEntity<String>("SUCCESS",HttpStatus.CREATED);
    }
    @SneakyThrows
    @PostMapping(value = "/secured-messages",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> createSecuredMessageWithAttachments(
            @RequestPart("files") MultipartFile[] files,
            @RequestPart("client") String client,
            @RequestPart("idConv") String idConv,
            @RequestPart("message") String content) {

        log.info("BINDED {}", content);
        SecuredMessageEntity msg = new SecuredMessageEntity();
        List<SecuredMessageAttachmentEntity> listAttch = new ArrayList<>();
        for(MultipartFile file : files) {
            SecuredMessageAttachmentEntity attch = new SecuredMessageAttachmentEntity();
            attch.setData(file.getBytes());
            attch.setFileName(file.getOriginalFilename());
            attch.setType(file.getContentType());
            attch.setSecuredMessageEntity(msg);
            listAttch.add(attch);
        }

        msg.setMessage(content);
        msg.setClient(client);
        msg.setConvId(idConv);
        msg.setAttachment(listAttch);
        securedMessageRepo.saveAndFlush(msg);
        return new ResponseEntity<String>("SUCCESS",HttpStatus.CREATED);
    }

    @GetMapping(value = "/secured-message/{attchId}")
    public ResponseEntity<byte[]> getAttch(@PathVariable("attchId") String attchId) {

        log.info("GET attachment");
        SecuredMessageAttachmentEntity attch = securedMessageAttachmentRepo.findById(Long.parseLong(attchId)).get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attch.getFileName()+ "\"")
                .body(attch.getData());
    }

    @GetMapping(value = "/message/{messageId}")
    public ResponseEntity<SecuredMessageEntity> getMessage(@PathVariable("messageId") String messageId) {

        SecuredMessageEntity securedMessageEntity = securedMessageRepo.findById(Long.parseLong(messageId)).get();
        securedMessageEntity.getAttachment()
                .forEach(e -> e.setType("http://localhost:8888/"+e.getId()));
        log.info("GET attachment");
        return ResponseEntity.ok()
                .body(securedMessageEntity);
    }

}

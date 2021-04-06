package com.serradj.lamine.securedmessagepoc.core.repo;

import com.serradj.lamine.securedmessagepoc.core.entities.SecuredMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecuredMessageRepo extends JpaRepository<SecuredMessageEntity, Long> {
}

package com.serradj.lamine.securedmessagepoc.bsahtech.repo;

import com.serradj.lamine.securedmessagepoc.bsahtech.entities.OrderRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRequestRepo extends JpaRepository<OrderRequestEntity, Long> {

}

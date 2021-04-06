package com.serradj.lamine.securedmessagepoc.bsahtech.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMAIL",length = 200)
    private String email;

    @Column(name = "PASSWORD")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "FIRSTNAME",length = 50)
    private String firstname;

    @Column(name = "LASTNAME",length = 50)
    private String lastname;

    @Column(name = "CREATION_DATE")
    @Builder.Default
    private LocalDate creationDate = LocalDate.now();


    @OneToMany(
            mappedBy = "createdBy",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<OrderRequestEntity> orderRequestEntities = new ArrayList<>();


    public void addOrderRequest(OrderRequestEntity order) {
        if (order == null) return;
        orderRequestEntities.add(order);
        order.setCreatedBy(this);
    }

    public void removeOrderRequest(OrderRequestEntity order) {
        if (order == null) return;
        orderRequestEntities.remove(order);
        order.setCreatedBy(null);
    }



}

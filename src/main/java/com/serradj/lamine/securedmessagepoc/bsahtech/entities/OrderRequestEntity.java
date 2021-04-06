package com.serradj.lamine.securedmessagepoc.bsahtech.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDER_REQUEST")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRODUCT_DESCRIPTION")
    private String productDescription;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "URL")
    private String url;

    @Column(name = "REWARD")
    private Long reward;

    @Column(name = "REWARD_CURRENCY")
    private String rewardCurrency;

    @Column(name = "TO_COUNTRY")
    private String toCountry;

    @Column(name = "TO_CITY")
    private String toCity;

    @Column(name = "FROM_COUNTRY")
    private String fromCountry;

    @Column(name = "FROM_CITY")
    private String fromCity;

    @Column(name = "EXPECTED_DELIVERY_DATE")
    private LocalDate expectedDeliveryDate;

    @Column(name = "EXPECTED_PRICE")
    private Long expectedPrice;

    @Column(name = "EXPECTED_PRICE_CURRENCY")
    private String expectedPriceCurrency;

    @Column(name = "CREATION_DATE")
    @Builder.Default
    private LocalDate creationDate = LocalDate.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CREATED_BY",nullable = false)
    private UserEntity createdBy;



}

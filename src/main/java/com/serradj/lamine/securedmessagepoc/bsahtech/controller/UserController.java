package com.serradj.lamine.securedmessagepoc.bsahtech.controller;

import com.serradj.lamine.securedmessagepoc.bsahtech.entities.ApiResponse;
import com.serradj.lamine.securedmessagepoc.bsahtech.entities.UserEntity;
import com.serradj.lamine.securedmessagepoc.bsahtech.entities.OrderRequestEntity;
import com.serradj.lamine.securedmessagepoc.bsahtech.repo.UserRepo;
import com.serradj.lamine.securedmessagepoc.bsahtech.repo.OrderRequestRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.vavr.control.Try;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Key;
import java.time.LocalDate;
import java.util.*;

@CrossOrigin(value = "*",origins = "*",exposedHeaders = "authorization")
@RestController
@Slf4j
public class UserController {

    private final OrderRequestRepo orderRequestRepo;
    private final UserRepo userRepo;

    @Autowired
    public UserController(OrderRequestRepo orderRequestRepo, UserRepo userRepo) {
        this.orderRequestRepo = orderRequestRepo;
        this.userRepo = userRepo;
        UserEntity user = UserEntity.builder().lastname("SERRADJ").firstname("Lamine").email("test@test.ts").password("123456").build();
        OrderRequestEntity order1 = OrderRequestEntity.builder()
                .toCity("Paris")
                .toCountry("France")
                .productName("Iphone SE")
                .productDescription("Blanc, 250go")
                .fromCity("Alger")
                .fromCountry("Algérie")
                .expectedPriceCurrency("EUR")
                .expectedPrice(150L)
                .reward(120L)
                .url("https://www.apple.com/fr/shop/buy-iphone/iphone-se/%C3%A9cran-4,7-pouces-64go-blanc")
                .expectedDeliveryDate(LocalDate.parse("2021-10-01"))
                .build();

        OrderRequestEntity order2 = OrderRequestEntity.builder()
                .toCity("Los Angeles")
                .productName("Mac book air")
                .productDescription("Gris sideral, 250Go")
                .toCountry("USA")
                .fromCountry("France")
                .fromCity("Paris")
                .expectedPriceCurrency("EUR")
                .expectedPrice(400L)
                .reward(50L)
                .url("https://www.apple.com/fr/shop/buy-mac/macbook-pro/16-pouces-argent-processeur-8-c%C5%93urs-%C3%A0-2,3-ghz-1to#")
                .expectedDeliveryDate(LocalDate.parse("2022-01-01"))
                .build();
        user.addOrderRequest(order1);
        user.addOrderRequest(order2);
        user.addOrderRequest(order1);
        user.addOrderRequest(order1);
        user.addOrderRequest(order2);
        userRepo.saveAndFlush(user);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ApiResponse<UserEntity>> register(@RequestBody UserEntity userEntity) {

        log.info("Registering user={}", userEntity.toString());
        return Try.of(() -> userRepo.findByEmail(userEntity.getEmail()))
                .map(e -> {
                    if(e.isPresent()) return ApiResponse.badRequest((UserEntity) null,"Error, user already exist");
                    UserEntity userEntity1 = userRepo.saveAndFlush(userEntity);
                    return ApiResponse.ok(userEntity);
                })
                .getOrElseGet(e -> ApiResponse.badRequest(null,"an error happend"));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody UserEntity userEntity) {
        log.info("LOGIN user={}", userEntity.toString());

        return Try.of(() -> userRepo.findByEmail(userEntity.getEmail()))
                .map(userInDb -> {
                    if(userInDb.isPresent() && checkUserPassword(userInDb.get(),userEntity)) {
                        return new ResponseEntity<Object>(processAuth(userInDb.get()),HttpStatus.OK);
                    }
                    return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
                })
                .onFailure(e -> log.error("User {} not found",userEntity.getEmail()))
                .getOrElseGet(e -> new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED));
    }

    private boolean checkUserPassword(UserEntity userInDb, UserEntity userEntity) {
        return userInDb.getPassword().equals(userEntity.getPassword());
    }

    private HttpHeaders processAuth(UserEntity userEntity) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        Map<String,String> claims = new HashMap<>();
        claims.put("firstname",userEntity.getFirstname());
        claims.put("lastname",userEntity.getLastname());
        claims.put("id",userEntity.getId().toString());
        String jws = Jwts.builder()
                .setClaims(claims)
                .signWith(key)
                .compact();
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization",jws);
        return headers;
    }


//    @GetMapping(value = "/message/{messageId}")
//    public ResponseEntity<OrderRequestEntity> getMessage(@PathVariable("messageId") String messageId) {
//
//        OrderRequestEntity orderRequestEntity = orderRequestRepo.findById(Long.parseLong(messageId)).get();
//        orderRequestEntity.getAttachment()
//                .forEach(e -> e.setType("http://localhost:8888/"+e.getId()));
//        log.info("GET attachment");
//        return ResponseEntity.ok()
//                .body(orderRequestEntity);
//    }

}

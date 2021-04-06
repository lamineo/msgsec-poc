package com.serradj.lamine.securedmessagepoc.bsahtech.controller;

import com.serradj.lamine.securedmessagepoc.bsahtech.entities.ApiResponse;
import com.serradj.lamine.securedmessagepoc.bsahtech.entities.OrderRequestEntity;
import com.serradj.lamine.securedmessagepoc.bsahtech.entities.UserEntity;
import com.serradj.lamine.securedmessagepoc.bsahtech.repo.OrderRequestRepo;
import com.serradj.lamine.securedmessagepoc.bsahtech.repo.UserRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@Slf4j
public class OrderRequestController {

    private final OrderRequestRepo orderRequestRepo;
    private final UserRepo userRepo;

    @Autowired
    public OrderRequestController(OrderRequestRepo orderRequestRepo, UserRepo userRepo) {
        this.orderRequestRepo = orderRequestRepo;
        this.userRepo = userRepo;
    }

    @PostMapping(value = "/user/{userId}/order-request")
    @Transactional
    public ResponseEntity<ApiResponse<UserEntity>> createOrderRequest(
                                                                      @RequestBody OrderRequestEntity order, @PathVariable("userId") Long userId) {

        log.info("[ORDER-REQUEST] - user ={}", userId);
        log.info("[ORDER-REQUEST] - ORDER ={}", order.toString());
        return Try.of(() -> userRepo.findById(userId))
                .map(user -> {
                    if (!user.isPresent()) return ApiResponse.badRequest((UserEntity) null, "Error, user not exist");
                    UserEntity userEntity = user.get();
                    userEntity.addOrderRequest(order);
                    userRepo.saveAndFlush(userEntity);
                    return ApiResponse.created(userEntity);
                })
                .getOrElseGet(e -> ApiResponse.badRequest(null, "an error happend"));
    }

    @GetMapping(value = "/user/{userId}/order-request")
    public ResponseEntity<ApiResponse<List<OrderRequestEntity>>> retrieveUserOrders(@PathVariable("userId") Long userId) {

        log.info("[ORDER-REQUEST] - retrieveUserOrders  ={}", userId);

        return Try.of(() -> userRepo.findById(userId))
                .map(user -> {
//                    if (user.isEmpty()) {
//                        return ApiResponse.ok( new ArrayList<OrderRequestEntity>());
//                    }
                    return ApiResponse.ok(user.get().getOrderRequestEntities());
                })
//                .get();
                .getOrElseGet(e -> ApiResponse.nok(new ArrayList<>(), e));
    }

    @GetMapping(value = "/order-request")
    public ResponseEntity<ApiResponse<List<OrderRequestEntity>>> retrieveAll() {

        return Try.of(orderRequestRepo::findAll)
                .map(ApiResponse::ok)
                .onFailure(e -> log.error("Something wrong happend {}", e.getMessage()))
                .getOrElseGet(e -> ApiResponse.nok(null, e));
    }


}

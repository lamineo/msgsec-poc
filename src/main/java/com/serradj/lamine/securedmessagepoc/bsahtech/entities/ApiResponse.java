package com.serradj.lamine.securedmessagepoc.bsahtech.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApiResponse<T>{
    T data;
    ApiError error;

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        ApiResponse<T> api = new ApiResponse<T>();
        api.setData(data);
        return new ResponseEntity<>(api, HttpStatus.OK);
    }
    public static <T> ResponseEntity<ApiResponse<T>> nok(T data, Throwable throwable) {
        ApiResponse<T> api = new ApiResponse<T>();
        api.setData(data);
        api.setError(ApiError.builder().message("INTERNAL SERVER ERROR - UNEXPECTED ERROR").errorPayload(throwable.getMessage()).build());
        return new ResponseEntity<>(api, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        ApiResponse<T> api = new ApiResponse<T>();
        api.setData(data);
        return new ResponseEntity<>(api, HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<ApiResponse<T>> badRequest(T data, String message) {
        ApiResponse<T> api = new ApiResponse<T>();
        api.setData(data);
        api.setError(ApiError.builder().message(message).build());
        return new ResponseEntity<>(api, HttpStatus.BAD_REQUEST);
    }



}

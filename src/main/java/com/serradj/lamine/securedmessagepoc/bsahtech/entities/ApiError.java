package com.serradj.lamine.securedmessagepoc.bsahtech.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ApiError {
    private String message;
    private Object errorPayload;
}

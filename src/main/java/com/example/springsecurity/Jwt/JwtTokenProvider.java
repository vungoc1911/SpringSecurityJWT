package com.example.springsecurity.Jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenProvider {

    // chuỗi key
    private String JWT_SECRET;

    // thời gian hết han
    private int JWT_EXPIRSTION;
}

package com.example.springsecurity.Payload.Request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String userName;
    private String passWord;
}

package com.example.springsecurity.Controller;

import com.example.springsecurity.Jwt.JwtTokenProvider;
import com.example.springsecurity.Payload.Request.SignUpRequest;
import com.example.springsecurity.Payload.Response.MessageResponse;
import com.example.springsecurity.Service.RoleService;
import com.example.springsecurity.Service.UserService;
import org.apache.logging.log4j.message.MapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest request) {
        if (userService.existsByUserName(request.getUserName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("user đã tồn tại"));
        }
        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("email đã tồn tại"));
        }
    }
}

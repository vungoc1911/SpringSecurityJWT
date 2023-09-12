package com.example.springsecurity.Controller;

import com.example.springsecurity.Entity.Role;
import com.example.springsecurity.Entity.Roles;
import com.example.springsecurity.Entity.Users;
import com.example.springsecurity.Jwt.JwtTokenProvider;
import com.example.springsecurity.Payload.Request.LoginRequest;
import com.example.springsecurity.Payload.Request.SignUpRequest;
import com.example.springsecurity.Payload.Response.JwtResponse;
import com.example.springsecurity.Payload.Response.MessageResponse;
import com.example.springsecurity.Security.CustomUserDetail;
import com.example.springsecurity.Service.RoleService;
import com.example.springsecurity.Service.UserService;
import org.apache.logging.log4j.message.MapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        Users users = new Users();
        users.setUserName(request.getUserName());
        users.setEmail(request.getEmail());
        users.setPassWord(passwordEncoder.encode(request.getPassword()));
        users.setPhone(request.getPhone());
        users.setStatus(true);
        users.setCreateDate(LocalDate.now());
        Set<String> strRoles = request.getListRoles();
        Set<Roles> listRoles = new HashSet<>();
        if (strRoles == null) {
            // User quyen mac dinh
            Roles roles = roleService.findByRoleName(Role.ROLE_USER).orElseThrow(() -> new RuntimeException("Role not found"));
            listRoles.add(roles);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = roleService.findByRoleName(Role.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Role not found"));
                        listRoles.add(adminRole);
                    case "moderator":
                        Roles moderator = roleService.findByRoleName(Role.ROLE_MODERATOR).orElseThrow(() -> new RuntimeException("Role not found"));
                        listRoles.add(moderator);
                    case "user":
                        Roles userRole = roleService.findByRoleName(Role.ROLE_USER).orElseThrow(() -> new RuntimeException("Role not found"));
                        listRoles.add(userRole);
                }
            });
        }
        users.setRoles(listRoles);
        userService.saveOrUpdate(users);
        return ResponseEntity.ok(new MessageResponse("success"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassWord())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        // sinh jwt trả về
        String jwt = jwtTokenProvider.generateToken(customUserDetail);
        // lấy danh sách các quyền
        List<String> listRoles = customUserDetail.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, customUserDetail.getUsername(), customUserDetail.getEmail(), customUserDetail.getPhone(), listRoles));
    }
}

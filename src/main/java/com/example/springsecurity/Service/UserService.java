package com.example.springsecurity.Service;

import com.example.springsecurity.Entity.Users;

public interface UserService {
    Users findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    Users saveOrUpdate(Users users);
}

package com.example.springsecurity.Service.UserServiceImpl;

import com.example.springsecurity.Entity.Role;
import com.example.springsecurity.Entity.Roles;
import com.example.springsecurity.Repository.RoleRepository;
import com.example.springsecurity.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Override
    public Optional<Roles> findByRoleName(Role roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}

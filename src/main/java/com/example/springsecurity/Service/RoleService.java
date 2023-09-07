package com.example.springsecurity.Service;

import com.example.springsecurity.Entity.Role;
import com.example.springsecurity.Entity.Roles;

import java.util.Optional;

public interface RoleService {
    Optional<Roles> findByRoleName(Role roleName);
}

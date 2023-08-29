package com.example.springsecurity.Security;

import com.example.springsecurity.Entity.Users;
import com.example.springsecurity.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;

public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUserName(username);
        if (ObjectUtils.isEmpty(users)) {
            throw  new UsernameNotFoundException("user not found");
        }
        return CustomUserDetail.mapUserToUserDetail(users);
    }
}

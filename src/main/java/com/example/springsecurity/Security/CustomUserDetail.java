package com.example.springsecurity.Security;

import com.example.springsecurity.Entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CustomUserDetail implements UserDetails {
    private int userId;
    private String userName;
    @JsonIgnore
    private String passWord;
    private boolean status;
    private String phone;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    // lấy ra các quyền của user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    // map thông tin User sang UserDetail
    public static CustomUserDetail mapUserToUserDetail(Users users) {
        // lấy các quyền từ các đô tượng
        List<GrantedAuthority> listAuthority = users.getRoles().stream()
                .map(roles -> new SimpleGrantedAuthority(roles.getRoleName().name()))
                .collect(Collectors.toList());

        // Trả về userDetail
        return new CustomUserDetail(
                users.getUserId(),
                users.getUserName(),
                users.getPassWord(),
                users.isStatus(),
                users.getPhone(),
                users.getEmail(),
                listAuthority
        );
    }
    @Override
    public String getPassword() {
        return this.passWord;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

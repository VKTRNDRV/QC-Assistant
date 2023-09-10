package com.example.qcassistant.service.auth;

import com.example.qcassistant.domain.UserDetailsImpl;
import com.example.qcassistant.domain.entity.auth.RoleEntity;
import com.example.qcassistant.domain.entity.auth.UserEntity;
import com.example.qcassistant.repository.auth.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;


    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findUserEntityByUsername(username)
                .map(this::mapUser)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User with name " + username + " not found!"));
    }

    private UserDetailsImpl mapUser(UserEntity userEntity){
        return new UserDetailsImpl(
                userEntity.getUsername(),
                userEntity.getPassword(),
                extractAuthorities(userEntity));
    }

    private List<GrantedAuthority> extractAuthorities(UserEntity userEntity) {
        return userEntity.
                getRoles().
                stream().
                map(this::mapRole).
                toList();
    }

    private GrantedAuthority mapRole(RoleEntity role) {
        return new SimpleGrantedAuthority("ROLE_" + role.getRole().name());
    }
}

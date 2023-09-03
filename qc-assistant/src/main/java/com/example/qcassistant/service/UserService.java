package com.example.qcassistant.service;

import com.example.qcassistant.domain.dto.UserDisplayDto;
import com.example.qcassistant.domain.dto.UserRegistrationDto;
import com.example.qcassistant.domain.entity.RoleEntity;
import com.example.qcassistant.domain.entity.UserEntity;
import com.example.qcassistant.domain.enums.RoleEnum;
import com.example.qcassistant.exception.ExistingUsernameException;
import com.example.qcassistant.exception.UnconfirmedPasswordException;
import com.example.qcassistant.repository.RoleRepository;
import com.example.qcassistant.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;
    private ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.modelMapper = modelMapper;
    }

    public List<UserDisplayDto> getAllUsers(){
        return this.userRepository.findAll().stream()
                .map(u -> this.modelMapper.map(u, UserDisplayDto.class))
                .collect(Collectors.toList());
    }


    public void registerUser(UserRegistrationDto registrationDTO,
                             Consumer<Authentication> successfulLoginProcessor){

        validateUserRegistration(registrationDTO);

        UserEntity userEntity = new UserEntity()
                .setUsername(registrationDTO.getUsername())
                .setPassword(this.passwordEncoder
                        .encode(registrationDTO.getPassword()));
        List<RoleEntity> roles = List.of(this.roleRepository
                .findRoleEntityByRole(RoleEnum.USER).get());
        userEntity.setRoles(roles);

        userRepository.save(userEntity);

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(registrationDTO.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        successfulLoginProcessor.accept(authentication);
    }

    private void validateUserRegistration(UserRegistrationDto registrationDTO){
        if(!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())){
            throw new UnconfirmedPasswordException();
        }
        if(this.userRepository.findUserEntityByUsername(registrationDTO.getUsername())
                .isPresent()){
            throw new ExistingUsernameException();
        }
    }

}

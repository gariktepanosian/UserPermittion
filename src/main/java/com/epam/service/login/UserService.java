package com.epam.service.login;

import com.epam.dto.AuthenticationRequest;
import com.epam.dto.AuthenticationResponse;
import com.epam.dto.UserDto;
import com.epam.security.JwtTokenUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService {

    UserDto getById(Long id);

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto);

    UserDto updateField(UserDto userDto);

    UserDto findUserByUsername(String username);

    Boolean existsByUsername(String username);

    AuthenticationResponse login(AuthenticationRequest authenticationRequest, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil);

}

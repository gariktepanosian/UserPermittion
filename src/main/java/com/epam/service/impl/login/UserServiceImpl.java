package com.epam.service.impl.login;

import com.epam.dto.AuthenticationRequest;
import com.epam.dto.AuthenticationResponse;
import com.epam.dto.UserDto;
import com.epam.exceptions.ResourceNotFoundException;
import com.epam.mapper.UserMapper;
import com.epam.model.Role;
import com.epam.model.User;
import com.epam.repository.UserRepository;
import com.epam.security.JwtTokenUtil;
import com.epam.service.login.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final String USER = "ROLE_" + Role.USER.name();

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        UserDto userDto = UserMapper.mapUser(user);
        return userDto;
    }

    @Override
    @Transactional
    public UserDto findUserByUsername(String username) {
        User userByUsername = userRepository.findUserByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Username : " + username + "not found"));
        UserDto userDto = UserMapper.mapUser(userByUsername);
        return userDto;
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        User userByUsername = userRepository.findUserByUsername(authenticationRequest.getUsername()).orElseThrow(ResourceNotFoundException::new);
        if (userByUsername != null && passwordEncoder.matches(authenticationRequest.getPassword(), userByUsername.getPassword())) {
            UserDto userDto = UserMapper.mapUser(userByUsername);
            String token = jwtTokenUtil.generateToken(userByUsername.getUsername());
            return new AuthenticationResponse(token, userDto);
        }
        return null;
    }

    //REGISTER ONLY USER
    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        User user = UserMapper.map(userDto);
        user.setRole(USER);
        User save = userRepository.save(user);
        return UserMapper.mapUser(save);
    }

    //UPDATE ONLY USER
    @Override
    @Transactional
    public UserDto update(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(ResourceNotFoundException::new);
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setRole(USER);
        return UserMapper.mapUser(user);
    }

    //UPDATE FIELDS ONLY USER
    @Override
    public UserDto updateField(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(ResourceNotFoundException::new);
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }  if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }  if (userDto.getUsername() != null) {
            user.setUsername(userDto.getUsername());
        }  if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }
        user.setRole(USER);

        return UserMapper.mapUser(user);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

}

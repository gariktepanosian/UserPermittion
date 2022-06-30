package com.epam.controller.login;

import com.epam.dto.AuthenticationRequest;
import com.epam.dto.AuthenticationResponse;
import com.epam.dto.UserDto;
import com.epam.security.JwtTokenUtil;
import com.epam.service.login.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class UserAuthorizationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    private final String LOGIN = "/login";
    private final String REGISTER = "/register";
    private final String SIGN_IN = "/sign_in";
    private final String UNAUTHORIZED = "Unauthorized";

    @Autowired
    public UserAuthorizationController(UserService userService, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;

    }

    @PostMapping(SIGN_IN)
    public ResponseEntity<AuthenticationResponse> userLogin(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse login = userService.login(authenticationRequest,passwordEncoder,jwtTokenUtil);
        return ResponseEntity.ok(login);
    }

    @PostMapping(value = LOGIN)
    public ResponseEntity login(@RequestBody AuthenticationRequest authenticationRequest) {
        UserDto findUserByUsername = userService.findUserByUsername(authenticationRequest.getUsername());
        if (findUserByUsername != null && passwordEncoder.matches(authenticationRequest.getPassword(), findUserByUsername.getPassword())) {
            String generatedToken = jwtTokenUtil.generateToken(findUserByUsername.getUsername());
            return ResponseEntity.ok(AuthenticationResponse
                    .builder()
                    .token(generatedToken)
                    .userDto(findUserByUsername)
                    .build());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(UNAUTHORIZED);
    }

    @PostMapping(value = REGISTER, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserDto createUser = userService.create(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
    }

}

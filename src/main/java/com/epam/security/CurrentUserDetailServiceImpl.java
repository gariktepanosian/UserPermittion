package com.epam.security;

import com.epam.dto.UserDto;
import com.epam.mapper.UserMapper;
import com.epam.model.User;
import com.epam.service.login.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Priority;

@Service
@Priority(1)
public class CurrentUserDetailServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public CurrentUserDetailServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Qualifier
    public UserDetails loadUserByUsername(String username) {
        UserDto userByUsername = userService.findUserByUsername(username);
        User map = UserMapper.map(userByUsername);
        return new CurrentUser(map);
    }
}

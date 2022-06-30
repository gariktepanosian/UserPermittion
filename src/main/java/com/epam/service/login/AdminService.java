package com.epam.service.login;

import com.epam.dto.UserDto;

import java.util.List;

public interface AdminService {

    UserDto create(UserDto userDto);

    String delete(Long id,UserDto userDto);

     UserDto updateField(UserDto userDto);

    UserDto update(UserDto userDto);

    List<UserDto> getAll();

    List<UserDto> getAllByRole(String role);

    UserDto findUserByUsername(String username);

    UserDto getById(Long id);



}

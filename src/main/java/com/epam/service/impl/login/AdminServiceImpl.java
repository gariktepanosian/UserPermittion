package com.epam.service.impl.login;

import com.epam.dto.UserDto;
import com.epam.exceptions.ResourceNotFoundException;
import com.epam.mapper.UserMapper;
import com.epam.model.Role;
import com.epam.model.User;
import com.epam.repository.UserRepository;
import com.epam.service.login.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final String FORBIDDEN = "Forbidden";
    private  String infoAboutUser;
    private final String ADMIN = "ROLE_" + Role.ADMIN.name();

    @Autowired
    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUserById(Long id){
     return userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    //GET ALL
    @Override
    @Transactional
    public List<UserDto> getAll() {
        List<User> all = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : all) {
            userDtoList.add(UserMapper.mapUser(user));
        }
        return userDtoList;
    }

    //GET BY ID
    @Override
    public UserDto getById(Long id) {
        User user = findByUserById(id);
        return  UserMapper.mapUser(user);
    }

    //GET BY USERNAME
    @Override
    @Transactional()
    public UserDto findUserByUsername(String username) {
        User userByUsername = userRepository.findUserByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Username : " + username + "not found"));
        return UserMapper.mapUser(userByUsername);
    }

    //GET BY ROLE ID
    @Override
    @Transactional()
    public List<UserDto> getAllByRole(String role) {
        List<User> allByRole = userRepository.findAllByRole(role);
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : allByRole) {
            userDtoList.add(UserMapper.mapUser(user));
        }
        return userDtoList;
    }

    //REGISTER
    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        User user = UserMapper.map(userDto);
        user.setRole(userDto.getRole());
        return UserMapper.mapUser(userRepository.save(user));
    }

    //DELETE
    @Override
    @Transactional
    public String delete(UserDto userDto) {
        if (userDto.getRole() == ADMIN) {
            userRepository.deleteById(userDto.getId());
            infoAboutUser = "The User with the " + userDto.getId() + "the ID was Deleted " + Boolean.TRUE;
            return infoAboutUser;
        } else return FORBIDDEN;
    }

    //UPDATE
    @Override
    @Transactional
    public UserDto update(UserDto userDto) {
        User user = findByUserById(userDto.getId());
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        return UserMapper.mapUser(userRepository.save(user));
    }

    //UPDATE Fields
    @Override
    @Transactional
    public UserDto updateField(UserDto userDto) {
        User user = findByUserById(userDto.getId());
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }  if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }  if (userDto.getUsername() != null) {
            user.setUsername(userDto.getUsername());
        }  if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }  if (userDto.getRole() != null) {
            user.setRole((userDto.getRole()));
        }
        return UserMapper.mapUser(userRepository.save(user));
    }
}

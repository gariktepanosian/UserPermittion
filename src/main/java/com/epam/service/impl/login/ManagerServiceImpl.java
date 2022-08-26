package com.epam.service.impl.login;

import com.epam.dto.UserDto;
import com.epam.exceptions.ResourceNotFoundException;
import com.epam.mapper.UserMapper;
import com.epam.model.Role;
import com.epam.model.User;
import com.epam.repository.UserRepository;
import com.epam.service.login.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManagerServiceImpl implements ManagerService {

    private final UserRepository userRepository;
    private final String ADMIN = "ROLE_" + Role.ADMIN.name();
    @Autowired
    public ManagerServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDto updateField(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(ResourceNotFoundException::new);
        if (userDto.getName()!=null) {
            user.setName(userDto.getName());
        }
         if (userDto.getLastName()!=null){
            user.setLastName(userDto.getLastName());
        }
         if (userDto.getUsername()!=null){
            user.setUsername(userDto.getUsername());
        } if (userDto.getPassword()!=null){
            user.setPassword(userDto.getPassword());
        } if (userDto.getRole()!=null && !userDto.getRole().equals(ADMIN)) {
            user.setRole((userDto.getRole()));
        }
        return UserMapper.mapUser(userRepository.save(user));
    }
}

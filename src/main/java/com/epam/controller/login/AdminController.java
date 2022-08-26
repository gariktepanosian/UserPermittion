package com.epam.controller.login;

import com.epam.dto.UserDto;
import com.epam.model.Role;
import com.epam.service.login.AdminService;
import com.epam.service.login.ManagerService;
import com.epam.service.login.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/admin")
public class AdminController {

    private final UserService userService;
    private final ManagerService managerService;
    private final AdminService adminService;
    private final String ALL = "/all";
    private final String USERS = "/users";
    private final String MANAGERS = "/managers";
    private final String ADMINS = "/admins";


    private final String ROLE = "ROLE_";
    private final String ADMIN = ROLE + Role.ADMIN.name();
    private final String MANAGER = ROLE + Role.MANAGER.name();
    private final String USER = ROLE + Role.USER.name();

    @Autowired
    public AdminController(UserService userService, ManagerService managerService, AdminService adminService) {
        this.userService = userService;
        this.managerService = managerService;
        this.adminService = adminService;
    }

    @GetMapping(value = ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAll());
    }

    @GetMapping(value = USERS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getOnlyUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllByRole(USER));
    }

    @GetMapping(value = MANAGERS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getOnlyManagers() {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllByRole(MANAGER));
    }

    @GetMapping(value = ADMINS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getOnlyAdmins() {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllByRole(ADMIN));
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto getUserById = adminService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(getUserById);
    }

    @GetMapping(value = "/{name}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getUserByRole(@PathVariable String name) {
        List<UserDto> getAllUsersByRoleName = adminService.getAllByRole(name);
        return ResponseEntity.status(HttpStatus.OK).body(getAllUsersByRoleName);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createUser = adminService.create(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        userDto.setId(id);
        UserDto updateUser = userService.update(userDto);
        return ResponseEntity.status(HttpStatus.OK).body(updateUser);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateFields(@PathVariable Long id, @RequestBody UserDto userDto) {
        userDto.setId(id);
        UserDto updateUserAnyDate = userService.updateField(userDto);
        return ResponseEntity.status(HttpStatus.OK).body(updateUserAnyDate);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        UserDto findUserById = userService.getById(id);
        String deleteChecker = adminService.delete(findUserById);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deleteChecker);
    }
}

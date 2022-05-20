package com.berkaybaltaci.myrestapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("principal == 'ADMIN'")
    public List<UserDto> getUsers() {
        List<User> userList = userService.getUsers();
        return userList.stream().map(userService::entityToDto).toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("principal == 'ADMIN'")
    public UserDto getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with the given id is not found.");
        }
        return userService.entityToDto(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("principal == 'ADMIN' || principal == @userServiceImpl.getUser(#id).name")
    public UserDto updateUser(@PathVariable Long id, @RequestBody @Valid UserDto userDto) {
        User userInDb = userService.getUser(id);
        if (userInDb == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with the given id is not found.");
        }

        userInDb.setName(userDto.getName());
        userService.updateUser(userInDb);

        userDto.setId(id);
        return userDto;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("principal == 'ADMIN' || principal == @userServiceImpl.getUser(#id).name")
    public void deleteUser(@PathVariable Long id) {
        if (userService.getUser(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with the given id is not found.");
        }
        userService.deleteUser(id);
    }
}

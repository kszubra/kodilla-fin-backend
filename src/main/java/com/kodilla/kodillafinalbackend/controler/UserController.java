package com.kodilla.kodillafinalbackend.controler;

import com.kodilla.kodillafinalbackend.domain.User;
import com.kodilla.kodillafinalbackend.domain.UserDto;
import com.kodilla.kodillafinalbackend.domain.UserRegistrationDto;
import com.kodilla.kodillafinalbackend.mapper.UserMapper;
import com.kodilla.kodillafinalbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("users")
    public void registerUser(@RequestBody UserRegistrationDto dto) {
        userService.addUser( userMapper.mapRegistrationDtoToUser(dto) );
    }

    @GetMapping("users/{id}")
    public UserDto getUser(@PathVariable("id") Long id) {
        return userMapper.mapToDto( userService.getUserById(id) );
    }

    @GetMapping("users")
    public List<UserDto> getUsers() {
        return userMapper.mapToUserDtoList( userService.getAllUsers() );
    }

    @PutMapping("users")
    @Transactional
    public UserDto updateUser(@RequestBody UserDto updatingDto) {
        User user = userService.getUserById( updatingDto.getId() );

        if(! user.getName().equals( updatingDto.getName() )) { user.setName( updatingDto.getName() ); }
        if(! user.getSurname().equals( updatingDto.getSurname() )) { user.setSurname( updatingDto.getSurname() ); }
        if(! user.getEmail().equals( updatingDto.getEmail() )) { user.setEmail( updatingDto.getEmail() ); }
        if(! user.getSecurePassword().equals( updatingDto.getSecurePassword() )) { user.setSecurePassword( updatingDto.getSecurePassword() ); }
        if(! user.getRegistered().equals( updatingDto.getRegistered() )) { user.setRegistered( updatingDto.getRegistered() ); }

        return userMapper.mapToDto(user);
    }


}

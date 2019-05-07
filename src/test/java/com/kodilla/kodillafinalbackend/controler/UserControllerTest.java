package com.kodilla.kodillafinalbackend.controler;

import com.google.gson.Gson;
import com.kodilla.kodillafinalbackend.domain.User;
import com.kodilla.kodillafinalbackend.domain.UserDto;
import com.kodilla.kodillafinalbackend.mapper.UserMapper;
import com.kodilla.kodillafinalbackend.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;

    private Gson gson = new Gson();

    @Test
    public void registerUser() {
    }

    @Test
    public void getUser() {
    }

    @Test
    public void testGetUsers() throws Exception {
        //Given
        User testUser = User.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo.com")
                .registered(LocalDate.now())
                .securePassword("123456789")
                .notificationPreferences(new HashSet<>())
                .build();
        User testUserTwo = User.builder()
                .name("John")
                .surname("Rambo 2")
                .email("rambo@rambo32.com")
                .registered(LocalDate.now())
                .securePassword("123456789")
                .notificationPreferences(new HashSet<>())
                .build();
        List<User> users = Arrays.asList(testUser, testUserTwo);

        UserDto testDto = UserDto.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo.com")
                .registered(LocalDate.now())
                .securePassword("123456789")
                .notificationIds(new HashSet<>())
                .build();
        UserDto testDtoTwo = UserDto.builder()
                .name("John")
                .surname("Rambo 2")
                .email("rambo@rambo32.com")
                .registered(LocalDate.now())
                .securePassword("123456789")
                .notificationIds(new HashSet<>())
                .build();
        List<UserDto> userDtos = Arrays.asList(testDto, testDtoTwo);

        when(userService.getAllUsers()).thenReturn(users);
        when(userMapper.mapToUserDtoList(users)).thenReturn(userDtos);

        // When & Then
        mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].surname", is("Rambo")))
                .andExpect(jsonPath("$[0].email", is("rambo@rambo.com")))
                .andExpect(jsonPath("$[1].surname", is("Rambo 2")))
                .andExpect(jsonPath("$[1].email", is("rambo@rambo32.com")));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void deleteUser() {
    }
}
package com.kodilla.kodillafinalbackend.service;

import com.kodilla.kodillafinalbackend.domain.StartupArgs;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StartupArgsServiceTest {
    @Autowired
    private StartupArgsService service;

    @Before
    public void clean() {
        service.deleteAll();
    }

    @Test
    public void testAddStartupArgs() {
        //Given
        String[] args = {"Something", "Blep", "Mlem"};
        StartupArgs testArgs = new StartupArgs(LocalDateTime.now(), args);

        //when
        service.addStartupArgs(testArgs);
        StartupArgs result = service.getAll().get(0);

        //Then
        assertEquals(testArgs, result);
    }

    @Test
    public void testGetAll() {
        //Given
        String[] args = {"Something", "Blep", "Mlem"};
        StartupArgs testArgsOne = new StartupArgs(LocalDateTime.now(), args);
        StartupArgs testArgsTwo = new StartupArgs(LocalDateTime.now().plusDays(2), args);

        List<StartupArgs> argList = Arrays.asList(testArgsOne, testArgsTwo);

        //when
        service.addStartupArgs(testArgsOne);
        service.addStartupArgs(testArgsTwo);
        List<StartupArgs> result = service.getAll();

        //Then
        assertEquals(argList, result);
    }

    @Test
    public void testDeleteAll() {
        //Given
        String[] args = {"Something", "Blep", "Mlem"};
        StartupArgs testArgsOne = new StartupArgs(LocalDateTime.now(), args);
        StartupArgs testArgsTwo = new StartupArgs(LocalDateTime.now().plusDays(2), args);
        service.addStartupArgs(testArgsOne);
        service.addStartupArgs(testArgsTwo);

        //when
        service.deleteAll();
        List<StartupArgs> result = service.getAll();

        //Then
        assertEquals(0, result.size());
    }
}
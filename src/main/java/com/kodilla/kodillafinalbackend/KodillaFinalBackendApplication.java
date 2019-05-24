package com.kodilla.kodillafinalbackend;

import com.kodilla.kodillafinalbackend.domain.StartupArgs;
import com.kodilla.kodillafinalbackend.repository.StartupArgsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@Slf4j
@SpringBootApplication
public class KodillaFinalBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(KodillaFinalBackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner startupData(StartupArgsRepository repository) {
        return args -> {
            StartupArgs startupArgs = new StartupArgs(LocalDateTime.now(), args);
            repository.save(startupArgs);
            log.info("----- SAVED STARTUP ARGS: " + startupArgs.toString());
        };
    }

}

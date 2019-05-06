package com.kodilla.kodillafinalbackend.scheduler;

import com.kodilla.kodillafinalbackend.config.AdminConfig;
import com.kodilla.kodillafinalbackend.domain.Mail;
import com.kodilla.kodillafinalbackend.service.EmailCreatorService;
import com.kodilla.kodillafinalbackend.service.SimpleEmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class NotificationScheduler {
    private final SimpleEmailService simpleEmailService;
    private final AdminConfig adminConfig;

    private static final String SUBJECT = "New flights matching your preferences";

    /**
     * Every 24 hours updates country database
     */
    //@Scheduled(fixedDelay = 86400000)
    public void notifyAboutOffers(){
        //TODO: preparing data

        simpleEmailService.send(EmailCreatorService.MAIL_TYPE_USER_CUSTOM_NOTIFICATIONS, Mail.builder()
                .mailTo(adminConfig.getApplicationEmail())
                .toCc(adminConfig.getApplicationEmail())
                .subject(SUBJECT)
                .message("TEST MESSAGE FOR TEST")
                .build()
        );

    }
}

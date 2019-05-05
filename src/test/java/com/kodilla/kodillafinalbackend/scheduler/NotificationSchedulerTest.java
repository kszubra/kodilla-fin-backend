package com.kodilla.kodillafinalbackend.scheduler;

import com.kodilla.kodillafinalbackend.config.AdminConfig;
import com.kodilla.kodillafinalbackend.domain.Mail;
import com.kodilla.kodillafinalbackend.service.SimpleEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class NotificationSchedulerTest {
    @InjectMocks
    private NotificationScheduler notificationScheduler;
    @Mock
    private AdminConfig adminConfig;
    @Mock
    private SimpleEmailService simpleEmailService;

    @Test
    public void testSendNotificationEmail() {
        //Given
        when(adminConfig.getApplicationEmail()).thenReturn("test@test.com");
        //When
        notificationScheduler.notifyAboutOffers();
        //Then
        verify(simpleEmailService, times(1)).send(anyString(), any(Mail.class));
    }
}

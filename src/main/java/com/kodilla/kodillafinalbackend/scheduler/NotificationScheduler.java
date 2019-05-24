package com.kodilla.kodillafinalbackend.scheduler;

import com.kodilla.kodillafinalbackend.config.AdminConfig;
import com.kodilla.kodillafinalbackend.domain.*;
import com.kodilla.kodillafinalbackend.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class NotificationScheduler {
    private final SimpleEmailService simpleEmailService;
    private final AdminConfig adminConfig;
    private final WeekendFlightOffersCreator offersCreator;
    private final MailSentRecordService mailRecordService;

    private static final String SUBJECT = "New flights matching your preferences";

    private  Map<String, List<String>> groupEmailAndOffers( Map<NotificationPreference, TripOffer> preferencesAndOffers ) {
        Map<String, List<String>> result = new HashMap<>();

        for( Map.Entry<NotificationPreference, TripOffer> entry : preferencesAndOffers.entrySet() ) {
            String email = entry.getKey().getUser().getEmail();
            String offer = entry.getValue().toString();

            if( result.containsKey(email) ) {
                result.get(email).add(offer);
            } else {
                result.put(email, Arrays.asList(offer));
            }
        }

        return result;
    }

    private Map<String, String> getEmailAndOfferMessage( Map<NotificationPreference, TripOffer> preferencesAndOffers ) {
        Map<String, List<String>> emailAndOffers = groupEmailAndOffers(preferencesAndOffers);
        Map<String, String> result = new HashMap<>();

        for( Map.Entry<String, List<String>> entry : emailAndOffers.entrySet() ) {
            StringBuilder builder = new StringBuilder();
            builder.append("Here are offers that match your requested trip preferences: ");
            entry.getValue().forEach( e -> builder.append(e + "\r\n") );

            result.put(entry.getKey(), builder.toString() );
        }

        return result;
    }

    /**
     * Every 24 hours sends emails
     */
    //@Scheduled(fixedDelay = 86400000)
    public void notifyAboutOffers(){
         Map<String, String> emailAndOfferMessage = getEmailAndOfferMessage( offersCreator.getPreferencesAndOffers() );

        for( Map.Entry<String, String> entry : emailAndOfferMessage.entrySet()) {
            simpleEmailService.send(EmailCreatorService.MAIL_TYPE_USER_CUSTOM_NOTIFICATIONS, Mail.builder()
                    .mailTo(entry.getKey())
                    .toCc(adminConfig.getApplicationEmail())
                    .subject(SUBJECT)
                    .message(entry.getValue())
                    .build()
            );

            MailSentRecord record = new MailSentRecord(LocalDateTime.now(), entry.getKey());
            mailRecordService.addRecord(record);
        }
    }
}

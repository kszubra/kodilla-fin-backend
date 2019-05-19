package com.kodilla.kodillafinalbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalTime;

@Service
public class EmailCreatorService {
    public static final String MAIL_TYPE_USER_CUSTOM_NOTIFICATIONS = "CUSTOM_NOTIFICATIONS";

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    private boolean isAfterNoon() {
        return LocalTime.now().isAfter(LocalTime.NOON);
    }

    private String buildUserCustomNotificationEmail(String message) {

        Context context = new Context(); // wewnątrz niej deklarujemy zmienne do widoku, który później przeprocesujemy za pomocą silnika szablonów wywołując templateEngine.process(). Nie stosujemy w tym wypadku modelu w którym rozszerzaliśmy mapę, ponieważ w tym wypadku Thymeleaf potrzebuje kontekstu dla swojego silnika widoku szablonu. Przyczyną tego rozwiązania jest obowiązek utworzenia w jednej metodzie widoku już ze wstawionymi zmiennymi do szablonu, przed wysłaniem maila.
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/tasks");
        context.setVariable("button", "Visit website");
        context.setVariable("goodbye_message", "Have a nice day");
        context.setVariable("show_button", true);
        context.setVariable("is_after_12", isAfterNoon());
        context.setVariable("company_details", "Company: weekend flights");
        context.setVariable("customer_name", "TEST NAME HERE");

        return templateEngine.process("mail/custom-user-notification-mail", context); //Metoda process() przyjmuje dwa argumenty, jednym z nich jest ścieżka szablonu mail. Metoda wie już, że ma szukać szablonów w katalogu resources/templates, jeśli jednak umiejscowiliśmy nasz szablon "głębiej" w strukturze folderów, należy dodać taką informację w argumencie. Drugim argumentem jest Context, czyli obiekt przechowujący zmienne do widoku.
    }


    public String createMail(final String mailType, final String message) {
        switch (mailType) {
            case MAIL_TYPE_USER_CUSTOM_NOTIFICATIONS:
                return buildUserCustomNotificationEmail(message);
            default:
                return null;
        }
    }
}

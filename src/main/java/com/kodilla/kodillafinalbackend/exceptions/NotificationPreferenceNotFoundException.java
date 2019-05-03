package com.kodilla.kodillafinalbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason="Requested notification preference was not found in database")
public class NotificationPreferenceNotFoundException extends RuntimeException {
}

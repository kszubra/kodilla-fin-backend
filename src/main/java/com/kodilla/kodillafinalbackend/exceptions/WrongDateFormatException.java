package com.kodilla.kodillafinalbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason="Provided date has to match the pattern of YYYY-MM-DD. Single digit values require 0 ahead")
public class WrongDateFormatException extends RuntimeException {
}

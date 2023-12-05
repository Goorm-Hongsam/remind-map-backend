package com.backend.remindmap.marker.application;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeFormatValidator implements ConstraintValidator<LocalDateTimeFormat, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            String formattedDate = value.format(formatter);
            LocalDateTime.parse(formattedDate, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
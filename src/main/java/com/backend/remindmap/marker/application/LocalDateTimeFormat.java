package com.backend.remindmap.marker.application;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LocalDateTimeFormatValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalDateTimeFormat {
    String message() default "YYYY-MM-DD 형식을 지켜주세요.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

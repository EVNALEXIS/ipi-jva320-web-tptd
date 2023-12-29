package com.ipi.jva320.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Aucun salarié trouvé")

public class SalarieException extends Exception {

    public SalarieException(String s) {
        super(s);
    }


}

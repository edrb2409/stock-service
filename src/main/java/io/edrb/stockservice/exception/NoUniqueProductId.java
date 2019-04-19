package io.edrb.stockservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "ProductId is not unique")
public class NoUniqueProductId extends RuntimeException {
}

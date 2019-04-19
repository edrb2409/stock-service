package io.edrb.stockservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Outdated stock", code = HttpStatus.NO_CONTENT)
public class OutdatedStockException extends RuntimeException{
}

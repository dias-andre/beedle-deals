package promo.bot.beedle_deals.adapters.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import promo.bot.beedle_deals.adapters.in.web.dtos.ErrorDTO;
import promo.bot.beedle_deals.core.exceptions.AlreadyExistsException;
import promo.bot.beedle_deals.core.exceptions.NotificationDeliveryException;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class WebExceptionHandler {
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> alreadyExistsException(AlreadyExistsException e) {
        return ResponseEntity.status(400).body(new ErrorDTO("ALREADY_EXISTS", e.getMessage(), OffsetDateTime.now()));
    }

    @ExceptionHandler(NotificationDeliveryException.class)
    public ResponseEntity<ErrorDTO> notificationDeliveryException(NotificationDeliveryException e) {
        return ResponseEntity.status(400).body( new ErrorDTO("ERROR", e.getMessage(), OffsetDateTime.now()));
    }
}

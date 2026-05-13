package promo.bot.beedle_deals.adapters.in.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import promo.bot.beedle_deals.adapters.in.web.dtos.ErrorDTO;
import promo.bot.beedle_deals.core.exceptions.AlreadyExistsException;
import promo.bot.beedle_deals.core.exceptions.NotificationDeliveryException;
import promo.bot.beedle_deals.core.exceptions.UnprocessableEntityException;

import java.time.OffsetDateTime;

@RestControllerAdvice
@Slf4j
public class WebExceptionHandler {
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> alreadyExistsException(AlreadyExistsException e) {
        return ResponseEntity.status(400).body(new ErrorDTO("ALREADY_EXISTS", e.getMessage(), OffsetDateTime.now()));
    }

    @ExceptionHandler(NotificationDeliveryException.class)
    public ResponseEntity<ErrorDTO> notificationDeliveryException(NotificationDeliveryException e) {
        return ResponseEntity.status(400).body( new ErrorDTO("ERROR", e.getMessage(), OffsetDateTime.now()));
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<ErrorDTO> unprocessableEntity(UnprocessableEntityException e) {
        return ResponseEntity.status(422).body(new ErrorDTO("UNPROCESSABLE_ENTITY", e.getMessage(), OffsetDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> genericException(Exception e) {
        log.error("Failure -> ", e);
        return ResponseEntity.status(500).body(ErrorDTO.builder()
                .code("INTERNAL_SERVER_ERROR")
                .message("Internal server error, contact administrator")
                .timestamp(OffsetDateTime.now()).build()
        );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorDTO> noResourceFound(NoResourceFoundException e) {
        return ResponseEntity.status(404).body(new ErrorDTO("ROUTE_NOT_FOUND", e.getMessage(), OffsetDateTime.now()));
    }
}

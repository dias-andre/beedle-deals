package promo.bot.beedle_deals.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import promo.bot.beedle_deals.core.usecases.PostDealUseCase;
import promo.bot.beedle_deals.dtos.ProductDTO;

@RestController
public class NotificationController {
    private final PostDealUseCase dealUseCase;

    public NotificationController(PostDealUseCase dealUseCase) {
        this.dealUseCase = dealUseCase;
    }
    @PostMapping
    public ResponseEntity<String> sendNotification(@Valid @RequestBody ProductDTO dto) {
        this.dealUseCase.execute(dto.toDomain());

        return ResponseEntity.ok().body("Notification sent");
    }
}

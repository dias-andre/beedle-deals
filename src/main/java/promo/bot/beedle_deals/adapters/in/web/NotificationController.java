package promo.bot.beedle_deals.adapters.in.web;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import promo.bot.beedle_deals.core.usecases.PostDealUseCase;
import promo.bot.beedle_deals.adapters.in.web.dtos.SendProductDTO;

@RestController
public class NotificationController {
    private final PostDealUseCase dealUseCase;

    public NotificationController(PostDealUseCase dealUseCase) {
        this.dealUseCase = dealUseCase;
    }
    @PostMapping
    public ResponseEntity<String> sendNotification(@Valid @RequestBody SendProductDTO dto) {
        this.dealUseCase.execute(dto.product().toDomain(), dto.externalGroupId());

        return ResponseEntity.ok().body("Notification sent");
    }
}

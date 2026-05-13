package promo.bot.beedle_deals.adapters.in.web.dtos;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record ErrorDTO(
        String code,
        String message,
        OffsetDateTime timestamp
) {
}

package promo.bot.beedle_deals.adapters.in.web.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record SendProductDTO(
        @Valid ProductDTO product,
        @NotBlank String externalGroupId
) {
}

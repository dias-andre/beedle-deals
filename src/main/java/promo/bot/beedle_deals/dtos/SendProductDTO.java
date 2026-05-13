package promo.bot.beedle_deals.dtos;

import jakarta.validation.constraints.NotBlank;

public record SendProductDTO(
        ProductDTO product,
        @NotBlank String externalGroupId
) {
}

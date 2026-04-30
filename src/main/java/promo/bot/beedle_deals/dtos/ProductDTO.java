package promo.bot.beedle_deals.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.URL;
import promo.bot.beedle_deals.core.domain.Product;

public record ProductDTO(
        @NotBlank String name,
        @URL @NotBlank String affiliateUrl,
        @URL @NotBlank String imageUrl,
        @PositiveOrZero Integer priceInCents,
        @PositiveOrZero Integer discountPercent
) {
    public Product toDomain() {
        return new Product(this.name, this.affiliateUrl, this.imageUrl, this.priceInCents, this.discountPercent);
    }
}

package promo.bot.beedle_deals.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class Product {
    private String name;
    private String affiliateUrl;
    private String imageUrl;
    private Integer priceInCents;
    private Integer discountPercent;
}

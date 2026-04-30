package promo.bot.beedle_deals.core.ports;

import promo.bot.beedle_deals.core.domain.Product;

public interface NotificationServicePort {
    void sendPromotion(Product product);
}

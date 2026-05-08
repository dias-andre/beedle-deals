package promo.bot.beedle_deals.core.ports;

import promo.bot.beedle_deals.core.domain.Group;
import promo.bot.beedle_deals.core.domain.Product;
import promo.bot.beedle_deals.core.exceptions.NotificationDeliveryException;

public interface NotificationServicePort {
    void sendProduct(Product product, Group gp) throws NotificationDeliveryException;
}

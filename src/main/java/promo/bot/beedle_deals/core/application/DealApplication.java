package promo.bot.beedle_deals.core.application;

import promo.bot.beedle_deals.core.domain.Product;
import promo.bot.beedle_deals.core.ports.NotificationServicePort;
import promo.bot.beedle_deals.core.usecases.PostDealUseCase;

public class DealApplication implements PostDealUseCase {
    private final NotificationServicePort notificationService;

    public DealApplication(NotificationServicePort notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void execute(Product product) {
        notificationService.sendPromotion(product);
    }
}

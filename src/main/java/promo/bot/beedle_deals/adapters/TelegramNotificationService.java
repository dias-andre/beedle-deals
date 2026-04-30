package promo.bot.beedle_deals.adapters;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import promo.bot.beedle_deals.core.domain.Product;
import promo.bot.beedle_deals.core.ports.NotificationServicePort;

@Component
public class TelegramNotificationService implements NotificationServicePort {
    private final TelegramClient client;

    public TelegramNotificationService() {
        this.client = new OkHttpTelegramClient("token");
    }

    @Override
    public void sendPromotion(Product product) {
    }
}

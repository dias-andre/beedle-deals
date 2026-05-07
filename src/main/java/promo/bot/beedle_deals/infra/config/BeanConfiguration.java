package promo.bot.beedle_deals.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import promo.bot.beedle_deals.core.application.DealApplication;
import promo.bot.beedle_deals.core.ports.NotificationServicePort;
import promo.bot.beedle_deals.core.usecases.PostDealUseCase;

@Configuration
public class BeanConfiguration {
    @Bean
    public DealApplication postDealUseCase(NotificationServicePort notificationPort) {
        return new DealApplication(notificationPort);
    }
}

package promo.bot.beedle_deals.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import promo.bot.beedle_deals.application.DealApplication;
import promo.bot.beedle_deals.application.GroupApplication;
import promo.bot.beedle_deals.core.ports.GroupRepositoryPort;
import promo.bot.beedle_deals.core.ports.NotificationServicePort;

@Configuration
public class BeanConfiguration {
    @Bean
    public DealApplication postDealUseCase(NotificationServicePort notificationPort, GroupRepositoryPort repo) {
        return new DealApplication(notificationPort, repo);
    }

    @Bean
    public GroupApplication groupApplication(GroupRepositoryPort groupRepositoryPort) {
        return new GroupApplication(groupRepositoryPort);
    }
}

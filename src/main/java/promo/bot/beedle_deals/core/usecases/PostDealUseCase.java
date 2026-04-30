package promo.bot.beedle_deals.core.usecases;

import promo.bot.beedle_deals.core.domain.Product;

public interface PostDealUseCase {
    void execute(Product product);
}

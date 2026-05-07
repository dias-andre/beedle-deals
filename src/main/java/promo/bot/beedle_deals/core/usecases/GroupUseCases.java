package promo.bot.beedle_deals.core.usecases;

import promo.bot.beedle_deals.core.domain.Group;

import java.util.Optional;

public interface GroupUseCases {
    Group createGroup(String externalId);
    boolean deleteGroup(String externalId);
    Optional<Group> getGroup(String externalId);
}

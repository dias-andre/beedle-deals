package promo.bot.beedle_deals.core.ports;

import promo.bot.beedle_deals.core.domain.Group;

import java.util.Optional;

public interface GroupRepositoryPort {
    Group saveGroup(Group gp);
    boolean deleteGroup(String externalId);
    Optional<Group> getGroupByExternalId(String externalId);
}

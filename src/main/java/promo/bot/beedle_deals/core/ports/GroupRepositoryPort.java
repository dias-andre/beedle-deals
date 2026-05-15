package promo.bot.beedle_deals.core.ports;

import promo.bot.beedle_deals.core.domain.Category;
import promo.bot.beedle_deals.core.domain.Group;

import java.util.List;
import java.util.Optional;

public interface GroupRepositoryPort {
    Group saveGroup(Group gp);
    boolean deleteGroup(String externalId);
    Optional<Group> getGroupByExternalId(String externalId);
    List<Group> listGroupsByCategory(Category category);
    void addGroupCategory(Category category, Group group);
    void removeGroupCategory(Category category, Group group);
}

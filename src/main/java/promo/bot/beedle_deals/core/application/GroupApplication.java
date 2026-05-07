package promo.bot.beedle_deals.core.application;

import promo.bot.beedle_deals.core.domain.Group;
import promo.bot.beedle_deals.core.ports.GroupRepositoryPort;
import promo.bot.beedle_deals.core.usecases.GroupUseCases;

import java.time.OffsetDateTime;
import java.util.Optional;

public class GroupApplication implements GroupUseCases {
    private final GroupRepositoryPort repository;

    public GroupApplication(GroupRepositoryPort repo) {
        this.repository = repo;
    }

    @Override
    public Group createGroup(String externalId) {
        Group newgp = new Group(externalId, OffsetDateTime.now());
        this.repository.saveGroup(newgp);
        return newgp;
    }

    @Override
    public boolean deleteGroup(String externalId) {
        return this.repository.deleteGroup(externalId);
    }

    @Override
    public Optional<Group> getGroup(String externalId) {
        return this.repository.getGroupByExternalId(externalId);
    }
}

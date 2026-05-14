package promo.bot.beedle_deals.application;

import jakarta.transaction.Transactional;
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
    @Transactional
    public Group createGroup(String name, String externalId) {
        Group newgp = new Group(name, externalId, OffsetDateTime.now());
        this.repository.saveGroup(newgp);
        return newgp;
    }

    @Override
    @Transactional
    public boolean deleteGroup(String externalId) {
        return this.repository.deleteGroup(externalId);
    }

    @Override
    @Transactional
    public Optional<Group> getGroup(String externalId) {
        return this.repository.getGroupByExternalId(externalId);
    }
}

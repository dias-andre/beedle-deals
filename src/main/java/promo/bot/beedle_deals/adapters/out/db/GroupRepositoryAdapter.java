package promo.bot.beedle_deals.adapters.out.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import promo.bot.beedle_deals.core.domain.Group;
import promo.bot.beedle_deals.core.ports.GroupRepositoryPort;
import promo.bot.beedle_deals.adapters.out.db.models.ChatModel;

import java.util.Optional;

@Repository
public class GroupRepositoryAdapter implements GroupRepositoryPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Group saveGroup(Group gp) {
        ChatModel newChat = new ChatModel(gp);
        entityManager.persist(newChat);
        return newChat.toDomain();
    }

    @Override
    public boolean deleteGroup(String externalId) {
        int affectedLines = entityManager.createQuery(
                "DELETE FROM ChatModel c WHERE c.externalId = :externalId"
                ).setParameter("externalId", externalId).executeUpdate();

        return affectedLines > 0;
    }

    @Override
    public Optional<Group> getGroupByExternalId(String externalId) {
        ChatModel chat = entityManager.createQuery("SELECT c FROM ChatModel c WHERE c.externalId = :externalId", ChatModel.class)
                .setParameter("externalId", externalId)
                .getSingleResultOrNull();

        if(chat == null) {
            return Optional.empty();
        }

        return Optional.of(chat.toDomain());
    }
}

package promo.bot.beedle_deals.adapters.out.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import promo.bot.beedle_deals.core.domain.Category;
import promo.bot.beedle_deals.core.domain.Group;
import promo.bot.beedle_deals.core.exceptions.NotFoundException;
import promo.bot.beedle_deals.core.ports.GroupRepositoryPort;
import promo.bot.beedle_deals.adapters.out.db.models.ChatModel;

import java.util.List;
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

    @Override
    @SuppressWarnings("unchecked")
    public List<Group> listGroupsByCategory(Category category) {
        List<ChatModel> groupList =  entityManager.createNativeQuery("""
            SELECT c.* FROM chats c
            INNER JOIN chats_categories cc ON cc.chat_id = c.id
            WHERE cc.category_name = :category_name
        """, ChatModel.class)
                .setParameter("category_name", category.name())
                .getResultList();

        return groupList.stream()
                .map(this::mapToDomain)
                .toList();
    }

    @Override
    public void addGroupCategory(Category category, Group group) {
        int affectedLines = entityManager.createNativeQuery("""
            INSERT INTO chats_categories (chat_id, category_name)
            SELECT id, :category_name FROM chats WHERE external_id = :external_id
        """)
                .setParameter("category_name", category.name())
                .setParameter("external_id", group.getExternalId())
                .executeUpdate();

        if(affectedLines == 0) {
            throw new NotFoundException("Group with externalId: %s, not found!".formatted(group.getExternalId()));
        }
    }

    @Override
    public void removeGroupCategory(Category category, Group group) {
        int affectedLines = entityManager.createNativeQuery("""
            DELETE FROM chats_categories 
            WHERE category_name = :category_name
            AND chat_id = (SELECT id FROM chats WHERE external_id = :external_id)
        """)
                .setParameter("category_name", category.name())
                .setParameter("external_id", group.getExternalId())
                .executeUpdate();

        if(affectedLines == 0) {
            throw new NotFoundException("Group with externalId: %s, not found!".formatted(group.getExternalId()));
        }
    }

    private Group mapToDomain(ChatModel chat) {
        return new Group(chat.getName(), chat.getExternalId(), chat.getRegisteredAt(), chat.getCategories());
    }
}

package promo.bot.beedle_deals.adapters.out.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import promo.bot.beedle_deals.core.domain.Category;
import promo.bot.beedle_deals.core.domain.Group;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@Import(GroupRepositoryAdapter.class)
@ActiveProfiles("test")
public class GroupRepositoryAdapterTest {

    @Autowired
    private GroupRepositoryAdapter adapter;

    @Test
    void shouldSaveAndRetrieveGroupFromDatabase() {
        Group gp = new Group("deals-group","discord-000", OffsetDateTime.now());
        this.adapter.saveGroup(gp);
        Optional<Group> savedGp = adapter.getGroupByExternalId("discord-000");
        assertTrue(savedGp.isPresent());
        assertEquals("discord-000", savedGp.get().getExternalId());
    }

    @Test
    void shouldSaveAndDeleteGroup() {
        Group gp = new Group("deals-group", "123", OffsetDateTime.now());
        this.adapter.saveGroup(gp);
        boolean result = this.adapter.deleteGroup("123");
        assertTrue(result);
    }

    @Test
    void shouldGetEmptyGroupFromDb() {
        Optional<Group> gp = this.adapter.getGroupByExternalId("unknown");
        assertFalse(gp.isPresent());
    }

    @Test
    void shouldCreateGroupWithCategory() {
        Group gp = new Group("game-group", "1231231212", OffsetDateTime.now(), List.of(Category.GAMES));
        this.adapter.saveGroup(gp);
        List<Group> groupList = this.adapter.listGroupsByCategory(Category.GAMES);
        assertEquals(gp.getExternalId(), groupList.get(0).getExternalId());
    }

    @Test
    void shouldAddCategory() {
        Group gp = new Group("hardware-group", "!23232", OffsetDateTime.now());
        this.adapter.saveGroup(gp);
        this.adapter.addGroupCategory(Category.HARDWARE, gp);
        List<Group> grouplist = this.adapter.listGroupsByCategory(Category.HARDWARE);
        assertEquals(gp.getExternalId(), grouplist.get(0).getExternalId());
    }

    @Test
    void shouldRemoveCategory() {
        Group gp = new Group("eletronic-group", "434343", OffsetDateTime.now(), List.of(Category.ELECTRONICS));
        this.adapter.saveGroup(gp);
        this.adapter.removeGroupCategory(Category.ELECTRONICS, gp);
        List<Group> groupList = this.adapter.listGroupsByCategory(Category.ELECTRONICS);
        assertEquals(0, groupList.size());
    }
}

package promo.bot.beedle_deals.adapters.out.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import promo.bot.beedle_deals.core.domain.Group;

import java.time.OffsetDateTime;
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
        Group gp = new Group("discord-000", OffsetDateTime.now());
        this.adapter.saveGroup(gp);
        Optional<Group> savedGp = adapter.getGroupByExternalId("discord-000");
        assertTrue(savedGp.isPresent());
        assertEquals("discord-000", savedGp.get().getExternalId());
    }

    @Test
    void shouldSaveAndDeleteGroup() {
        Group gp = new Group("123", OffsetDateTime.now());
        this.adapter.saveGroup(gp);
        boolean result = this.adapter.deleteGroup("123");
        assertTrue(result);
    }

    @Test
    void shouldGetEmptyGroupFromDb() {
        Optional<Group> gp = this.adapter.getGroupByExternalId("unknown");
        assertFalse(gp.isPresent());
    }
}

package promo.bot.beedle_deals.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import promo.bot.beedle_deals.core.domain.Group;
import promo.bot.beedle_deals.core.ports.GroupRepositoryPort;
import promo.bot.beedle_deals.core.usecases.GroupUseCases;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GroupApplicationTest {

    @Mock
    private GroupRepositoryPort repository;

    private GroupUseCases groupApplication;

    @BeforeEach
    void setUp() {
        groupApplication = new GroupApplication(repository);
    }

    @Test
    void shouldCreateGroup() {
        when(repository.saveGroup(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Group result = groupApplication.createGroup("deals-group", "channel-123");

        assertEquals("channel-123", result.getExternalId());
        assertNotNull(result.getRegisteredAt());
        verify(repository).saveGroup(any());
    }

    @Test
    void shouldDeleteExistingGroup() {
        when(repository.deleteGroup("channel-123")).thenReturn(true);

        assertTrue(groupApplication.deleteGroup("channel-123"));
    }

    @Test
    void shouldReturnFalseWhenDeletingNonExistentGroup() {
        when(repository.deleteGroup("unknown")).thenReturn(false);

        assertFalse(groupApplication.deleteGroup("unknown"));
    }

    @Test
    void shouldFindExistingGroup() {
        var group = new Group("deals-group", "channel-123", null);
        when(repository.getGroupByExternalId("channel-123")).thenReturn(Optional.of(group));

        Optional<Group> result = groupApplication.getGroup("channel-123");

        assertTrue(result.isPresent());
        assertEquals("channel-123", result.get().getExternalId());
    }

    @Test
    void shouldReturnEmptyWhenGroupNotFound() {
        when(repository.getGroupByExternalId("unknown")).thenReturn(Optional.empty());

        assertFalse(groupApplication.getGroup("unknown").isPresent());
    }
}

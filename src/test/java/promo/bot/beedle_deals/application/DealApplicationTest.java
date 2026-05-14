package promo.bot.beedle_deals.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import promo.bot.beedle_deals.core.domain.Group;
import promo.bot.beedle_deals.core.domain.Product;
import promo.bot.beedle_deals.core.exceptions.NotificationDeliveryException;
import promo.bot.beedle_deals.core.exceptions.UnprocessableEntityException;
import promo.bot.beedle_deals.core.ports.GroupRepositoryPort;
import promo.bot.beedle_deals.core.ports.NotificationServicePort;
import promo.bot.beedle_deals.core.usecases.PostDealUseCase;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DealApplicationTest {

    @Mock
    private NotificationServicePort notificationService;

    @Mock
    private GroupRepositoryPort groupRepository;

    private PostDealUseCase dealApplication;

    private Product validProduct;

    @BeforeEach
    void setUp() {
        dealApplication = new DealApplication(notificationService, groupRepository);
        validProduct = new Product("Gaming Headset", "https://example.com/deal", "https://example.com/img.jpg", 19990, 25);
    }

    @Test
    void shouldSendNotificationWhenGroupExists() {
        var group = new Group("123456789012345678", OffsetDateTime.now());
        when(groupRepository.getGroupByExternalId("123456789012345678")).thenReturn(Optional.of(group));

        dealApplication.execute(validProduct, "123456789012345678");

        verify(notificationService).sendProduct(validProduct, group);
    }

    @Test
    void shouldThrowWhenGroupIdIsEmpty() {
        assertThrows(UnprocessableEntityException.class, () ->
                dealApplication.execute(validProduct, ""));
        verifyNoInteractions(notificationService);
        verifyNoInteractions(groupRepository);
    }

    @Test
    void shouldThrowWhenGroupNotFound() {
        when(groupRepository.getGroupByExternalId("unknown")).thenReturn(Optional.empty());

        assertThrows(NotificationDeliveryException.class, () ->
                dealApplication.execute(validProduct, "unknown"));
        verify(notificationService, never()).sendProduct(any(), any());
    }
}

package promo.bot.beedle_deals.adapters.in.web;

import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import promo.bot.beedle_deals.core.exceptions.NotificationDeliveryException;
import promo.bot.beedle_deals.core.exceptions.UnprocessableEntityException;
import promo.bot.beedle_deals.core.usecases.PostDealUseCase;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
    controllers = NotificationController.class,
    properties = {"spring.profiles.active=test"}
)
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PostDealUseCase dealUseCase;

    @Test
    void shouldReturn200WhenNotificationIsSent() throws Exception {
        var body = Map.of(
                "product", Map.of(
                        "name", "Gaming Headset",
                        "affiliateUrl", "https://example.com/deal",
                        "imageUrl", "https://example.com/img.jpg",
                        "priceInCents", 19990,
                        "discountPercent", 25
                ),
                "externalGroupId", "123456789012345678"
        );

        mockMvc.perform(post("/api/v1/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(content().string("Notification sent"));
    }

    @Test
    void shouldReturn400WhenExternalGroupIdIsBlank() throws Exception {
        var body = Map.of(
                "product", Map.of(
                        "name", "Product",
                        "affiliateUrl", "https://example.com",
                        "imageUrl", "https://example.com/img.jpg",
                        "priceInCents", 1000,
                        "discountPercent", 10
                ),
                "externalGroupId", ""
        );

        mockMvc.perform(post("/api/v1/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenProductNameIsBlank() throws Exception {
        var body = Map.of(
                "product", Map.of(
                        "name", "",
                        "affiliateUrl", "https://example.com",
                        "imageUrl", "https://example.com/img.jpg",
                        "priceInCents", 1000,
                        "discountPercent", 10
                ),
                "externalGroupId", "channel-123"
        );

        mockMvc.perform(post("/api/v1/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenAffiliateUrlIsInvalid() throws Exception {
        var body = Map.of(
                "product", Map.of(
                        "name", "Product",
                        "affiliateUrl", "not-a-url",
                        "imageUrl", "https://example.com/img.jpg",
                        "priceInCents", 1000,
                        "discountPercent", 10
                ),
                "externalGroupId", "channel-123"
        );

        mockMvc.perform(post("/api/v1/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenPriceIsNegative() throws Exception {
        var body = Map.of(
                "product", Map.of(
                        "name", "Product",
                        "affiliateUrl", "https://example.com",
                        "imageUrl", "https://example.com/img.jpg",
                        "priceInCents", -1,
                        "discountPercent", 10
                ),
                "externalGroupId", "channel-123"
        );

        mockMvc.perform(post("/api/v1/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn422WhenUseCaseThrowsUnprocessableEntity() throws Exception {
        doThrow(new UnprocessableEntityException("invalid")).when(dealUseCase).execute(any(), any());

        var body = Map.of(
                "product", Map.of(
                        "name", "Product",
                        "affiliateUrl", "https://example.com",
                        "imageUrl", "https://example.com/img.jpg",
                        "priceInCents", 1000,
                        "discountPercent", 10
                ),
                "externalGroupId", "channel-123"
        );

        mockMvc.perform(post("/api/v1/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void shouldReturn400WhenUseCaseThrowsNotificationDelivery() throws Exception {
        doThrow(new NotificationDeliveryException("group not found")).when(dealUseCase).execute(any(), any());

        var body = Map.of(
                "product", Map.of(
                        "name", "Product",
                        "affiliateUrl", "https://example.com",
                        "imageUrl", "https://example.com/img.jpg",
                        "priceInCents", 1000,
                        "discountPercent", 10
                ),
                "externalGroupId", "channel-123"
        );

        mockMvc.perform(post("/api/v1/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }
}

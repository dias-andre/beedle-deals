package promo.bot.beedle_deals.core.domain;

import java.time.OffsetDateTime;

public class Group {
    private final String externalId;
    private final OffsetDateTime registeredAt;

    public Group(String externalId, OffsetDateTime registeredAt) {
        this.externalId = externalId;
        this.registeredAt = registeredAt;
    }

    public String getExternalId() {
        return externalId;
    }

    public OffsetDateTime getRegisteredAt() {
        return registeredAt;
    }
}

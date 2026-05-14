package promo.bot.beedle_deals.core.domain;

import java.time.OffsetDateTime;

public class Group {
    private String name;
    private final String externalId;
    private final OffsetDateTime registeredAt;

    public Group(String name, String externalId, OffsetDateTime registeredAt) {
        this.name = name;
        this.externalId = externalId;
        this.registeredAt = registeredAt;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OffsetDateTime getRegisteredAt() {
        return registeredAt;
    }
}

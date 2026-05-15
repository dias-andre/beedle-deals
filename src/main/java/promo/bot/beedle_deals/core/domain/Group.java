package promo.bot.beedle_deals.core.domain;

import java.time.OffsetDateTime;
import java.util.List;

public class Group {
    private String name;
    private final String externalId;
    private final OffsetDateTime registeredAt;
    private List<Category> categories;

    public Group(String name, String externalId, OffsetDateTime registeredAt) {
        this.name = name;
        this.externalId = externalId;
        this.registeredAt = registeredAt;
    }

    public Group(String name, String externalId, OffsetDateTime registeredAt, List<Category> categories) {
        this.name = name;
        this.externalId = externalId;
        this.registeredAt = registeredAt;
        this.categories = categories;
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}

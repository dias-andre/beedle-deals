package promo.bot.beedle_deals.core.domain;

public enum Category {
    HARDWARE("Hardware"),
    GAMES("Games"),
    SMARTPHONES("Smartphones"),
    ELECTRONICS("Electronics"),
    HOME_APPLIANCES("Home Appliances"),
    FASHION("Fashion"),
    SUBSCRIPTIONS("Subscriptions"),
    OTHER("Outros");


    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String toValue() {
        return this.name();
    }

    public static Category fromString(String text) {
        if (text == null || text.isBlank()) {
            return OTHER;
        }

        for (Category category : Category.values()) {
            if(category.name().equalsIgnoreCase(text)) {
                return category;
            }
        }

        return OTHER;
    }
}

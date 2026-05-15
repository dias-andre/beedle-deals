package promo.bot.beedle_deals.adapters.out.db.models;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;
import promo.bot.beedle_deals.core.domain.Category;
import promo.bot.beedle_deals.core.domain.Group;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter
@Table(name = "chats")
public class ChatModel {
    @Id
    private UUID id;
    private String name;

    @Column(updatable = false)
    private String externalId;

    @Column(updatable = false)
    private OffsetDateTime registeredAt;


    @ElementCollection(targetClass = Category.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "chats_categories",
            joinColumns = @JoinColumn(name = "chat_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "category_name")
    private List<Category> categories;

    public ChatModel(@NonNull Group gp) {
        this.registeredAt = gp.getRegisteredAt();
        this.externalId = gp.getExternalId();
        this.categories = gp.getCategories();
    }

    @PrePersist
    protected void onCreate() {
        this.id = UuidCreator.getTimeOrderedEpoch();
    }

    public Group toDomain() {
        return new Group(this.name, this.externalId, this.registeredAt, this.categories);
    }
}

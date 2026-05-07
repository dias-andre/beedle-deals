package promo.bot.beedle_deals.infra.database;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import promo.bot.beedle_deals.core.domain.Group;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter
public class ChatModel {
    @Id
    private UUID id;
    private String externalId;

    @Column(updatable = false)
    private OffsetDateTime registeredAt;

    public ChatModel(Group gp) {
        this.registeredAt = gp.getRegisteredAt();
        this.externalId = gp.getExternalId();
    }


    @PrePersist
    protected void onCreate() {
        this.id = UuidCreator.getTimeOrderedEpoch();
    }

    public Group toDomain() {
        return new Group(this.externalId, this.registeredAt);
    }
}

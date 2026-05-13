package promo.bot.beedle_deals.application;

import promo.bot.beedle_deals.core.domain.Product;
import promo.bot.beedle_deals.core.exceptions.NotificationDeliveryException;
import promo.bot.beedle_deals.core.exceptions.UnprocessableEntityException;
import promo.bot.beedle_deals.core.ports.GroupRepositoryPort;
import promo.bot.beedle_deals.core.ports.NotificationServicePort;
import promo.bot.beedle_deals.core.usecases.PostDealUseCase;

public class DealApplication implements PostDealUseCase {
    private final NotificationServicePort notificationService;
    private final GroupRepositoryPort groupRepository;

    public DealApplication(NotificationServicePort notificationService,
                           GroupRepositoryPort gpRepo) {
        this.notificationService = notificationService;
        this.groupRepository = gpRepo;
    }

    @Override
    public void execute(Product product, String groupId) {
        if(groupId.isEmpty()) {
            throw new UnprocessableEntityException("The externalGroupId must be a valid string");
        }
        var gpFound = this.groupRepository.getGroupByExternalId(groupId)
                .orElseThrow(() -> new NotificationDeliveryException("Group not found!"));

        notificationService.sendProduct(product, gpFound);
    }
}

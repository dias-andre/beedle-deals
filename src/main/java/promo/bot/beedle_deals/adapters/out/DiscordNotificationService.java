package promo.bot.beedle_deals.adapters.out;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import promo.bot.beedle_deals.adapters.in.discord.DiscordSlashCommandListener;
import promo.bot.beedle_deals.core.domain.Group;
import promo.bot.beedle_deals.core.domain.Product;
import promo.bot.beedle_deals.core.exceptions.NotificationDeliveryException;
import promo.bot.beedle_deals.core.ports.NotificationServicePort;

import java.awt.*;
import java.math.BigDecimal;

@Service
@Primary
public class DiscordNotificationService implements NotificationServicePort {

    private final JDA jdaApi;

    public DiscordNotificationService(@Value("${DISCORD_TOKEN}") String dcToken,
                                      DiscordSlashCommandListener cmdListener) {
        this.jdaApi = JDABuilder.createLight(dcToken)
                .enableIntents(GatewayIntent.GUILD_MESSAGES)
                .addEventListeners(cmdListener)
                .build();
    }

    @Override
    public void sendProduct(Product product, Group gp) {
        var channel = this.jdaApi.getTextChannelById(gp.getExternalId());
        if (channel == null)
            throw new NotificationDeliveryException("Failed to get channel with id %s".formatted(gp.getExternalId()));

        EmbedBuilder embed = new EmbedBuilder();

        BigDecimal decimalPrice = BigDecimal.valueOf(product.getPriceInCents())
                .divide(BigDecimal.valueOf(100));
        //        String message = """
        //                🚨 **NOVA OFERTA DETECTADA!** 🚨
        //
        //                📦 **Produto:** %s
        //                💰 **Preço:** R$ %.2f
        //
        //                🛒 **Compre aqui:** %s
        //                """.formatted(
        //                        product.getName(),
        //                        decimalPrice,
        //                        product.getAffiliateUrl()
        //        );
        embed.setTitle("🚨 " + product.getName(), product.getAffiliateUrl());
        embed.setDescription("Preço: **R$ %.2f**".formatted(decimalPrice));
        embed.setColor(Color.GREEN);
        embed.setImage(product.getImageUrl());
        channel.sendMessageEmbeds(embed.build()).queue();
    }
}

package promo.bot.beedle_deals.adapters.discord;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;
import promo.bot.beedle_deals.application.GroupApplication;
import promo.bot.beedle_deals.core.exceptions.AlreadyExistsException;

import java.awt.*;

@Slf4j
@Component
public class DiscordSlashCommandListener extends ListenerAdapter {
    private GroupApplication gpApplication;

    public DiscordSlashCommandListener(GroupApplication gpApp) {
        this.gpApplication = gpApp;
    }

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        JDA jda = event.getJDA();
        for (Guild guild : jda.getGuilds()) {
            guild.updateCommands().queue();
        }
        jda.updateCommands().addCommands(
                Commands.slash("add-channel", "Adds channel to list of promotion channels")
        ).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getName().equals("add-channel")) {
            var embed = new EmbedBuilder();
            try {
                this.gpApplication.createGroup(event.getChannelId());
                embed.setTitle("Channel registered successfully!");
                embed.setDescription("Beedle will ship products here!");
                embed.setColor(Color.GREEN);
                event.replyEmbeds(embed.build()).queue();
            } catch (AlreadyExistsException e) {
                embed.setTitle("Already registered!");
                embed.setDescription("I'm already shipping products to this channel!");
                embed.setColor(Color.ORANGE);
                event.replyEmbeds(embed.build()).queue();
            } catch (RuntimeException e) {
                embed.setTitle("Failed to register channel!");
                embed.setDescription("An internal error occurred! I can't ship products here!");
                embed.setColor(Color.RED);
                log.error("/add-channel: {}", event.getChannelId(), e);
                event.replyEmbeds(embed.build()).queue();
            }
        }
    }

    @Override
    public void onGuildJoin(@Nonnull GuildJoinEvent event) {
        Guild guild = event.getGuild();
        guild.updateCommands().addCommands(
                Commands.slash("add-channel", "Adds channel to list of promotion channels")
        ).queue();
    }
}

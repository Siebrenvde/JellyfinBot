package dev.siebrenvde.jellyfinbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.utils.FileUpload;

public class DiscordBot {

    private final TextChannel channel;

    public DiscordBot() {
        JDA jda = JDABuilder.createLight(Environment.BOT_TOKEN).build();

        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        channel = jda.getTextChannelById(Environment.CHANNEL_ID);
    }

    public void sendMessage(String message) {
        channel.sendMessage(message).queue();
    }

    public void sendEmbed(MessageEmbed embed) {
        channel.sendMessageEmbeds(embed).queue();
    }

    public void sendEmbed(MessageEmbed embed, FileUpload file) {
        channel.sendMessageEmbeds(embed).addFiles(file).queue();
    }

}

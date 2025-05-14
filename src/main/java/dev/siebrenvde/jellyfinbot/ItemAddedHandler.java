package dev.siebrenvde.jellyfinbot;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.FileUpload;
import org.apache.commons.text.StringEscapeUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.DateTimeException;
import java.time.Instant;

public record ItemAddedHandler(DiscordBot bot) implements Handler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemAddedHandler.class);
    private static final Gson GSON = new Gson();

    @Override
    public void handle(@NotNull Context ctx) {
        Request request;

        try {
            request = GSON.fromJson(
                StringEscapeUtils.unescapeHtml4(ctx.body()),
                Request.class
            );
        } catch (JsonSyntaxException e) {
            ctx.status(400);
            bot.sendMessage(
                "Failed to parse request body:" +
                "\n> " + e.getMessage() +
                "\n```\n" +
                ctx.body() +
                "\n```"
            );
            LOGGER.error("Failed to parse request body", e);
            return;
        }

        if (!request.isValid()) {
            ctx.status(400);
            // TODO: Make error more specific
            bot.sendMessage(
                "Invalid request:" +
                "\n```json\n" +
                ctx.body() +
                "\n```"
            );
            LOGGER.error("Invalid request:\n{}", ctx.body());
            return;
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(3381759);

        embed.setAuthor(
            request.title(),
            request.url()
        );

        if (!request.description().isEmpty()) embed.setDescription("> " + request.description());
        embed.appendDescription(String.format(
            "\n\n**[%s Now](%s)**",
            request.isAudio() ? "Listen" : "Watch",
            request.url()
        ));

        FileUpload image = null;
        if (!request.image().isEmpty()) {
            try {
                image = FileUpload.fromData(
                    new URI(request.image()).toURL().openStream(),
                    "image.jpg"
                );
                embed.setImage("attachment://image.jpg");
            } catch (IOException | URISyntaxException e) {
                bot.sendMessage("Invalid image: " + request.image());
                LOGGER.error("Failed to set image", e);
            }
        }

        embed.setFooter(
            request.serverName(),
            "https://files.siebrenvde.dev/jellyfin-icon.png"
        );

        if (!request.timestamp().isEmpty()) {
            try {
                embed.setTimestamp(Instant.parse(request.timestamp()));
            } catch(DateTimeException e) {
                bot.sendMessage("Invalid timestamp: " + request.timestamp());
                LOGGER.error("Failed to set timestamp", e);
            }
        }

        if (image != null) {
            bot.sendEmbed(embed.build(), image);
        } else {
            bot.sendEmbed(embed.build());
        }
    }

}

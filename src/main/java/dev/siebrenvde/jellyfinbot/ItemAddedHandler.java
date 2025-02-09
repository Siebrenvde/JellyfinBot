package dev.siebrenvde.jellyfinbot;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.FileUpload;
import org.apache.commons.text.StringEscapeUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.DateTimeException;
import java.time.Instant;

public record ItemAddedHandler(DiscordBot bot) implements Handler {

    @Override
    public void handle(@NotNull Context ctx) {
        Request request;

        try {
            request = new Gson().fromJson(
                StringEscapeUtils.unescapeHtml4(ctx.body()),
                Request.class
            );
        } catch(JsonSyntaxException e) {
            ctx.status(400);
            bot.sendMessage(
                "Failed to parse request body:" +
                "\n> " + e.getMessage() +
                "\n```\n" +
                ctx.body() +
                "\n```"
            );
            return;
        }

        if(!request.isValid()) {
            ctx.status(400);
            // TODO: Make error more specific
            bot.sendMessage(
                "Invalid request:" +
                "\n```json\n" +
                ctx.body() +
                "\n```"
            );
            return;
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(3381759);

        embed.setAuthor(
            request.title(),
            request.url()
        );

        if(!request.description().isEmpty()) embed.setDescription("> " + request.description());
        embed.appendDescription(String.format(
            "\n\n**[%s Now](%s)**",
            request.isAudio() ? "Listen" : "Watch",
            request.url()
        ));

        FileUpload image = null;
        if(!request.image().isEmpty()) {
            try {
                image = FileUpload.fromData(
                    new URI(request.image()).toURL().openStream(),
                    "image.jpg"
                );
                embed.setImage("attachment://image.jpg");
            } catch (IOException | URISyntaxException ignored) {
                bot.sendMessage("Invalid image: " + request.image());
            }
        }

        embed.setFooter(
            request.serverName(),
            "https://files.siebrenvde.dev/jellyfin-icon.png"
        );

        if(!request.timestamp().isEmpty()) {
            try {
                embed.setTimestamp(Instant.parse(request.timestamp()));
            } catch(DateTimeException ignored) {
                bot.sendMessage("Invalid timestamp: " + request.timestamp());
            }
        }

        if(image != null) {
            bot.sendEmbed(embed.build(), image);
        } else {
            bot.sendEmbed(embed.build());
        }
    }

}

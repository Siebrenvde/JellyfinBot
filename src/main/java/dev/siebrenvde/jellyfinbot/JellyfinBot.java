package dev.siebrenvde.jellyfinbot;

import io.javalin.Javalin;

public class JellyfinBot {

    public static void main(String[] args) {
        DiscordBot bot = new DiscordBot();

        Javalin.create()
            .post("item_added", new ItemAddedHandler(bot))
            .start(Environment.WEBSERVER_PORT);
    }

}
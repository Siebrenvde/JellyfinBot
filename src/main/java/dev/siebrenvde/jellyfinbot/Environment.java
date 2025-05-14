package dev.siebrenvde.jellyfinbot;

import io.github.cdimascio.dotenv.Dotenv;

public class Environment {

    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    public static final int WEBSERVER_PORT = Integer.parseInt(dotenv.get("WEBSERVER_PORT"));
    public static final String BOT_TOKEN = dotenv.get("BOT_TOKEN");
    public static final String CHANNEL_ID = dotenv.get("CHANNEL_ID");

}

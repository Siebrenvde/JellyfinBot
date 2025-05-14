## What is this?

The [Jellyfin webhook plugin](https://github.com/jellyfin/jellyfin-plugin-webhook) uses webhooks (duh) to send updates to Discord and cannot access images from a local Jellyfin instance. 

I wrote this to fix the lack of images by using a bot and uploading the images.

> [!NOTE]
> This bot is tailored to how I want the messages to look.  
> It is currently not possible to customise anything.

## Running
If you, for some reason, want to run this yourself, I've provided some basic steps that should get it running.

This project requires `Java 21` and for the [Jellyfin webhook plugin](https://github.com/jellyfin/jellyfin-plugin-webhook) to be installed on your Jellyfin instance.

You can find a pre-built JAR under [releases](https://github.com/Siebrenvde/JellyfinBot/releases) or you can [build it yourself](#building).

Copy [.env.example](https://github.com/Siebrenvde/JellyfinBot/blob/main/.env.example) to a new file called `.env` and configure the port, bot token and channel id.

In the Webhook configuration in your Jellyfin dashboard, add a new generic destination.  
The webhook url should be `http://[your-local-ip]:[port]/item_added`  
Make sure to select the `Item Added` notification type and check all types you want to be notified for under `Item Type`.

Copy the contents of [webhook.handlebars](https://github.com/Siebrenvde/JellyfinBot/blob/main/webhook.handlebars) to the `Template` field. 

You can then run the bot using:
```shell
java -jar JellyfinBot-VERSION.jar
```

I may add some better instructions for running this on Docker in the future.

## Building

Build the project using:
```shell
./gradlew clean shadowJar
```

The JAR file will be located at `build/libs/JellyfinBot-VERSION.jar`

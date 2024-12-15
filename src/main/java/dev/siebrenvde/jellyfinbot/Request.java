package dev.siebrenvde.jellyfinbot;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Objects;

public record Request(
    String title,
    String url,
    String image,
    String description,
    String type,
    @SerializedName("server_name") String serverName,
    String timestamp
) {
    public boolean isValid() {
        return Arrays.stream(new String[] {
            title,
            url,
            image,
            description,
            type,
            serverName,
            timestamp
        }).allMatch(Objects::nonNull)
            && !title.isEmpty()
            && !url.isEmpty();
    }

    public boolean isAudio() {
        return
            !type.isEmpty()
            && (type.equalsIgnoreCase("album")
                || type.equalsIgnoreCase("audio"));
    }
}

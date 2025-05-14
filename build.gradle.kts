plugins {
    id("java")
    alias(libs.plugins.shadow)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.javalin)
    implementation(libs.gson)
    implementation(libs.jda) {
        exclude(module = "opus-java")
    }
    implementation(libs.dotenv)
    implementation(libs.commons.text)
    implementation(libs.bundles.logging)
}

tasks.jar {
    manifest.attributes(
        "Main-Class" to "dev.siebrenvde.jellyfinbot.JellyfinBot"
    )
}

tasks.shadowJar {
    archiveClassifier = ""
}

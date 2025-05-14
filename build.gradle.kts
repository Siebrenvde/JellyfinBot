plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.6"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:6.6.0")
    implementation("org.slf4j:slf4j-simple:2.0.17")
    implementation("com.google.code.gson:gson:2.13.1")
    implementation("net.dv8tion:JDA:5.5.1") {
        exclude(module = "opus-java")
    }
    implementation("io.github.cdimascio:dotenv-java:3.2.0")
    implementation("org.apache.commons:commons-text:1.13.1")
}

tasks.jar {
    manifest.attributes(
        "Main-Class" to "dev.siebrenvde.jellyfinbot.JellyfinBot"
    )
}

tasks.shadowJar {
    archiveClassifier = ""
}

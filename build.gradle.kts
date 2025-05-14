plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.5"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:6.4.0")
    implementation("org.slf4j:slf4j-simple:2.0.16")
    implementation("com.google.code.gson:gson:2.12.0")
    implementation("net.dv8tion:JDA:5.3.0") {
        exclude(module = "opus-java")
    }
    implementation("io.github.cdimascio:dotenv-java:3.1.0")
    implementation("org.apache.commons:commons-text:1.13.0")
}

tasks.jar {
    manifest.attributes(
        "Main-Class" to "dev.siebrenvde.jellyfinbot.JellyfinBot"
    )
}

tasks.shadowJar {
    archiveClassifier = ""
}

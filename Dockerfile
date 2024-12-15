FROM eclipse-temurin:21
RUN mkdir "/opt/app"
COPY build/libs/JellyfinBot-1.0.0.jar /opt/app
CMD ["java", "-jar", "/opt/app/JellyfinBot-1.0.0.jar"]
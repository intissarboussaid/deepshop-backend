# Étape 1 : Build de l'application
FROM gradle:8.13-jdk17 AS build
WORKDIR /app

# Copier les fichiers Gradle et le code source
COPY build.gradle settings.gradle ./
COPY src ./src

# Compiler ton projet (en sautant les tests)
RUN gradle clean build -x test

# Étape 2 : Image finale d'exécution
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copier le jar généré depuis l'étape précédente
COPY --from=build /app/build/libs/*.jar app.jar

# Exposer le port 8080
EXPOSE 8080

# Lancer l'application Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]

# Utilise une image Java officielle
FROM openjdk:17-jdk-slim

# Crée un dossier pour l'application
WORKDIR /app

# Copie le fichier .jar dans le conteneur
COPY build/libs/association-site-0.0.1-SNAPSHOT.jar app.jar

# Expose le port utilisé par Spring Boot
EXPOSE 8080

# Commande pour démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]

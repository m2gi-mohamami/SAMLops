# Utilisation d'une image de base Java
FROM maven:3.8.1-openjdk-11 AS build

# Copie du projet Maven dans le répertoire /usr/src/app du conteneur
COPY . /usr/src/app

# Définition du répertoire de travail comme répertoire du projet
WORKDIR /usr/src/app

# Exécution de Maven pour compiler l'application
RUN mvn clean compile test package

# Deuxième étape : création d'une image contenant uniquement l'application compilée
FROM openjdk:11-jre-slim

# Copie de l'artefact JAR depuis l'étape précédente dans le répertoire /app du conteneur
COPY --from=build /usr/src/app/target/projetDevOps-1.0-SNAPSHOT.jar /app/

# Définition du répertoire de travail comme répertoire de l'application
WORKDIR /app

# Commande par défaut à exécuter lorsque le conteneur démarre
CMD ["java", "-jar", "projetDevOps-1.0-SNAPSHOT.jar"]

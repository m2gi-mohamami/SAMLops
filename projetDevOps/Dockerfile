#Utilisation d'une image de base Maven pour construire l'application
FROM maven:3.8.1-openjdk-11 AS build

#Copie du projet Maven dans le répertoire /usr/src/app du conteneur
COPY . /usr/src/app

#Définition du répertoire de travail comme répertoire du projet
WORKDIR /usr/src/app

#Exécution de Maven pour compiler l'application et créer le fichier JAR
RUN mvn clean compile test package

#Deuxième étape : création d'une image contenant uniquement l'application compilée
FROM openjdk:11

#Copie du fichier JAR dans le répertoire /app du conteneur
COPY --from=build /usr/src/app/target/projetDevOps-1.0-SNAPSHOT.jar /app/

#Définition du répertoire de travail comme répertoire de l'application
WORKDIR /app

#Commande par défaut à exécuter lorsque le conteneur démarre
CMD ["java", "-jar", "projetDevOps-1.0-SNAPSHOT.jar"]
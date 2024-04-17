# Utilisez l'image officielle Maven avec OpenJDK 11 pour construire et tester le projet
FROM maven:3.8.1-openjdk-11 AS build

# Copiez l'intégralité du projet dans le conteneur, en supposant que votre Dockerfile se trouve à la racine du projet dans le dossier projetDevOps
COPY . /usr/src/projetDevOps

# Définissez le répertoire de travail dans lequel Maven va s'exécuter
WORKDIR /usr/src/projetDevOps

# Compilez et testez le projet. Maven va télécharger toutes les dépendances nécessaires.
RUN mvn clean test

# Commande par défaut à exécuter lorsque le conteneur démarre
CMD ["mvn", "test"]
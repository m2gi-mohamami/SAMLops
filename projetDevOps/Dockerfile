FROM maven:3.8.1-openjdk-11 AS build
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp

# Ajoutez ces commandes pour déboguer
RUN ls -la /usr/src/myapp
RUN cat /usr/src/myapp/pom.xml

RUN mvn clean test

# Commande par défaut à exécuter lorsque le conteneur démarre
CMD ["mvn", "test"]

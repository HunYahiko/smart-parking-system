FROM java:8
WORKDIR /srv/
ADD target/parkingapp-0.0.1-SNAPSHOT.jar parking-app-backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "parking-app-backend.jar"]

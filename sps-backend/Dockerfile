FROM java:8
WORKDIR /srv/
ADD target/parkingapp-0.0.1-SNAPSHOT.jar parking-app-backend.jar
EXPOSE 8080
CMD java  $JAVA_OPTS -jar parking-app-backend.jar -Dspring.profiles.active=prod
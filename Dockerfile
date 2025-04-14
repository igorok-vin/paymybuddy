FROM bellsoft/liberica-openjdk-alpine:latest
ADD target/paymybuddy-0.0.1-SNAPSHOT.jar paymybuddy-0.0.1-SNAPSHOT.jar
EXPOSE 8086
ENTRYPOINT ["java","-jar","paymybuddy-0.0.1-SNAPSHOT.jar"]
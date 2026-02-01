FROM bellsoft/liberica-openjre-alpine:25-cds

WORKDIR /application

COPY build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

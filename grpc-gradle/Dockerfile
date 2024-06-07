FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . .

# build the application's jar file using gradle
RUN ./gradlew clean build --no-daemon

RUN ./gradlew bootJar --no-daemon

# COPY /app/build/libs/grpc-gradle-0.0.1.jar /app/app.jar

EXPOSE 50051

ENTRYPOINT ["java","-jar","/app/build/libs/grpc-gradle-0.0.1.jar"]
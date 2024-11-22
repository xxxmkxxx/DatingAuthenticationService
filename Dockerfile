FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

RUN ./gradlew build -x test --no-daemon || return 0

COPY . .

RUN ./gradlew build -x test --no-daemon

CMD ["java", "-jar", "build/libs/DatingAuthenticationService-0.0.1-SNAPSHOT.jar"]
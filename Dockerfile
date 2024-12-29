FROM gradle:8.10-jdk21 AS build-stage
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build -x test

FROM openjdk:21-jdk-slim AS prefinal-stage
ARG JAR_FILE=/home/gradle/src/build/libs/KotlinServer-0.0.1-SNAPSHOT.jar
COPY --from=build-stage ${JAR_FILE} app.jar
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf /app.jar)

FROM openjdk:21-jdk-slim
VOLUME /tmp
ARG DEPENDENCY=/target/dependency
COPY --from=prefinal-stage ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=prefinal-stage ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=prefinal-stage ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java", "-cp","app:app/lib/*","ru.labs.kotlinserver.KotlinServerApplicationKt"]
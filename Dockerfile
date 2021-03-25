# ======== Build image ========
FROM mozilla/sbt:8u232_1.4.1 as build

RUN mkdir app
WORKDIR /app

COPY project/build.properties project/plugins.sbt project/
COPY build.sbt build.sbt
COPY src src

RUN [ "sbt", "clean", "assembly" ]

# ======== Deploy image ========
FROM openjdk:8

COPY src/main/resources resources

COPY --from=build /app/target/scala-2.13/server.jar server.jar
CMD ["java", "-jar", "server.jar"]

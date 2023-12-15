FROM gradle:latest AS BUILD
WORKDIR /usr/app/api/
COPY . .
RUN gradle build

FROM openjdk:17
ENV JAR_NAME=kalah-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/api/
WORKDIR $APP_HOME
COPY --from=BUILD $APP_HOME .
EXPOSE 8080
ENTRYPOINT exec java -jar $APP_HOME/build/libs/$JAR_NAME
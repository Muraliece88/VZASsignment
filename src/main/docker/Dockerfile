FROM adoptopenjdk:11-jre-hotspot
EXPOSE 1234
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} docker-customerOrder.jar
ENTRYPOINT ["java", "-jar", "docker-customerOrder.jar"]
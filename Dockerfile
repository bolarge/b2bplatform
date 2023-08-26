FROM openjdk:17-ea-4-jdk
LABEL maintainer="bolaji.salau@gmail.com"
#VOLUME /tmp
#EXPOSE 8585
ARG JAR_FILE=target/b2bplatform-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} b2bplatform.jar
ENTRYPOINT ["java", "-jar", "b2bplatform.jar"]
#ADD ${JAR_FILE} b2bplatform.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/b2bplatform.jar"]

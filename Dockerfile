FROM openjdk:11
ADD target/view.jar view.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "view.jar"]
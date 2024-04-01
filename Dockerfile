FROM openjdk:8
EXPOSE 8080
ADD target/spring-boot-contextify.jar spring-boot-contextify.jar
ENTRYPOINT ["java","-jar","/spring-boot-contextify.jar"]
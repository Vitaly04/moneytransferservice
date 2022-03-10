
FROM adoptopenjdk/openjdk11:jre-11.0.13_8-alpine

EXPOSE 5500

ADD target/moneytransferservice-0.0.1-SNAPSHOT.jar myapp.jar

CMD ["java", "-jar", "myapp.jar"]


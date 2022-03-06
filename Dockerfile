FROM node:alpine

WORKDIR /user/app/front

EXPOSE 3000

COPY . .

RUN npm install

CMD ["npm", "start"]

FROM adoptopenjdk/openjdk11:jre-11.0.13_8-alpine

EXPOSE 5500

ADD target/moneytransferservice-0.0.1-SNAPSHOT.jar myapp.jar

ENTRYPOINT ["java", "-jar", "myapp.jar"]


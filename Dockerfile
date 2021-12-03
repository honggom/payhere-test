FROM openjdk:11

EXPOSE 8080

ADD build/libs/payhere-0.0.1.jar payhere-0.0.1.jar

ENTRYPOINT ["java","-jar","payhere-0.0.1.jar"]
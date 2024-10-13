FROM alpine/java:21.0.4

WORKDIR /app
COPY ./cart.jar /app/cart.jar

ENTRYPOINT ["java", "-jar", "cart.jar"]
EXPOSE 8080
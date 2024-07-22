FROM alpine/java:21.0.4

WORKDIR /app
COPY build/libs/cart.jar /app/cart.jar

ENTRYPOINT ["java", "-jar", "cart.jar"]
EXPOSE 8080
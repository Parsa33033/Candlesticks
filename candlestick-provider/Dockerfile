FROM openjdk:11
WORKDIR /usr/src/candlestick-provider/
COPY ./target .
EXPOSE 9000 9000
CMD ["java", "-jar", "candlestick-provider-0.0.1-SNAPSHOT.jar"]
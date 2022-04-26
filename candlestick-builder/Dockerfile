FROM openjdk:11
WORKDIR /usr/src/candlestick-builder/
COPY ./target .
EXPOSE 9001 9001
CMD ["java", "-jar", "candlestick-builder-0.0.1-SNAPSHOT.jar"]
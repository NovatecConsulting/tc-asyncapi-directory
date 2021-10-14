FROM openjdk:11.0.12-jre
COPY ./build/dependent-libs/ /app
COPY ./build/libs/ /app
WORKDIR /app
CMD ["java","-cp", "/app/*","de.novatec.tc.KafkaApplication"]

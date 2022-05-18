# build stage
#FROM maven:3.8.5-openjdk-18-slim AS build
#COPY backend/src /home/app/src
#COPY backend/pom.xml /home/app
#RUN mvn -f /home/app/pom.xml clean package

# package stage
#FROM openjdk:18-jdk-slim
#COPY --from=build /home/app/target/scrumw.jar /usr/local/lib/scrumw.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "/usr/local/lib/scrumw.jar"]

FROM maven:3.8.5-openjdk-18-slim
WORKDIR /backend
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run
# 1. Specify a base image with Maven
FROM maven:3.8.3-openjdk-17

# 2. Set working directory within the container
WORKDIR /jwttoken

# 3. Build your application
COPY . /
# RUN mvn clean install

# 4. Create a new image with just the built JAR file and OpenJDK
FROM openjdk:17-jdk

# 5. Copy the JAR file from the first image
COPY --from=0 /jwttoken/target/jwttoken-0.0.1-SNAPSHOT.jar /jwttoken/app.jar

# 6. Expose the port your application listens on
EXPOSE 8083

# 7. Specify the command to run your application
ENTRYPOINT ["java", "-jar", "/jwttoken/app.jar"]
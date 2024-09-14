FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the service's jar file to the container
COPY target/service-discovery-0.0.1-SNAPSHOT.jar /app/service-discovery-0.0.1-SNAPSHOT.jar

# Expose the service's port
EXPOSE 8761

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/service-discovery-0.0.1-SNAPSHOT.jar"]

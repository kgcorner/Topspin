# Start with a base image containing Java runtime
FROM adoptopenjdk/openjdk11

# Add Maintainer Info
LABEL maintainer="kumargauravgupta3@gmail.com"

RUN echo "gateway-key:secret">/app.lst
# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=service-jar.jar

# Add the application's jar to the container
ADD ${JAR_FILE} service-jar.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005","-jar","/service-jar.jar"]
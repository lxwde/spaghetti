FROM java:openjdk-8-jre-alpine

ENV SLEEP 0

# add directly the war
ADD *.war /app.war

RUN sh -c 'touch /app.war'
VOLUME /tmp
EXPOSE 8080
CMD echo "The application will start in ${SLEEP}s..." && \
    sleep ${SLEEP} && \
    java -Djava.security.egd=file:/dev/./urandom -jar /app.war

FROM my.hub.com/library/jdk:1.8.3
COPY target/base-neuralnet-server.jar /app/base-neuralnet-server.jar
CMD java $JAVA_OPTS -Dpinpoint.agentId=${POD_IP} -jar /app/base-neuralnet-server.jar

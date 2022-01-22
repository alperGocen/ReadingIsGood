FROM tomcat:9.0

ENV JAVA_OPTS ""

COPY target/ROOT.jar pro.jar

EXPOSE 8080

CMD chmod +x pro.jar

CMD java ${JAVA_OPTS} -jar pro.jar
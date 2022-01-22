FROM tomcat:10.0
MAINTAINER readingisgood
EXPOSE 8081
COPY target/ROOT.jar /usr/local/tomcat/webapps/
mvn clean package spring-boot:repackage -Dmaven.test.skip=true

docker-compose build

docker-compose up

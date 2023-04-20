This is a java 17 Application You can run this application using 

mvn spring-boot:run

Use following command to run tests
mvn test

You can also test it using the following curl commands

curl -X POST http://localhost:8080/api/v1/grecords -F file=@exercise.csv
curl http://localhost:8080/api/v1/grecords
curl http://localhost:8080/api/v1/grecords/61086009
curl -X DELETE http://localhost:8080/api/v1/grecords

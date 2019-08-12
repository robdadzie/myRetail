# Spring RESTful Webservice 

POC implementation of 'myRetail RESTFul service'

## Getting Started
This is an end-to-end proof-of concept implementation for a products API.
The API aggregates product data from multiple sources and return a JSON representing
product details [Product Information + Product Price]

The endpoints are secured with Basic authentication for 2 credentials:
* user1/supersecret1
* user2/supersecret2
### Prerequisites

* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Redis](https://redis.io/)
* [Maven](https://maven.apache.org/)

### Installing
mvn clean install<br>
mvn spring-boot:run -Dserver.port=8085<br>


End with an example of getting some data out of the system or using it for a little demo

## Running Unit tests

mvn test


## Deployment
* Build package using **prod** profile<br>
  mvn spring-boot:run -Pprod<br>

* Deploy runnable jar<br>
  java -jar myRetail-1.0.0-SNAPSHOT.jar

## Fully functioning API with Swagger Documentation
[myRetail RESTFul service](http://54.210.21.128:8085)

## Author

* **Robert Dadzie**

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details



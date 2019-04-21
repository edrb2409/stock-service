# Stock Service

Store and manage stock data for all available products. Also, it provides basic statistics.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

- Java 11
- Docker

### Installing

Compilation

```
./gradlew build
```

Create docker image (edrb/stock-service)

```
./gradlew build docker
```

## Running the test cases

Run the test cases execute

```
./gradlew test
```

## Deployment

For deployment you have to have Docker installed.

### From code source

Create docker image (edrb/stock-service)

```
/.gradlew build docker
```

Run the docker image

```
docker run --name stock-service -p 8080:8080 edrb/stock-service
```

### Download docker image from Docker hub

Docker page [edrb/stock-service](https://cloud.docker.com/u/edrb/repository/docker/edrb/stock-service)

Pull the image 

```
docker push edrb/stock-service
```

Run the image

```
docker run --name stock-service -p 8080:8080 edrb/stock-service
```

## Build With

- SpringBoot
- Spring Framework (Core, Data, WebFlux)
- JUnit 5 Jupiter
- Mockito
- Gradle

## Author

- Daniel Ron - [edrb-profile](https://edrb.github.io)

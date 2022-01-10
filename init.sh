#!/bin/bash
./mvnw clean package -DskipTests
cp target/evolution-0.0.1-SNAPSHOT.jar ./
docker-compose up
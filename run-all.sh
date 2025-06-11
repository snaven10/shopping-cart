#!/bin/bash

# 🚀 Boot all microservices in parallel with their own ports

echo "Starting auth-service on port 8081..."
mvn -pl auth-service spring-boot:run &

echo "Starting product-service on port 8082..."
mvn -pl product-service spring-boot:run &

echo "Starting order-service on port 8083..."
mvn -pl order-service spring-boot:run &

echo "Starting payment-service on port 8084..."
mvn -pl payment-service spring-boot:run &

wait

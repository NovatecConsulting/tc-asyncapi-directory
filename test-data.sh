#!/bin/sh

#send asyncapi definitions to Kafka as test data
curl -X POST -d@src/main/resources/testApi1.json http://localhost:8080/kafka/asyncapi -H "content-type: application/json"
curl -X POST -d@src/main/resources/testApi1 http://localhost:8080/kafka/asyncapi -H "content-type: application/json"
curl -X POST -d@src/main/resources/testApi2.json http://localhost:8080/kafka/asyncapi -H "content-type: application/json"

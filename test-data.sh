#!/bin/sh

#send asyncapi definitions to Kafka as test data
curl -X POST -d@src/main/resources/bbc-ably-api.json http://localhost:8080/asyncapi -H "content-type: application/json"
curl -X POST -d@src/main/resources/ebay-api.json http://localhost:8080/asyncapi -H "content-type: application/json"
curl -X POST -d@src/main/resources/gitter-api.json http://localhost:8080/asyncapi -H "content-type: application/json"
curl -X POST -d@src/main/resources/open-weather-api.json http://localhost:8080/asyncapi -H "content-type: application/json"
curl -X POST -d@src/main/resources/user-registration-api.json http://localhost:8080/asyncapi -H "content-type: application/json"

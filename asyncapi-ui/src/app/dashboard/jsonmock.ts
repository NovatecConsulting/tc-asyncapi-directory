export const jsonMock = `
{
  "asyncapi": "2.0.0",
  "info": {
    "title": "Account Service",
    "version": "1.0.0",
    "description": "Manages user accounts in the system.\\n",
    "license": {
      "name": "Apache 2.0",
      "url": "https://www.apache.org/licenses/LICENSE-2.0"
    }
  },
  "servers": {
    "production": {
      "url": "localhost:5672",
      "protocol": "amqp",
      "description": "Test RabbitMQ broker"
    }
  },
  "channels": {
    "user/signedup": {
      "subscribe": {
        "summary": "Receive information about user signed up",
        "operationId": "receivedUserSignedUp",
        "message": {
          "description": "An event describing that a user just signed up.",
          "contentType": "avro/binary",
          "schemaFormat": "application/vnd.apache.avro+json;version=1.9.0",
          "payload": {
          }
        }
      }
    }
  }
}
`

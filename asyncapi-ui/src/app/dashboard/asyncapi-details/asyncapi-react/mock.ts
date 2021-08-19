export const mock = '{\n' +
  '    "asyncapi": "2.0.0",\n' +
  '    "info": {\n' +
  '      "title": "Account service",\n' +
  '      "description": "Manages user accounts in the system.",\n' +
  '      "termsOfService": "https://asyncapi.org/terms/",\n' +
  '      "contact": {\n' +
  '        "name": "API Support",\n' +
  '        "url": "https://www.asyncapi.org/support",\n' +
  '        "email": "support@asyncapi.org",\n' +
  '        "x-team-name": "My Team"\n' +
  '      },\n' +
  '      "license": {\n' +
  '        "name": "Apache 2.0",\n' +
  '        "url": "https://www.apache.org/licenses/LICENSE-2.0.html"\n' +
  '      },\n' +
  '      "version": "1.0.1"\n' +
  '    },\n' +
  '    "externalDocs": {\n' +
  '      "description": "More internal documentation is available here.",\n' +
  '      "url": "https://github.com/NovatecConsulting/tc-asyncapi-ui"\n' +
  '    },\n' +
  '    "servers": {\n' +
  '      "production": {\n' +
  '        "url": "localhost:5672",\n' +
  '        "protocol": "amqp",\n' +
  '        "description": "Test RabbitMQ broker"\n' +
  '      }\n' +
  '    },\n' +
  '    "channels": {\n' +
  '      "user/signedup": {\n' +
  '        "subscribe": {\n' +
  '          "summary": "Receive information about user signed up",\n' +
  '          "operationId": "receivedUserSignedUp",\n' +
  '          "message": {\n' +
  '            "description": "An event describing that a user just signed up.",\n' +
  '            "contentType": "avro/binary",\n' +
  '            "schemaFormat": "application/vnd.apache.avro+json;version=1.9.0",\n' +
  '            "payload": {\n' +
  '                "$ref": "https://raw.githubusercontent.com/NovatecConsulting/tc-paia-kafka-examples/main/src/main/avro/de/novatec/tc/account/v1/Account.avsc"\n' +
  '            }\n' +
  '          }\n' +
  '        }\n' +
  '      }\n' +
  '    }\n' +
  '  }'

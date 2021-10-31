#!/bin/sh

docker-compose -f docker/docker-compose.yaml up -d

echo 'Waiting for cluster to be available...'
sleep 20

echo 'Start creation of topic.'
docker exec -i kafka bash <<'EOF'
kafka-topics --create --topic asyncapi --bootstrap-server localhost:19092 --partitions 1
exit
EOF

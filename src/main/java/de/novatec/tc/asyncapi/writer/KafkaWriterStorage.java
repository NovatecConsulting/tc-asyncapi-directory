package de.novatec.tc.asyncapi.writer;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.confluent.kafka.serializers.KafkaJsonDeserializer;
import io.confluent.kafka.serializers.KafkaJsonDeserializerConfig;
import io.confluent.kafka.serializers.KafkaJsonSerializer;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.concurrent.ExecutionException;

import static java.util.stream.Collectors.toSet;

@Primary
@Service
public class KafkaWriterStorage implements WriterStorage, ConsumerSeekAware{
    public static final String ARTIFACT_ID_FIELD = "artifactId";
    public static final String VERSION_FIELD = "version";
    private static final String PROPERTIES_TOPIC_NAME = "message.topic.name";
    Map<String, SortedMap<Integer, JSONObject>> asyncApiCache;
    private final Map<String, String> properties;

    public KafkaWriterStorage(@Qualifier("kafkaConfig") Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public synchronized void add(String id, ObjectNode value) {
        Map<String, Object> producerConfig = new HashMap<>(properties);
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaJsonSerializer.class);
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaJsonSerializer.class);
        ObjectNode key = createKeyForRecord(id, (getLatestVersions().getOrDefault(id, 0)+1));

        try (Producer<ObjectNode, ObjectNode> producer = new KafkaProducer<>(producerConfig)) {
            producer.send(new ProducerRecord<>(properties.get(PROPERTIES_TOPIC_NAME), key, value)).get();
        } catch (InterruptedException e) {
            throw new KafkaException(e.getMessage(), e);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof KafkaException) {
                throw (KafkaException) e.getCause();
            }
            throw new KafkaException(e.getCause().getMessage(), e.getCause());
        }
    }

    private Map<String, Integer> getLatestVersions() {
        Map<String, Object> consumerConfig = new HashMap<>(properties);
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaJsonDeserializer.class);
        consumerConfig.put(KafkaJsonDeserializerConfig.JSON_KEY_TYPE, ObjectNode.class);
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        Consumer<ObjectNode, ?> consumer = new KafkaConsumer<ObjectNode, byte[]>(consumerConfig);
        Set<TopicPartition> partitionsToAssign =  consumer.partitionsFor(properties.get(PROPERTIES_TOPIC_NAME))
                .stream()
                .map(p -> new TopicPartition(p.topic(), p.partition()))
                .collect(toSet());
        consumer.assign(partitionsToAssign);
        consumer.seekToBeginning(partitionsToAssign);

        ConsumerRecords<ObjectNode, ?> records;
        Map<String, Integer> latestVersionMap = new HashMap<>();
        do {
           records = consumer.poll(Duration.ofMillis(200));
           for(ConsumerRecord<ObjectNode, ?> record : records) {
               latestVersionMap.put(record.key().get(ARTIFACT_ID_FIELD).asText(), record.key().get(VERSION_FIELD).asInt());
           }
        } while(!records.isEmpty());

        return latestVersionMap;
    }

    private ObjectNode createKeyForRecord(String artifactId, int version) {
        return JsonNodeFactory.instance.objectNode()
                .put(ARTIFACT_ID_FIELD, artifactId)
                .put(VERSION_FIELD, version);
    }
}


package de.novatec.tc;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.lang.NonNull;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class KafkaConsumer implements ConsumerSeekAware{
    private static final String INFO_FIELD = "info";
    private static final String ARTIFACT_ID_FIELD = "artifactId";
    private static final String VERSION_FIELD = "version";

    private Map<String, SortedMap<Integer, JSONObject>> asyncApiCache = new HashMap<>();
    private List<JSONObject> asyncApiSummaryCache;
    private final Map<String, String> properties;

    @Autowired
    public KafkaConsumer(@Qualifier("kafkaConfig") Map<String, String> properties) {
        this.properties = properties;
    }

    @KafkaListener(topics = "${kafka.message.topic.name}")
    public void listener(@Payload String receivedValue,
                         @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String receivedKey) {
        JSONObject key = new JSONObject(receivedKey);
        JSONObject value = new JSONObject(receivedValue);

        SortedMap<Integer, JSONObject> versionMap = asyncApiCache.computeIfAbsent(
                key.getString(ARTIFACT_ID_FIELD),
                (k) -> new TreeMap<>()
        );
        versionMap.put(key.getInt(VERSION_FIELD), value);
        calculateSummaryCache();
    }

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, @NonNull ConsumerSeekAware.ConsumerSeekCallback callback) {
        assignments.forEach((t, o) -> callback.seekToBeginning(t.topic(), t.partition()));
    }

    private void calculateSummaryCache() {
        List<JSONObject> summaryCache = new ArrayList<>();

        for (Map.Entry<String, SortedMap<Integer, JSONObject>> pair : asyncApiCache.entrySet()) {
            int version = pair.getValue().lastKey();
            JSONObject value = pair.getValue().get(version).getJSONObject(INFO_FIELD);
            summaryCache.add(new JSONObject()
                    .put(ARTIFACT_ID_FIELD, pair.getKey())
                    .put(VERSION_FIELD, version)
                    .put(INFO_FIELD, value));
        }
        asyncApiSummaryCache = summaryCache;
    }
}

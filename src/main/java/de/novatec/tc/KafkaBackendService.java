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
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaBackendService implements ConsumerSeekAware {
    private static final String INFO_FIELD = "info";
    private static final String ARTIFACT_ID_FIELD = "artifactId";
    private static final String VERSION_FIELD = "version";
    private static final String ASYNCAPI_DEF_FIELD = "asyncapi-def";
    private static final String PROPERTIES_TOPIC_NAME = "message.topic.name";

    private Map<String, SortedMap<Integer, JSONObject>> asyncApiCache = new HashMap<>();
    private List<JSONObject> asyncApiSummaryCache;
    private final Map<String, String> properties;

    @Autowired
    public KafkaBackendService(@Qualifier("kafkaConfig") Map<String, String> properties) {
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
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, @NonNull ConsumerSeekCallback callback) {
        assignments.forEach((t, o) -> callback.seekToBeginning(t.topic(), t.partition()));
    }

    public JSONObject getLatestVersionOfAsyncApi(String artifactId) {
        if (asyncApiCache.get(artifactId) != null) {
            int version = asyncApiCache.get(artifactId).lastKey();
            return asyncApiCache.get(artifactId).get(version);
        } else {
            return new JSONObject(); //empty response if not present
        }
    }

    public List<JSONObject> getAsyncApiSummary() {
        return asyncApiSummaryCache;
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

    public void publishAsyncApiDefinition(String asyncApi) {
        Map<String, Object> actualConfig = new HashMap<>(properties);
        actualConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        actualConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        JSONObject newAsyncApi = new JSONObject(asyncApi);
        String key = createKeyForRecord(newAsyncApi.getString(ARTIFACT_ID_FIELD));
        String value = newAsyncApi.get(ASYNCAPI_DEF_FIELD).toString();

        try (Producer<String, String> producer = new KafkaProducer<>(actualConfig)) {
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

    private String createKeyForRecord(String artifactId) {
        return new JSONObject()
                .put(ARTIFACT_ID_FIELD, artifactId)
                .put(VERSION_FIELD, getUpdateVersion(artifactId))
                .toString();
    }

    private int getUpdateVersion(String artifactId) {
        if (asyncApiCache.get(artifactId) != null) {
            return asyncApiCache.get(artifactId).lastKey() + 1;
        }
        return 0;
    }
}

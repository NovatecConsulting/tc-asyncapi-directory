package de.novatec.tc.asyncapi.reader;

import com.fasterxml.jackson.databind.node.ObjectNode;
import de.novatec.tc.AsyncApiRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.lang.NonNull;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toSet;

@Primary
@Service
public class KafkaReaderStorage implements ReaderStorage, ConsumerSeekAware{
    public static final String ID_FIELD = "artifactId";
    public static final String VERSION_FIELD = "version";

    Map<String, SortedMap<Integer, ObjectNode>> cache;

    public KafkaReaderStorage(Map<String, SortedMap<Integer, ObjectNode>> cache) {
        this.cache = cache;
    }

    public KafkaReaderStorage() {
        this(new HashMap<>());
    }

    @KafkaListener(topics = "${kafka.message.topic.name}")
    public void listener(@Payload ObjectNode value,
                         @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) ObjectNode key) {

        SortedMap<Integer, ObjectNode> versionMap = cache.computeIfAbsent(
                key.get(ID_FIELD).asText(),
                (k) -> new TreeMap<>()
        );
        versionMap.put(key.get(VERSION_FIELD).asInt(), value);

    }

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, @NonNull ConsumerSeekAware.ConsumerSeekCallback callback) {
        assignments.forEach((t, o) -> callback.seekToBeginning(t.topic(), t.partition()));
    }

    @Override
    public Optional<AsyncApiRecord> getByIdAndVersion(String id, int version) {
        if(cache.get(id) != null && cache.get(id).get(version) != null) {
            return Optional.of(new AsyncApiRecord(id, version, cache.get(id).get(version)));
        }
        return Optional.empty();
    }

    @Override
    public Optional<AsyncApiRecord> getLatestById(String id) {
        if(cache.get(id) != null) {
            return Optional.of(new AsyncApiRecord(id, cache.get(id).lastKey(), cache.get(id).get(cache.get(id).lastKey())));
        }
        return Optional.empty();
    }

    @Override
    public Set<AsyncApiRecord> getAllLatest() {
        return cache.entrySet().stream()
                .map(this::createLatestRecord)
                .collect(toSet());
    }

    private AsyncApiRecord createLatestRecord(Map.Entry<String, SortedMap<Integer, ObjectNode>> artifact) {
        return new AsyncApiRecord(artifact.getKey(), artifact.getValue().lastKey(), artifact.getValue().get(artifact.getValue().lastKey()));
    }

}


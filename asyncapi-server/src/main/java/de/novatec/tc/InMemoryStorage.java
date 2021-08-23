package de.novatec.tc;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.novatec.tc.asyncapi.AsyncApiRecord;
import de.novatec.tc.asyncapi.reader.ReaderStorage;
import de.novatec.tc.asyncapi.writer.WriterStorage;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toSet;

@Service
public class InMemoryStorage implements ReaderStorage, WriterStorage {
    Map<String, SortedMap<Integer, ObjectNode>> map;

    public InMemoryStorage(Map<String, SortedMap<Integer, ObjectNode>> map) {
        this.map = map;
    }

    public InMemoryStorage() {
        this(new ConcurrentHashMap<>());
    }

    @Override
    public synchronized void add(String id, ObjectNode value) {
        SortedMap<Integer, ObjectNode> versionMap = map.computeIfAbsent(id, (k) -> new TreeMap<>());

        int version = 0;
        if(!versionMap.isEmpty()) {
            version = versionMap.lastKey();
        }
        versionMap.put(++version, value);
    }

    @Override
    public Optional<AsyncApiRecord> getByIdAndVersion(String id, int version) {
        if(map.get(id) != null && map.get(id).get(version) != null) {
            return Optional.of(new AsyncApiRecord(id, version, map.get(id).get(version)));
        }
        return Optional.empty();
    }

    @Override
    public Optional<AsyncApiRecord> getLatestById(String id) {
        if(map.get(id) != null) {
            return Optional.of(new AsyncApiRecord(id, map.get(id).lastKey(), map.get(id).get(map.get(id).lastKey())));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Set<AsyncApiRecord>> getAllLatest() {
        if(!map.entrySet().isEmpty()) {
            return Optional.of(map.entrySet().stream()
                    .map(e -> new AsyncApiRecord(e.getKey(), e.getValue().lastKey(), e.getValue().get(e.getValue().lastKey())))
                    .collect(toSet()));
        }
        return Optional.empty();
    }
}

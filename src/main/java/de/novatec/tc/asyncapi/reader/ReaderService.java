package de.novatec.tc.asyncapi.reader;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.novatec.tc.asyncapi.AsyncApiRecord;
import de.novatec.tc.asyncapi.AsyncApiVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
public class ReaderService {
    private static final String INFO_FIELD = "info";
    private final ReaderStorage storage;

    @Autowired
    public ReaderService(ReaderStorage storage) {
        this.storage = storage;
    }

    public Optional<AsyncApiRecord> getLatestAsyncApiById(String artifactId) {
        return storage.getLatestById(artifactId);
    }

    public Optional<Set<AsyncApiRecord>> getAsyncApiSummary() {
        if(storage.getAllLatest().isPresent()) {
            return Optional.of(storage.getAllLatest().get().stream()
                    .map(this::createSummaryRecord)
                    .collect(toSet()));
        }
        return Optional.empty();
    }

    public Optional<AsyncApiRecord> getSpecificVersionOfAsyncApi(String artifactId, int version) {
        return storage.getByIdAndVersion(artifactId, version);
    }

    private AsyncApiRecord createSummaryRecord(AsyncApiRecord asyncApiRecord) {
        ObjectNode info = JsonNodeFactory.instance.objectNode();
        info.set(INFO_FIELD, asyncApiRecord.definition.get(INFO_FIELD));
        return new AsyncApiRecord(asyncApiRecord.artifactId, asyncApiRecord.version, info);
    }

    public Optional<AsyncApiVersion> getHighestVersionNumberOfAsyncApi(String artifactId) {
        Optional<AsyncApiRecord> record = storage.getLatestById(artifactId);
        return record.map(asyncApiRecord -> new AsyncApiVersion(artifactId, asyncApiRecord.version));
    }
}


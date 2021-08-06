package de.novatec.tc.asyncapi.reader;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.novatec.tc.AsyncApiRecord;
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

    public Set<AsyncApiRecord> getAsyncApiSummary() {
        return storage.getAllLatest().stream()
                .map(this::createSummaryRecord)
                .collect(toSet());
    }

    public Optional<AsyncApiRecord> getSpecificVersionOfAsyncApi(String artifactId, int version) {
        return storage.getByIdAndVersion(artifactId, version);
    }

    private AsyncApiRecord createSummaryRecord(AsyncApiRecord asyncApiRecord) {
        ObjectNode info = JsonNodeFactory.instance.objectNode();
        info.set(INFO_FIELD, asyncApiRecord.definition.get(INFO_FIELD));
        return new AsyncApiRecord(asyncApiRecord.artifactId, asyncApiRecord.version, info);
    }
}


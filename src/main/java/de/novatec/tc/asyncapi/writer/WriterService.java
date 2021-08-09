package de.novatec.tc.asyncapi.writer;

import com.fasterxml.jackson.databind.node.ObjectNode;
import de.novatec.tc.asyncapi.AsyncApiRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WriterService {
    private final WriterStorage storage;
    private Map<String, AsyncApiRecord> asyncApiSummaryCache = new ConcurrentHashMap<>();

    @Autowired
    public WriterService(WriterStorage storage) {
        this.storage = storage;
    }

    public void publishAsyncApiDefinition(String artifactId, ObjectNode value) {
        storage.add(artifactId, value);
    }
}

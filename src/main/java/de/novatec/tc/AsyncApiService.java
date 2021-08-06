package de.novatec.tc;

import de.novatec.tc.asyncapi.reader.ReaderStorage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/*
@Service
public class AsyncApiService {
    private static final String INFO_FIELD = "info";
    public static final String ARTIFACT_ID_FIELD = "artifactId";
    public static final String VERSION_FIELD = "version";
    public static final String ASYNCAPI_VALUE_FIELD = "value";
    private static final String PROPERTIES_TOPIC_NAME = "message.topic.name";

    private final ReaderStorage storage;

    private Map<String, Record> asyncApiSummaryCache = new ConcurrentHashMap<>();

    @Autowired
    public AsyncApiService(Writer storage) {
        this.storage = storage;
        this.storage.addOnChangeListener(this::addToSummary);
    }

    public Record getLatestAsyncApiById(String artifactId) {
        return storage.getLatestById(artifactId);
    }

    public Set<Record> getAsyncApiSummary() {
        return new HashSet<>(asyncApiSummaryCache.values());
    }

    private void addToSummary(Record record) {
        JSONObject info = new JSONObject();
        info.put(INFO_FIELD, record.value.getJSONObject(INFO_FIELD));
        asyncApiSummaryCache.put(record.id, new Record(record.id, record.version, info));
    }

    public void publishAsyncApiDefinition(String asyncApi) {
        JSONObject newAsyncApi = new JSONObject(asyncApi);
        String id = newAsyncApi.getString(ARTIFACT_ID_FIELD);
        storage.add(id, newAsyncApi);
    }

    public Record getSpecificVersionOfAsyncApi(String artifactId, int version) {
        return storage.getByIdAndVersion(artifactId, version);
    }

}
*/

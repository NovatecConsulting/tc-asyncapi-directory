package de.novatec.tc;

import de.novatec.tc.asyncapi.reader.ReaderService;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class AsyncApiServiceTest {
/*
    private static final String ARTIFACT_ID = "TestArtifact";
    private static final String ARTIFACT_ID_LABEL = "artifactId";
    private final Map<String, SortedMap<Integer, JSONObject>> map;
    private final Record testRecord1;
    private final Record testRecord2;
    private final Record testRecord3;

    public AsyncApiServiceTest() {
        map = new HashMap<>();
        SortedMap<Integer, JSONObject> sortedMap = new TreeMap<>();
        testRecord1 = new Record(ARTIFACT_ID, 0, new JSONObject().put(ARTIFACT_ID_LABEL, ARTIFACT_ID));
        testRecord2 = new Record(ARTIFACT_ID, 1, new JSONObject().put(ARTIFACT_ID_LABEL, ARTIFACT_ID));
        testRecord3 = new Record(ARTIFACT_ID, 2, new JSONObject().put(ARTIFACT_ID_LABEL, ARTIFACT_ID));

        sortedMap.put(0, testRecord1.value);
        sortedMap.put(1, testRecord2.value);
        sortedMap.put(2, testRecord3.value);
        map.put(ARTIFACT_ID, sortedMap);
    }

    @Test
    void shouldGetLatestAsyncApiById() {
        ReaderService asyncApiService = new ReaderService(new InMemoryStorage(map));
        Record returnedAsyncApi = asyncApiService.getLatestAsyncApiById(ARTIFACT_ID);

        Assertions.assertEquals(testRecord3.artifactId, returnedAsyncApi.artifactId);
        Assertions.assertEquals(testRecord3.version, returnedAsyncApi.version);
        Assertions.assertEquals(testRecord3.value, returnedAsyncApi.value);
    }

    @Test
    void shouldGetSpecificVersionOfAsyncApi() {
        ReaderService asyncApiService = new ReaderService(new InMemoryStorage(map));
        Record returnedAsyncApi = asyncApiService.getSpecificVersionOfAsyncApi(ARTIFACT_ID, 1);

        Assertions.assertEquals(testRecord2.artifactId, returnedAsyncApi.artifactId);
        Assertions.assertEquals(testRecord2.version, returnedAsyncApi.version);
        Assertions.assertEquals(testRecord2.value, returnedAsyncApi.value);
    }

    @Test
    void shouldNotFindSpecificVersionOfAsyncApi() {
        ReaderService asyncApiService = new ReaderService(new InMemoryStorage(map));
    }
    */
}

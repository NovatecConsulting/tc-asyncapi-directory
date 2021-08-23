package de.novatec.tc.asyncapi.reader;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.novatec.tc.asyncapi.AsyncApiRecord;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class KafkaReaderStorageTest {

    private static final String ARTIFACT_ID_LABEL = "artifactId";
    private static final String INFO_LABEL = "info";
    private static final String VERSION_LABEL = "version";
    private static final String INFO_CONTENT = "Testinfo";
    private static final String ARTIFACT_ID_1 = "TestArtifact1";
    private static final String ARTIFACT_ID_2 = "TestArtifact2";
    private static final String INSERT_ARTIFACT_ID = "TestArtifact3";
    private static final String WRONG_ARTIFACT_ID = "WrongArtifactId";
    private static final int VERSION_0 = 0;
    private static final int VERSION_1 = 1;
    private static final int VERSION_2 = 2;
    private static final int UPDATE_VERSION = 3;
    private static final int WRONG_VERSION = 100;
    private final Map<String, SortedMap<Integer, ObjectNode>> map;
    private final AsyncApiRecord testRecord0, testRecord1, testRecord2, testRecord3;

    public KafkaReaderStorageTest() {
        map = new HashMap<>();
        JsonNodeFactory factory = JsonNodeFactory.instance;

        SortedMap<Integer, ObjectNode> artifact1Set = new TreeMap<>();
        testRecord0 = new AsyncApiRecord(ARTIFACT_ID_1, VERSION_0, factory.objectNode().put(ARTIFACT_ID_LABEL, ARTIFACT_ID_1).put(INFO_LABEL, INFO_CONTENT));
        testRecord1 = new AsyncApiRecord(ARTIFACT_ID_1, VERSION_1, factory.objectNode().put(ARTIFACT_ID_LABEL, ARTIFACT_ID_1).put(INFO_LABEL, INFO_CONTENT));
        testRecord2 = new AsyncApiRecord(ARTIFACT_ID_1, VERSION_2, factory.objectNode().put(ARTIFACT_ID_LABEL, ARTIFACT_ID_1).put(INFO_LABEL, INFO_CONTENT));
        artifact1Set.put(VERSION_0, testRecord0.definition);
        artifact1Set.put(VERSION_1, testRecord1.definition);
        artifact1Set.put(VERSION_2, testRecord2.definition);

        SortedMap<Integer, ObjectNode> artifact2Set = new TreeMap<>();
        testRecord3 = new AsyncApiRecord(ARTIFACT_ID_2, VERSION_0, factory.objectNode().put(ARTIFACT_ID_LABEL, ARTIFACT_ID_2).put(INFO_LABEL, INFO_CONTENT));
        artifact2Set.put(VERSION_0, testRecord3.definition);

        map.put(ARTIFACT_ID_1, artifact1Set);
        map.put(ARTIFACT_ID_2, artifact2Set);
    }

    @Test
    void shouldGetLatestAsyncApiById() {
        KafkaReaderStorage storage = new KafkaReaderStorage(map);
        Optional<AsyncApiRecord> returnedAsyncApi = storage.getLatestById(ARTIFACT_ID_1);

        assertTrue(returnedAsyncApi.isPresent());
        assertEquals(testRecord2.artifactId, returnedAsyncApi.get().artifactId);
        assertEquals(testRecord2.version, returnedAsyncApi.get().version);
        assertEquals(testRecord2.definition, returnedAsyncApi.get().definition);
    }

    @Test
    void shouldNotGetWithWrongId() {
        KafkaReaderStorage storage = new KafkaReaderStorage(map);
        Optional<AsyncApiRecord> returnedAsyncApi = storage.getLatestById(WRONG_ARTIFACT_ID);

        assertTrue(returnedAsyncApi.isEmpty());
    }

    @Test
    void shouldGetAsyncApiByIdAndVersion() {
        KafkaReaderStorage storage = new KafkaReaderStorage(map);
        Optional<AsyncApiRecord> returnedAsyncApi = storage.getByIdAndVersion(ARTIFACT_ID_1, VERSION_1);

        assertTrue(returnedAsyncApi.isPresent());
        assertEquals(testRecord1.artifactId, returnedAsyncApi.get().artifactId);
        assertEquals(testRecord1.version, returnedAsyncApi.get().version);
        assertEquals(testRecord1.definition, returnedAsyncApi.get().definition);
    }

    @Test
    void shouldNotGetWithWrongIdAndVersion() {
        KafkaReaderStorage storage = new KafkaReaderStorage(map);
        Optional<AsyncApiRecord> returnedAsyncApi = storage.getByIdAndVersion(WRONG_ARTIFACT_ID, VERSION_1);

        assertTrue(returnedAsyncApi.isEmpty());
    }

    @Test
    void shouldNotGetWithIdAndWrongVersion() {
        KafkaReaderStorage storage = new KafkaReaderStorage(map);
        Optional<AsyncApiRecord> returnedAsyncApi = storage.getByIdAndVersion(ARTIFACT_ID_1, WRONG_VERSION);

        assertTrue(returnedAsyncApi.isEmpty());
    }

    @Test
    void shouldNotGetWithWrongIdAndWrongVersion() {
        KafkaReaderStorage storage = new KafkaReaderStorage(map);
        Optional<AsyncApiRecord> returnedAsyncApi = storage.getByIdAndVersion(WRONG_ARTIFACT_ID, WRONG_VERSION);

        assertTrue(returnedAsyncApi.isEmpty());
    }

    @Test
    void shouldGetAllLatestAsyncApis() {
        KafkaReaderStorage storage = new KafkaReaderStorage(map);
        Optional<Set<AsyncApiRecord>> allLatest = storage.getAllLatest();

        Set<AsyncApiRecord> expected = new HashSet<>();
        expected.add(new AsyncApiRecord(testRecord2.artifactId, testRecord2.version, testRecord2.definition));
        expected.add(new AsyncApiRecord(testRecord3.artifactId, testRecord3.version, testRecord3.definition));

        assertTrue(allLatest.isPresent());
        assertEquals(expected, allLatest.get());
    }

    @Test
    void shouldReturnEmtpyListForAllLatestAsyncApis() {
        KafkaReaderStorage storage = new KafkaReaderStorage(new HashMap<>());
        Optional<Set<AsyncApiRecord>> allLatest = storage.getAllLatest();

        assertTrue(allLatest.isEmpty());
    }

    @Test
    void shouldUpdateAsyncApiVersionInCache() {
        JsonNodeFactory factory = JsonNodeFactory.instance;
        ObjectNode value = factory.objectNode().put(INFO_LABEL, INFO_CONTENT);
        ObjectNode key = factory.objectNode().put(ARTIFACT_ID_LABEL, ARTIFACT_ID_1).put(VERSION_LABEL, UPDATE_VERSION);

        KafkaReaderStorage storage = new KafkaReaderStorage(map);
        storage.listener(value, key);

        int lastVersion = map.get(ARTIFACT_ID_1).lastKey();
        assertEquals(value, map.get(ARTIFACT_ID_1).get(lastVersion));
    }

    @Test
    void shouldInsertNewAsyncApiInCache() {
        JsonNodeFactory factory = JsonNodeFactory.instance;
        ObjectNode value = factory.objectNode().put(INFO_LABEL, INFO_CONTENT);
        ObjectNode key = factory.objectNode().put(ARTIFACT_ID_LABEL, INSERT_ARTIFACT_ID).put(VERSION_LABEL, VERSION_0);

        KafkaReaderStorage storage = new KafkaReaderStorage(map);
        storage.listener(value, key);

        assertNotNull(map.get(INSERT_ARTIFACT_ID));

        int lastVersion = map.get(INSERT_ARTIFACT_ID).lastKey();
        assertEquals(value, map.get(INSERT_ARTIFACT_ID).get(lastVersion));
    }
}

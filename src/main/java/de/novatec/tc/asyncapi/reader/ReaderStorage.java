package de.novatec.tc.asyncapi.reader;

import de.novatec.tc.asyncapi.AsyncApiRecord;

import java.util.Optional;
import java.util.Set;

public interface ReaderStorage {

    Optional<AsyncApiRecord> getByIdAndVersion(String id, int version);

    Optional<AsyncApiRecord> getLatestById(String id);

    Optional<Set<AsyncApiRecord>> getAllLatest();
}

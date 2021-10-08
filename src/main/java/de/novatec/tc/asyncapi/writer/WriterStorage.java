package de.novatec.tc.asyncapi.writer;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface WriterStorage {

    void add(String id, ObjectNode value);

}


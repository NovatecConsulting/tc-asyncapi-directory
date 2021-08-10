package de.novatec.tc.asyncapi;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Objects;

public class AsyncApiRecord {

    public String artifactId;
    public Integer version;
    public ObjectNode definition;

    public AsyncApiRecord() {
    }

    public AsyncApiRecord(String artifactId, Integer version, ObjectNode definition) {
        this.artifactId = artifactId;
        this.version = version;
        this.definition = definition;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public ObjectNode getDefinition() {
        return definition;
    }

    public void setDefinition(ObjectNode definition) {
        this.definition = definition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AsyncApiRecord that = (AsyncApiRecord) o;
        return Objects.equals(artifactId, that.artifactId) && Objects.equals(version, that.version) && Objects.equals(definition, that.definition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artifactId, version, definition);
    }
}

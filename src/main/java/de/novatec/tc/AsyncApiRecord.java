package de.novatec.tc;

import com.fasterxml.jackson.databind.node.ObjectNode;

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

}

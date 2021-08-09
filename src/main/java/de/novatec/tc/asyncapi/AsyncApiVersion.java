package de.novatec.tc.asyncapi;

public class AsyncApiVersion {

    private String artifactId;
    private int highestVersion;

    public AsyncApiVersion() {

    }

    public AsyncApiVersion(String artifactId, int highestVersion) {
        this.artifactId = artifactId;
        this.highestVersion = highestVersion;
    }

    public int getHighestVersion() {
        return highestVersion;
    }

    public void setHighestVersion(int highestVersion) {
        this.highestVersion = highestVersion;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }
}

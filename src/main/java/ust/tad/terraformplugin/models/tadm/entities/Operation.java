package ust.tad.terraformplugin.models.tadm.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Operation {

    private String name;

    private List<Artifact> artifacts = new ArrayList<>();


    public Operation() {
    }

    public Operation(String name, List<Artifact> artifacts) {
        this.name = name;
        this.artifacts = artifacts;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Artifact> getArtifacts() {
        return this.artifacts;
    }

    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    public Operation name(String name) {
        setName(name);
        return this;
    }

    public Operation artifacts(List<Artifact> artifacts) {
        setArtifacts(artifacts);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Operation)) {
            return false;
        }
        Operation operation = (Operation) o;
        return Objects.equals(name, operation.name) && Objects.equals(artifacts, operation.artifacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, artifacts);
    }

    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", artifacts='" + getArtifacts() + "'" +
            "}";
    }

    
}

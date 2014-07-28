package dk.diku.lindsgaard.communication;

import dk.diku.lindsgaard.utils.Action;

import java.util.Date;

/**
 * An artifact is sent to the system in order to
 */

public class Artifact {
    private String identifier; //Unique identifier for the artifact
    private String service; //URI of the originating service
    private String title;
    private String description;
    private String type; //The type of the artifact
    private Date created;
    private Date modified;

    public Artifact

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artifact)) return false;

        Artifact artifact = (Artifact) o;

        if (!identifier.equals(artifact.identifier)) return false;
        if (!service.equals(artifact.service)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = identifier.hashCode();
        result = 31 * result + service.hashCode();
        return result;
    }
}
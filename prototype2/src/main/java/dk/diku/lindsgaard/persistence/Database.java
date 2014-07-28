package dk.diku.lindsgaard.persistence;

import dk.diku.lindsgaard.communication.Artifact;
import dk.diku.lindsgaard.utils.Link;

import java.util.*;

/**
 * Created by rel on 7/17/14.
 */
public class Database {
    private static Database ourInstance = new Database();
    private static Map<String, Artifact> artifacts = new HashMap<String, Artifact>();
    private static Map<String, Link> links = new HashMap<String, Link>();

    public static Database getInstance() {
        return ourInstance;
    }

    private Database() {
    }

    public Artifact createOrUpdateArtifact(Artifact artifact) {
        String id = artifact.getIdentifier();
        Artifact oldArtifact = artifacts.get(id);
        if(oldArtifact != null) {
            artifact.setModified(new Date());
        }
        artifacts.put(id, artifact);
        return artifact;
    }

    public Artifact getArtifact(String identifier) {
        return artifacts.get(identifier);
    }

    public List<Artifact> getAllArtifacts() {
        return new ArrayList<Artifact>(artifacts.values());
    }

    public List<Link> getAllLinks() {
        return new ArrayList<Link>(links.values());
    }
}

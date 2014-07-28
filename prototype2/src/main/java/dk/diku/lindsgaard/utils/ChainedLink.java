package dk.diku.lindsgaard.utils;

import dk.diku.lindsgaard.communication.Artifact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rel on 7/17/14.
 */
public class ChainedLink extends Link {
    private List<Artifact> sources = new ArrayList<Artifact>();
    private List<Artifact> targets = new ArrayList<Artifact>();


    @Override
    public boolean isAtomic() {
        return false;
    }

    public void addSource(Artifact artifact) {
        modify("Adding source: " + artifact.toString());
        sources.add(artifact);
    }

    public void removeSource(Artifact artifact) {
        //if(sources.contains(artifact)) {
            modify("Removing source: " + artifact.toString());
            sources.remove(artifact);
        //}
    }

    public List<Artifact> getSources() {
        return sources;
    }

    public Artifact getSource() {
        if(!sources.isEmpty()) {
            return sources.get(0);
        }
        return null;
    }

    public void addTarget(Artifact artifact) {
        modify("Adding artifact: " + artifact.toString());
        targets.add(artifact);
    }

    public void removeTarget(Artifact artifact) {
        //if(targets.contains(artifact)) {
            modify("Removing target: " + artifact.toString());
            targets.remove(artifact);
        //}
    }

    public List<Artifact> getTargets() {
        return targets;
    }

    public Artifact getTarget() {
        if(!targets.isEmpty()) {
            return targets.get(0);
        }
        return null;
    }
}

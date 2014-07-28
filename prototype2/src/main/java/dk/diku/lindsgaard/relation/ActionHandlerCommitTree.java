package dk.diku.lindsgaard.relation;

import dk.diku.lindsgaard.communication.Artifact;
import dk.diku.lindsgaard.utils.Link;

import java.util.List;

/**
 * Created by rel on 7/28/14.
 */
public class ActionHandlerCommitTree extends ActionHandlerStrategy {

    @Override
    public List<Link> add(Artifact artifact, List<Artifact> artifacts) {
        return null;
    }

    @Override
    public List<Link> modify(Artifact artifact, List<Artifact> artifacts, List<Link> links) {
        return null;
    }
}

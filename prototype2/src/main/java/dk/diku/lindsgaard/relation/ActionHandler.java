package dk.diku.lindsgaard.relation;

import dk.diku.lindsgaard.communication.Artifact;
import dk.diku.lindsgaard.utils.Action;
import dk.diku.lindsgaard.utils.Link;

import java.util.List;
import java.util.Observer;

/**
 * Created by rel on 7/28/14.
 */
public interface ActionHandler extends Observer{
    public List<Artifact> filterArtifacts(Artifact artifact, List<Artifact> artifacts);
    public List<Link> filterLinks(Artifact artifact, List<Link> links);

    public List<Link> add(Artifact artifact, List<Artifact> artifacts);

    /**
     *
     * @param artifact the artifact being modified
     * @param artifacts output of "filterArtifacts" method
     * @param links all current links where the artifact is either source or target
     * @return an updated list of links
     */
    public List<Link> modify(Artifact artifact, List<Artifact> artifacts, List<Link> links);
    public List<Link> delete(Artifact artifact, List<Link> links);
}

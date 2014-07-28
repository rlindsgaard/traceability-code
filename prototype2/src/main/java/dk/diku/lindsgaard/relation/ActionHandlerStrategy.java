package dk.diku.lindsgaard.relation;

import dk.diku.lindsgaard.communication.Artifact;
import dk.diku.lindsgaard.persistence.Database;
import dk.diku.lindsgaard.utils.Action;
import dk.diku.lindsgaard.utils.Link;
import dk.diku.lindsgaard.utils.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by rel on 7/28/14.
 */
abstract public class ActionHandlerStrategy implements ActionHandler {

    @Override
    final public void update(Observable o, Object arg) {
        Tuple<Action, Artifact> t = (Tuple<Action, Artifact>) arg;
        Action action = t.getFirst();
        Artifact artifact = t.getSecond();

        Database db = Database.getInstance();
        List<Artifact> artifacts = filterArtifacts(artifact, db.getAllArtifacts());
        List<Link> links = filterLinks(artifact, db.getAllLinks());

        //Here we find out the action and do stuff or not
        switch(action) {
            case ADD:
                add(artifact, artifacts);
                break;
            case MODIFY:
                modify(artifact, artifacts, links);
                break;
            case DELETE:
                delete(artifact, links);
                break;
            default:
                //we should never be here...
                break;
        }
    }

    /**
     * Default artifact filter method. Takes all artifacts that are older that the one passed as argument
     * @param artifact the argument artifact to filter form
     * @param artifacts list of artifacts to perform filtering on
     * @return
     */
    @Override
    public List<Artifact> filterArtifacts(Artifact artifact, List<Artifact> artifacts) {
        List<Artifact> retval = new ArrayList<Artifact>();
        for(Artifact a : artifacts) {
            if(!(a.equals(artifact)) && a.getCreated().before(artifact.getCreated())) {
                retval.add(a);
            }
        }
        return retval;
    }

    /**
     * Default link filtering method. Filters all links where the artifact is either a source or target
     * @param artifact
     * @param links
     * @return
     */
    @Override
    public List<Link> filterLinks(Artifact artifact, List<Link> links) {
        List<Link> retval = new ArrayList<Link>();
        for(Link l : links) {
            if(l.getSource().equals(artifact) || l.getTarget().equals(artifact)){
                retval.add(l);
            }
        }
        return retval;
    }

    @Override
    public List<Link> delete(Artifact artifact, List<Link> links) {
        for(Link l : links) {
            l.retire();
        }
        return links;
    }
}

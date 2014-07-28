package dk.diku.lindsgaard.relation;

import dk.diku.lindsgaard.communication.Artifact;
import dk.diku.lindsgaard.persistence.Database;
import dk.diku.lindsgaard.utils.Action;
import dk.diku.lindsgaard.utils.Tuple;

import java.util.Observable;

public class ActionDispatcher extends Observable {

    private static ActionDispatcher ourInstance = new ActionDispatcher();

    public static ActionDispatcher getInstance() { return ourInstance; }

    private ActionDispatcher() {
    }

    public void artifactUpdate(Tuple<Action, Artifact> tuple) {

        //something happened, lets first get it to disk
        Database db = Database.getInstance();
        tuple.setSecond(db.createOrUpdateArtifact(tuple.getSecond()));

        notifyObservers(tuple);
    }
}
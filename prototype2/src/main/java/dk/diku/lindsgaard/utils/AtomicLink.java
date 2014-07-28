package dk.diku.lindsgaard.utils;

import dk.diku.lindsgaard.communication.Artifact;

/**
 * Created by rel on 7/17/14.
 */
public class AtomicLink extends Link {
    private Artifact source;
    private Artifact target;


    @Override
    public Artifact getSource() {
        return source;
    }

    @Override
    public Artifact getTarget() {
        return target;
    }
}
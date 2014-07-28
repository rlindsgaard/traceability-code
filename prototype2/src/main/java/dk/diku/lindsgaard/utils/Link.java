package dk.diku.lindsgaard.utils;

import dk.diku.lindsgaard.communication.Artifact;

import java.util.*;

/**
 * Created by rel on 7/17/14.
 */
abstract public class Link {
    private String type;
    private boolean retired = false;
    private Map<Date, String> modified = new HashMap<Date, String>();

    public boolean isAtomic() {
        return true;
    }

    abstract public Artifact getSource();
    abstract public Artifact getTarget();

    public void setType(String type) {
        modify("Setting type: " + type);
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public boolean isRetired() {
        return this.retired;
    }

    public void retire() {
        modify("retiring");
        this.retired = true;
    }

    protected void modify(String msg) {
        modified.put(new Date(), msg);

    }
}

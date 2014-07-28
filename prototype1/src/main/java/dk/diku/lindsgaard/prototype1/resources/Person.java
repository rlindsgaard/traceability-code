package dk.diku.lindsgaard.prototype1.resources;

import dk.diku.lindsgaard.prototype1.util.Constants;
import org.eclipse.lyo.oslc4j.core.annotation.*;
import org.eclipse.lyo.oslc4j.core.model.AbstractResource;

import java.net.URI;

/**
 * Created by rel on 5/17/14.
 * This is more or less a copy from the BugzillaChangeRequest example
 */

@OslcNamespace(Constants.FOAF_NAMESPACE)
@OslcResourceShape(title = "FOAF Person Resource Shape", describes = Constants.TYPE_PERSON)
public class Person extends AbstractResource{
    private URI uri = null;
    private String name = null;
    private String mbox = null;

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    @OslcDescription("A FOAF name ")
    @OslcPropertyDefinition(Constants.FOAF_NAMESPACE + "name")
    @OslcReadOnly
    @OslcTitle("Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @OslcDescription("A FOAF Email address ")
    @OslcPropertyDefinition(Constants.FOAF_NAMESPACE + "mbox")
    @OslcReadOnly
    @OslcTitle("Email Address")
    public String getMbox() {
        return mbox;
    }

    public void setMbox(String mbox) {
        this.mbox = mbox;
    }
}

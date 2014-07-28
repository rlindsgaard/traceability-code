package dk.diku.lindsgaard.prototype1.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import dk.diku.lindsgaard.prototype1.util.Constants;

import org.eclipse.lyo.oslc4j.core.annotation.*;
import org.eclipse.lyo.oslc4j.core.model.AbstractResource;
import org.eclipse.lyo.oslc4j.core.model.Occurs;
import org.eclipse.lyo.oslc4j.core.model.OslcConstants;
import org.eclipse.lyo.oslc4j.core.model.Representation;
import org.eclipse.lyo.oslc4j.core.model.ValueType;

@OslcNamespace(Constants.CHANGE_MANAGEMENT_NAMESPACE)
@OslcName(Constants.CHANGESET)
@OslcResourceShape(title = "Configuration Resource Shape", describes = Constants.TYPE_CONFIGURATION)
public class GitCommit extends AbstractResource {
	//TODO: Make abstract Configuration class

	private final Set<URI>      rdfTypes                    = new TreeSet<URI>();
	private List<Person>        creators                    = new ArrayList<Person>();

	private String              identifier;
	private URI                 serviceProvider;
	private String              title;
	
    public GitCommit()
            throws URISyntaxException
     {
         super();

         rdfTypes.add(new URI(Constants.TYPE_GIT_COMMIT));
     }

     public GitCommit(final URI about)
            throws URISyntaxException
     {
         super(about);

         rdfTypes.add(new URI(Constants.TYPE_GIT_COMMIT));
     }
     
     @OslcDescription("The author of the commit.")
     @OslcName("creator")
     @OslcPropertyDefinition(OslcConstants.DCTERMS_NAMESPACE + "creator")
     @OslcRepresentation(Representation.Inline)
     @OslcTitle("Creators")
     public List<Person> getCreators()
     {
         return creators;
     }
     
     @OslcDescription("The commit SHA-1 hash.")
     @OslcOccurs(Occurs.ExactlyOne)
     @OslcPropertyDefinition(OslcConstants.DCTERMS_NAMESPACE + "identifier")
     @OslcReadOnly
     @OslcTitle("Identifier")
     public String getIdentifier()
     {
         return identifier;
     }

     @OslcDescription("The scope of a resource is a URI for the resource's OSLC Service Provider.")
     @OslcPropertyDefinition(OslcConstants.OSLC_CORE_NAMESPACE + "serviceProvider")
     @OslcRange(OslcConstants.TYPE_SERVICE_PROVIDER)
     @OslcTitle("Service Provider")
     public URI getServiceProvider()
     {
         return serviceProvider;
     }
     
     @OslcDescription("FIXME: The first line of the commit message.")
     @OslcOccurs(Occurs.ExactlyOne)
     @OslcPropertyDefinition(OslcConstants.DCTERMS_NAMESPACE + "title")
     @OslcTitle("Title")
     @OslcValueType(ValueType.XMLLiteral)
     public String getTitle()
     {
         return title;
     }
     
     public void setCreators(final List<Person> creators)
     {
         this.creators.clear();

         if (creators != null)
         {
             this.creators.addAll(creators);
         }
     }
     
     public void setIdentifier(final String identifier)
     {
         this.identifier = identifier;
     }
     
     public void setServiceProvider(final URI serviceProvider)
     {
         this.serviceProvider = serviceProvider;
     }
     
     public void setTitle(final String title)
     {
         this.title = title;
     }
}

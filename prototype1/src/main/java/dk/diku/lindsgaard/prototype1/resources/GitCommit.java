package dk.diku.lindsgaard.prototype1.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import dk.diku.lindsgaard.prototype1.Constants;

import org.eclipse.lyo.oslc4j.core.annotation.OslcDescription;
import org.eclipse.lyo.oslc4j.core.annotation.OslcName;
import org.eclipse.lyo.oslc4j.core.annotation.OslcOccurs;
import org.eclipse.lyo.oslc4j.core.annotation.OslcPropertyDefinition;
import org.eclipse.lyo.oslc4j.core.annotation.OslcRange;
import org.eclipse.lyo.oslc4j.core.annotation.OslcReadOnly;
import org.eclipse.lyo.oslc4j.core.annotation.OslcRepresentation;
import org.eclipse.lyo.oslc4j.core.annotation.OslcTitle;
import org.eclipse.lyo.oslc4j.core.annotation.OslcValueType;
import org.eclipse.lyo.oslc4j.core.model.AbstractResource;
import org.eclipse.lyo.oslc4j.core.model.Occurs;
import org.eclipse.lyo.oslc4j.core.model.OslcConstants;
import org.eclipse.lyo.oslc4j.core.model.Representation;
import org.eclipse.lyo.oslc4j.core.model.ValueType;

public class GitCommit extends AbstractResource {
	
	private final Set<URI>      rdfTypes                    = new TreeSet<URI>();
	
	private List<String> creators;
	private String identifier;
	private URI      serviceProvider;
	private String title;
	
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
     
     @OslcDescription("Creator or creators of resource.")
     @OslcName("creator")
     @OslcPropertyDefinition(OslcConstants.DCTERMS_NAMESPACE + "creator")
     @OslcRepresentation(Representation.Inline)
     @OslcTitle("Creators")
     public List<String> getCreators()
     {
         return creators;
     }
     
     @OslcDescription("A unique identifier for a resource. Assigned by the service provider when a resource is created. Not intended for end-user display.")
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
     
     @OslcDescription("Title (reference: Dublin Core) or often a single line summary of the resource represented as rich text in XHTML content.")
     @OslcOccurs(Occurs.ExactlyOne)
     @OslcPropertyDefinition(OslcConstants.DCTERMS_NAMESPACE + "title")
     @OslcTitle("Title")
     @OslcValueType(ValueType.XMLLiteral)
     public String getTitle()
     {
         return title;
     }
     
     public void setCreators(final List<String> creators)
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

package dk.diku.lindsgaard.prototype1.servlet;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.eclipse.lyo.oslc4j.client.ServiceProviderRegistryURIs;
import org.eclipse.lyo.oslc4j.core.exception.OslcCoreApplicationException;
import org.eclipse.lyo.oslc4j.core.model.OslcConstants;
import org.eclipse.lyo.oslc4j.core.model.PrefixDefinition;
import org.eclipse.lyo.oslc4j.core.model.Publisher;
import org.eclipse.lyo.oslc4j.core.model.ServiceProvider;
import org.eclipse.lyo.oslc4j.core.model.ServiceProviderFactory;

import dk.diku.lindsgaard.prototype1.util.Constants;
import dk.diku.lindsgaard.prototype1.services.GitCommitService;
import dk.diku.lindsgaard.prototype1.services.GitRepositoryService;

public class GitServiceProviderFactory {

	
	private static Class<?>[] RESOURCE_CLASSES =
    {
        GitRepositoryService.class,
        GitCommitService.class
    };

	public static PrefixDefinition[] getPrefixDefinitions() throws URISyntaxException {
	    final PrefixDefinition[] prefixDefinitions =
	    {
	        new PrefixDefinition(OslcConstants.DCTERMS_NAMESPACE_PREFIX,             new URI(OslcConstants.DCTERMS_NAMESPACE)),
	        new PrefixDefinition(OslcConstants.OSLC_CORE_NAMESPACE_PREFIX,           new URI(OslcConstants.OSLC_CORE_NAMESPACE)),
	        new PrefixDefinition(OslcConstants.OSLC_DATA_NAMESPACE_PREFIX,           new URI(OslcConstants.OSLC_DATA_NAMESPACE)),
	        new PrefixDefinition(OslcConstants.RDF_NAMESPACE_PREFIX,                 new URI(OslcConstants.RDF_NAMESPACE)),
	        new PrefixDefinition(OslcConstants.RDFS_NAMESPACE_PREFIX,                new URI(OslcConstants.RDFS_NAMESPACE)),
	        new PrefixDefinition(Constants.CHANGE_MANAGEMENT_NAMESPACE_PREFIX,       new URI(Constants.CHANGE_MANAGEMENT_NAMESPACE)),
	        new PrefixDefinition(Constants.GIT_NAMESPACE_PREFIX,                new URI(Constants.GIT_NAMESPACE)),
	        new PrefixDefinition(Constants.FOAF_NAMESPACE_PREFIX,                    new URI(Constants.FOAF_NAMESPACE)),
	        new PrefixDefinition(Constants.QUALITY_MANAGEMENT_PREFIX,                new URI(Constants.QUALITY_MANAGEMENT_NAMESPACE)),
	        new PrefixDefinition(Constants.REQUIREMENTS_MANAGEMENT_PREFIX,           new URI(Constants.REQUIREMENTS_MANAGEMENT_NAMESPACE)),
	        new PrefixDefinition(Constants.SOFTWARE_CONFIGURATION_MANAGEMENT_PREFIX, new URI(Constants.SOFTWARE_CONFIGURATION_MANAGEMENT_NAMESPACE))
	    };
	    return prefixDefinitions;
	}

    private GitServiceProviderFactory()
    {
        super();
    }
    /*
    public static ServiceProvider createCommitServiceProvider(final String baseURI, final String commit, final Map<String, Object> parameterValueMap) {
    	
    }*/

    /**
     * Create a new Bugzilla OSLC change management service provider.
     * @param baseURI
     * @param product
     * @param parameterValueMap - a map containing the path replacement value for {productId}.  See ServiceProviderCatalogSingleton.initServiceProvidersFromProducts()
     * @return
     * @throws OslcCoreApplicationException
     * @throws URISyntaxException
     */
    public static ServiceProvider createServiceProvider(final String baseURI, final String repository, final Map<String,Object> parameterValueMap)
           throws OslcCoreApplicationException, URISyntaxException
    {
        final ServiceProvider serviceProvider = ServiceProviderFactory.createServiceProvider(baseURI,
                                                                                             ServiceProviderRegistryURIs.getUIURI(),
                                                                                             repository,
                                                                                             "Service provider for Git repository: "+repository,
                                                                                             new Publisher("Eclipse Lyo", "urn:oslc:ServiceProvider"),
                                                                                             RESOURCE_CLASSES,
                                                                                             parameterValueMap);
        URI detailsURIs[] = {new URI(baseURI + "/details")};
        serviceProvider.setDetails(detailsURIs);

        serviceProvider.setPrefixDefinitions(getPrefixDefinitions());

        return serviceProvider;
    }
}

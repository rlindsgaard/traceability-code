package dk.diku.lindsgaard.prototype1.servlet;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.eclipse.lyo.oslc4j.client.ServiceProviderRegistryURIs;
import org.eclipse.lyo.oslc4j.core.model.Publisher;
import org.eclipse.lyo.oslc4j.core.model.Service;
import org.eclipse.lyo.oslc4j.core.model.ServiceProvider;
import org.eclipse.lyo.oslc4j.core.model.ServiceProviderCatalog;


public class ServiceProviderCatalogSingleton {

	private static final ServiceProviderCatalog serviceProviderCatalog;
	private static final SortedMap<String, ServiceProvider> serviceProviders = new TreeMap<String, ServiceProvider>();
	
	static {
		try {
		serviceProviderCatalog = new ServiceProviderCatalog();

        serviceProviderCatalog.setAbout(new URI(ServiceProviderRegistryURIs.getServiceProviderRegistryURI()));
        serviceProviderCatalog.setTitle("OSLC Service Provider Catalog");
        serviceProviderCatalog.setDescription("OSLC Service Provider Catalog");
        serviceProviderCatalog.setPublisher(new Publisher("Project Lyo", "org.eclipse.lyo.oslc4j.bugzilla"));
        serviceProviderCatalog.getPublisher().setIcon(new URI("http://open-services.net/css/images/logo-forflip.png"));
		} catch (final URISyntaxException exception) {
            // We should never get here.
            throw new ExceptionInInitializerError(exception);
        }
	}
	

    public static URI getUri()
    {
    	return serviceProviderCatalog.getAbout();
    }

    public static ServiceProviderCatalog getServiceProviderCatalog(HttpServletRequest httpServletRequest) 
    {
    	initServiceProvidersFromRepositories(httpServletRequest);
        return serviceProviderCatalog;
    }

    public static ServiceProvider[] getServiceProviders(HttpServletRequest httpServletRequest) 
    {
        synchronized(serviceProviders)
        {
        	initServiceProvidersFromRepositories(httpServletRequest);
            return serviceProviders.values().toArray(new ServiceProvider[serviceProviders.size()]);
        }
    }

    public static ServiceProvider getServiceProvider(HttpServletRequest httpServletRequest, final String serviceProviderId) 
    {
        ServiceProvider serviceProvider;

        synchronized(serviceProviders)
        {     	
            serviceProvider = serviceProviders.get(serviceProviderId);
            
            //One retry refreshing the service providers
            if (serviceProvider == null)
            {
            	getServiceProviders(httpServletRequest);
            	serviceProvider = serviceProviders.get(serviceProviderId);
            }
        }

        if (serviceProvider != null)
        {
            return serviceProvider;
        }

        throw new WebApplicationException(Status.NOT_FOUND);
    }

    public static ServiceProvider registerServiceProvider(final HttpServletRequest httpServletRequest,
                                                          final ServiceProvider    serviceProvider,
                                                          final String productId) throws URISyntaxException
    {
        synchronized(serviceProviders)
        {
            final URI serviceProviderURI = new URI(httpServletRequest.getScheme(),
                                                   null,
                                                   httpServletRequest.getServerName(),
                                                   httpServletRequest.getServerPort(),
                                                   httpServletRequest.getContextPath() + "/repositories/" + productId,
                                                   null,
                                                   null);

            return registerServiceProviderNoSync(serviceProviderURI,
                                                 serviceProvider,
                                                 productId);
        }
    }


/**
 * Register a service provider with the OSLC catalog
 * 
 * @param serviceProviderURI
 * @param serviceProvider
 * @param productId
 * @return
 */
    private static ServiceProvider registerServiceProviderNoSync(final URI             serviceProviderURI,
                                                                 final ServiceProvider serviceProvider,
                                                                 final String productId)
    {
        final SortedSet<URI> serviceProviderDomains = getServiceProviderDomains(serviceProvider);

        serviceProvider.setAbout(serviceProviderURI);
        serviceProvider.setIdentifier(productId);
        serviceProvider.setCreated(new Date());

        serviceProviderCatalog.addServiceProvider(serviceProvider);
        serviceProviderCatalog.addDomains(serviceProviderDomains);

        serviceProviders.put(productId,
                             serviceProvider);


        return serviceProvider;
    }
    
    // This version is for self-registration and thus package-protected
    static ServiceProvider registerServiceProvider(final String          baseURI,
                                                   final ServiceProvider serviceProvider,
                                                   final String productId)
           throws URISyntaxException
    {
        synchronized(serviceProviders)
        {
            final URI serviceProviderURI = new URI(baseURI + "/repositories/" + productId);

            return registerServiceProviderNoSync(serviceProviderURI,
                                                 serviceProvider,
                                                 productId);
        }
    }

    public static void deregisterServiceProvider(final String serviceProviderId)
    {
        synchronized(serviceProviders)
        {
            final ServiceProvider deregisteredServiceProvider = serviceProviders.remove(serviceProviderId);

            if (deregisteredServiceProvider != null)
            {
                final SortedSet<URI> remainingDomains = new TreeSet<URI>();

                for (final ServiceProvider remainingServiceProvider : serviceProviders.values())
                {
                    remainingDomains.addAll(getServiceProviderDomains(remainingServiceProvider));
                }

                final SortedSet<URI> removedServiceProviderDomains = getServiceProviderDomains(deregisteredServiceProvider);

                removedServiceProviderDomains.removeAll(remainingDomains);
                serviceProviderCatalog.removeDomains(removedServiceProviderDomains);
                serviceProviderCatalog.removeServiceProvider(deregisteredServiceProvider);
            }
            else
            {
                throw new WebApplicationException(Status.NOT_FOUND);
            }
        }
    }

    private static SortedSet<URI> getServiceProviderDomains(final ServiceProvider serviceProvider)
    {
        final SortedSet<URI> domains = new TreeSet<URI>();

        if (serviceProvider!=null) {
    		final Service[] services = serviceProvider.getServices();
	        for (final Service service : services)
	        {
	            final URI domain = service.getDomain();

	            domains.add(domain);
	        }
        }
        return domains;
    }
    
    /**
     * Retrieve a list of products from Bugzilla and construct a service provider for each.
     * 
     * Each product ID is added to the parameter map which will be used during service provider
     * creation to create unique URI paths for each Bugzilla product.  See @Path definition at
     * the top of BugzillaChangeRequestService.
     * 
     * @param httpServletRequest
     */
    protected static void initServiceProvidersFromRepositories (HttpServletRequest httpServletRequest)   
    {
    	
		try {
			/*BugzillaConnector bc = BugzillaManager.getBugzillaConnector(httpServletRequest);

			GetAccessibleProducts getProductIds = new GetAccessibleProducts();
			if (bc != null)
			{
				bc.executeMethod(getProductIds);
				Integer[] productIds = getProductIds.getIds();

				String basePath = BugzillaManager.getBugzServiceBase();
	        
				for (Integer p : productIds) {
					String productId = Integer.toString(p);
		        	
					if (! serviceProviders.containsKey(productId)) {
		        		        
						GetProduct getProductMethod = new GetProduct(p);
						bc.executeMethod(getProductMethod);
						String product = getProductMethod.getProduct().getName();
	            	
	            	
						Map<String, Object> parameterMap = new HashMap<String, Object>();
						parameterMap.put("productId",productId);
						final ServiceProvider bugzillaServiceProvider = BugzillaServiceProviderFactory.createServiceProvider(basePath, product, parameterMap);
						registerServiceProvider(basePath,bugzillaServiceProvider,productId);
					}
				}
			} else {
				System.err.println("Bugzilla Connectorn not initialized - check bugz.properties");
			}*/
			String[] repositories = {"foo","bar","baz"};
			String basePath = "http://localhost:8001/services";
			
			//remember to setup some test here
			for(String repository : repositories) {
				
				//Setup some test verifying that the repository exists
				final Map<String,Object> parameterMap = new HashMap<String, Object>();
				parameterMap.put("repositoryId",repository);
				
				ServiceProvider serviceProvider = GitServiceProviderFactory.createServiceProvider(basePath,
	                    repository,
	                    parameterMap);
				registerServiceProvider(basePath,serviceProvider,repository);		
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(e,Status.INTERNAL_SERVER_ERROR);
		}
    }
}

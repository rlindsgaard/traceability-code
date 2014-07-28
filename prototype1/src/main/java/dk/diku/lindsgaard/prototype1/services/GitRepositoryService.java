package dk.diku.lindsgaard.prototype1.services;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.eclipse.lyo.oslc4j.core.annotation.OslcDialog;
import org.eclipse.lyo.oslc4j.core.annotation.OslcService;
import org.eclipse.lyo.oslc4j.core.model.Compact;
import org.eclipse.lyo.oslc4j.core.model.OslcConstants;
import org.eclipse.lyo.oslc4j.core.model.OslcMediaType;
import org.eclipse.lyo.oslc4j.core.model.Service;
import org.eclipse.lyo.oslc4j.core.model.ServiceProvider;

import dk.diku.lindsgaard.prototype1.servlet.ServiceProviderCatalogSingleton;

@OslcService(OslcConstants.OSLC_CORE_DOMAIN)
@Path("repositories")
public class GitRepositoryService {
	@Context private HttpServletRequest httpServletRequest;
	@Context private HttpServletResponse httpServletResponse;
	
	
	/**
	 * RDF/XML, XML and JSON representations of an OSLC Service Provider collection
	 * @return
	 */
	
    @OslcDialog
    (
         title = "Service Provider Selection Dialog",
         label = "Service Provider Selection Dialog",
         uri = "",
         hintWidth = "1000px",
         hintHeight = "600px",
         resourceTypes = {OslcConstants.TYPE_SERVICE_PROVIDER},
         usages = {OslcConstants.OSLC_USAGE_DEFAULT}
    )
    
    @GET
    @Produces({OslcMediaType.APPLICATION_RDF_XML, OslcMediaType.APPLICATION_XML, OslcMediaType.APPLICATION_JSON})
    public ServiceProvider[] getServiceProviders()
    {
    	httpServletResponse.addHeader("Oslc-Core-Version","2.0");
        return ServiceProviderCatalogSingleton.getServiceProviders(httpServletRequest);
    }

    /**
     * RDF/XML, XML and JSON representations of a single OSLC Service Provider
     * 
     * @param repositoryId
     * @return
     */
    @GET
    @Path("{repositoryId}")
    @Produces({OslcMediaType.APPLICATION_RDF_XML, OslcMediaType.APPLICATION_XML, OslcMediaType.APPLICATION_JSON})
    public ServiceProvider getServiceProvider(@PathParam("repositoryId") final String repositoryId)
    {
    	httpServletResponse.addHeader("Oslc-Core-Version","2.0");
        return ServiceProviderCatalogSingleton.getServiceProvider(httpServletRequest, repositoryId);
    }

    /**
     * OSLC compact XML representation of a single OSLC Service Provider
     * 
     * @param repositoryId
     * @return
     */
    @GET
    @Path("{repositoryId}")
    @Produces({OslcMediaType.APPLICATION_X_OSLC_COMPACT_XML, OslcMediaType.APPLICATION_X_OSLC_COMPACT_JSON})
    public Compact getCompact(@PathParam("repositoryId") final String repositoryId)
    {
        final ServiceProvider serviceProvider = ServiceProviderCatalogSingleton.getServiceProvider(httpServletRequest, repositoryId);

        if (serviceProvider != null) {
        	
        	final Compact compact = new Compact();

        	compact.setAbout(serviceProvider.getAbout());
        	compact.setShortTitle(serviceProvider.getTitle());
        	compact.setTitle(serviceProvider.getTitle());

        	// TODO - Need icon for ServiceProvider compact.

        	httpServletResponse.addHeader("Oslc-Core-Version","2.0");
        	return compact;
        }
        
        throw new WebApplicationException(Status.NOT_FOUND);
    }
    
    /**
     * HTML representation of a single OSLC Service Provider
     * 
     * Forwards to serviceprovider_html.jsp to create the html document
     * 
     * @param repositoryId
     */
    @GET
    @Path("{repositoryId}")
    @Produces(MediaType.TEXT_HTML)
    public void getHtmlServiceProvider(@PathParam("repositoryId") final String repositoryId)
    {
    	ServiceProvider serviceProvider = ServiceProviderCatalogSingleton.getServiceProvider(httpServletRequest, repositoryId);
    	
    	Service [] services = serviceProvider.getServices();
    	
    	if (services !=null && services.length > 0)
    	{
    		
	        httpServletRequest.setAttribute("gitUri", "http://www.git-scm.org/");
	        httpServletRequest.setAttribute("service", services[0]);
	        httpServletRequest.setAttribute("serviceProvider", serviceProvider);

	        RequestDispatcher rd = httpServletRequest.getRequestDispatcher("/serviceprovider_html.jsp");
    		try {
    			
				rd.forward(httpServletRequest, httpServletResponse);
			} catch (Exception e) {				
				e.printStackTrace();
				throw new WebApplicationException(e);
			} 
    	}
    }

}

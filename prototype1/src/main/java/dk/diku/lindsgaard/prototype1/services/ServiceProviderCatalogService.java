package dk.diku.lindsgaard.prototype1.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.eclipse.lyo.oslc4j.core.annotation.OslcDialog;
import org.eclipse.lyo.oslc4j.core.annotation.OslcService;
import org.eclipse.lyo.oslc4j.core.model.OslcConstants;
import org.eclipse.lyo.oslc4j.core.model.OslcMediaType;
import org.eclipse.lyo.oslc4j.core.model.ServiceProviderCatalog;

import dk.diku.lindsgaard.prototype1.servlet.ServiceProviderCatalogSingleton;

@OslcService(OslcConstants.OSLC_CORE_DOMAIN)
@Path("catalog")
public class ServiceProviderCatalogService extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Context private HttpServletRequest httpServletRequest;
	@Context private HttpServletResponse httpServletResponse;
	@Context private UriInfo uriInfo;
	
	@OslcDialog
    (
         title = "Service Provider Catalog Selection Dialog",
         label = "Service Provider Catalog Selection Dialog",
         uri = "/catalog",
         hintWidth = "1000px",
         hintHeight = "600px",
         resourceTypes = {OslcConstants.TYPE_SERVICE_PROVIDER_CATALOG},
         usages = {OslcConstants.OSLC_USAGE_DEFAULT}
    )
    /**
     * Redirect requests to /catalog to /catalog/singleton
     * 
     * By default, OSLC4J returns an OSLC query response for /catalog.  We really just
     * want the catalog itself which lives at /catalog/{serviceProviderCatalogId}
     * 
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    @GET
    public Response getServiceProviderCatalogs() throws IOException, URISyntaxException
    {
    	String forwardUri = uriInfo.getAbsolutePath() + "/singleton";
    	httpServletResponse.sendRedirect(forwardUri);
        return Response.seeOther(new URI(forwardUri)).build();
    }


    /**
     * Return the OSLC service provider catalog as RDF/XML, XML or JSON
     * 
     * @return
     * @throws IOException 
     */
    @GET
    @Path("{serviceProviderCatalogId}") // Required to distinguish from array result.  But, ignored.
    @Produces({OslcMediaType.APPLICATION_RDF_XML, OslcMediaType.APPLICATION_XML, OslcMediaType.APPLICATION_JSON})
    public ServiceProviderCatalog getServiceProviderCatalog()
    {

        ServiceProviderCatalog catalog =  ServiceProviderCatalogSingleton.getServiceProviderCatalog(httpServletRequest);
        
        if (catalog != null) {

        	httpServletResponse.addHeader("OSLC-Core-Version","2.0");
        	return catalog;
        }
    	
        
        throw new WebApplicationException(Status.NOT_FOUND);
    }
    
    @GET
    @Path("{serviceProviderCatalogId}")
    @Produces(MediaType.TEXT_HTML)
    public void getHtmlServiceProvider(@PathParam("repositoryId") final String repositoryId) {
    	
    	ServiceProviderCatalog catalog = ServiceProviderCatalogSingleton.getServiceProviderCatalog(httpServletRequest);
    	

    	if (catalog !=null )
    	{
	        httpServletRequest.setAttribute("gitUri", "http://www.git-scm.org/");
	        httpServletRequest.setAttribute("catalog",catalog);

	        RequestDispatcher rd = httpServletRequest.getRequestDispatcher("/serviceprovidercatalog_html.jsp");
    		try {
				rd.forward(httpServletRequest, httpServletResponse);
			} catch (Exception e) {				
				e.printStackTrace();
				throw new WebApplicationException(e);
			} 
    	}
    }
}

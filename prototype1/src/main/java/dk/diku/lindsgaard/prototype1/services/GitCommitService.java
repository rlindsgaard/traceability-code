package dk.diku.lindsgaard.prototype1.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.eclipse.lyo.oslc4j.core.annotation.OslcService;
import org.eclipse.lyo.oslc4j.core.model.OslcMediaType;
import org.eclipse.lyo.oslc4j.core.model.Property;
import org.eclipse.lyo.oslc4j.core.model.ServiceProvider;

import dk.diku.lindsgaard.prototype1.Constants;
import dk.diku.lindsgaard.prototype1.git.GitManager;
import dk.diku.lindsgaard.prototype1.resources.GitCommit;
import dk.diku.lindsgaard.prototype1.servlet.ServiceProviderCatalogSingleton;

@OslcService(Constants.CHANGE_MANAGEMENT_DOMAIN)
@Path("{repositoryId}/commits")
public class GitCommitService {

	@Context private HttpServletRequest httpServletRequest;
	@Context private HttpServletResponse httpServletResponse;
	@Context private UriInfo uriInfo;
	
	public GitCommitService(){
		super();
	}

	/*@GET
    @Produces({OslcMediaType.APPLICATION_RDF_XML, OslcMediaType.APPLICATION_XML, OslcMediaType.APPLICATION_JSON})
	public List<GitCommit> getCommits(@PathParam("repositoryId")	final String productId,
    		                          @QueryParam("oslc.where")       final String where,
    		                          @QueryParam("oslc.select")      final String select,
    		                          @QueryParam("oslc.prefix")      final String prefix,
                                      @QueryParam("page")             final String pageString,
                                      @QueryParam("oslc.orderBy")     final String orderBy,
                                      @QueryParam("oslc.searchTerms") final String searchTerms,
                                      @QueryParam("oslc.paging")      final String paging,
                                      @QueryParam("oslc.pageSize")    final String pageSize) throws IOException, ServletException 
	{
		
    }*/
	
	@GET
	@Produces({ MediaType.TEXT_HTML })
	public Response getHtmlCollection(@PathParam("repositoryId")         final String productId,
			                          @QueryParam("oslc.where")       final String where,
                                      @QueryParam("oslc.prefix")      final String prefix,
                                      @QueryParam("page")             final String pageString,
                                      @QueryParam("oslc.orderBy")     final String orderBy,
                                      @QueryParam("oslc.searchTerms") final String searchTerms) throws ServletException, IOException
	{
    int page=0;
        
    if (null != pageString) {
      page = Integer.parseInt(pageString);
    }
        
		int limit=20;
		
		final List<GitCommit> results = GitManager.getCommits("master");
    System.out.println(results.size());
		
    if (results != null) {
     	httpServletRequest.setAttribute("results", results);
      Object nextPageAttr = httpServletRequest.getAttribute(Constants.NEXT_PAGE);
      if (nextPageAttr != null) {
      	httpServletRequest.setAttribute("nextPageUri", 
      	uriInfo.getAbsolutePath().toString() + "?oslc.paging=true&amp;page=" + nextPageAttr);
      }
      
      ServiceProvider serviceProvider = ServiceProviderCatalogSingleton.getServiceProvider(httpServletRequest, productId);
      httpServletRequest.setAttribute("serviceProvider", serviceProvider);

      RequestDispatcher rd = httpServletRequest.getRequestDispatcher("/commit_collection_html.jsp");
      rd.forward(httpServletRequest,httpServletResponse);
    }
		
		throw new WebApplicationException(Status.SERVICE_UNAVAILABLE);
	}
}

package dk.diku.lindsgaard.prototype1.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
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

import org.eclipse.lyo.core.query.ParseException;
import org.eclipse.lyo.core.query.Properties;
import org.eclipse.lyo.core.query.QueryUtils;
import org.eclipse.lyo.oslc4j.core.OSLC4JConstants;
import org.eclipse.lyo.oslc4j.core.annotation.OslcNamespaceDefinition;
import org.eclipse.lyo.oslc4j.core.annotation.OslcSchema;
import org.eclipse.lyo.oslc4j.core.annotation.OslcService;
import org.eclipse.lyo.oslc4j.core.model.OslcMediaType;
import org.eclipse.lyo.oslc4j.core.model.ServiceProvider;

import dk.diku.lindsgaard.prototype1.util.Constants;
import dk.diku.lindsgaard.prototype1.util.GitManager;
import dk.diku.lindsgaard.prototype1.resources.GitCommit;
import dk.diku.lindsgaard.prototype1.servlet.ServiceProviderCatalogSingleton;

@OslcService(Constants.SOFTWARE_CONFIGURATION_DOMAIN)
@Path("{repositoryId}/commits")
public class GitCommitService {

	@Context private HttpServletRequest httpServletRequest;
	@Context private HttpServletResponse httpServletResponse;
	@Context private UriInfo uriInfo;
	
	public GitCommitService(){
		super();
	}

	@GET
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
        boolean isPaging = false;

        if (paging != null) {
            isPaging = Boolean.parseBoolean(paging);
        }

        int page=0;

        if (null != pageString) {
            page = Integer.parseInt(pageString);
        }

        int limit=10;

        if (isPaging && pageSize != null) {
            limit = Integer.parseInt(pageSize);
        }

        Map<String, String> prefixMap;

        try {
            prefixMap = QueryUtils.parsePrefixes(prefix);
        } catch (ParseException e) {
            throw new IOException(e);
        }

        //addDefaultPrefixes(prefixMap);

        Properties properties;

        if (select == null) {
            properties = QueryUtils.WILDCARD_PROPERTY_LIST;
        } else {
            try {
                properties = QueryUtils.parseSelect(select, prefixMap);
            } catch (ParseException e) {
                throw new IOException(e);
            }
        }

        Map<String, Object> propMap =
                QueryUtils.invertSelectedProperties(properties);
        final List<GitCommit> results = GitManager.getCommits("master");


        Object nextPageAttr = httpServletRequest.getAttribute(Constants.NEXT_PAGE);

        if (! isPaging && nextPageAttr != null) {

            String location =
                    uriInfo.getBaseUri().toString() + uriInfo.getPath() + '?' +
                            (where != null ? ("oslc.where=" + URLEncoder.encode(where, "UTF-8") + '&') : "") +
                            (select != null ? ("oslc.select=" + URLEncoder.encode(select, "UTF-8") + '&') : "") +
                            (prefix != null ? ("oslc.prefix=" + URLEncoder.encode(prefix, "UTF-8") + '&') : "") +
                            (orderBy != null ? ("oslc.orderBy=" + URLEncoder.encode(orderBy, "UTF-8") + '&') : "") +
                            (searchTerms != null ? ("oslc.searchTerms=" + URLEncoder.encode(searchTerms, "UTF-8") + '&') : "") +
                            "oslc.paging=true&oslc.pageSize=" + limit;

            try {
                throw new WebApplicationException(Response.temporaryRedirect(new URI(location)).build());
            } catch (URISyntaxException e) {
                // XXX - Can't happen
                throw new IllegalStateException(e);
            }
        }

        httpServletRequest.setAttribute(OSLC4JConstants.OSLC4J_SELECTED_PROPERTIES,
                propMap);

        if (nextPageAttr != null) {

            String location =
                    uriInfo.getBaseUri().toString() + uriInfo.getPath() + '?' +
                            (where != null ? ("oslc.where=" + URLEncoder.encode(where, "UTF-8") + '&') : "") +
                            (select != null ? ("oslc.select=" + URLEncoder.encode(select, "UTF-8") + '&') : "") +
                            (prefix != null ? ("oslc.prefix=" + URLEncoder.encode(prefix, "UTF-8") + '&') : "") +
                            (orderBy != null ? ("oslc.orderBy=" + URLEncoder.encode(orderBy, "UTF-8") + '&') : "") +
                            (searchTerms != null ? ("oslc.searchTerms=" + URLEncoder.encode(searchTerms, "UTF-8") + '&') : "") +
                            "oslc.paging=true&oslc.pageSize=" + limit + "&page=" + nextPageAttr;

            httpServletRequest.setAttribute(OSLC4JConstants.OSLC4J_NEXT_PAGE,
                    location);

        }

        return results;
    }

    private static void addDefaultPrefixes(final Map<String, String> prefixMap)
    {
        recursivelyCollectNamespaceMappings(prefixMap, GitCommit.class);
    }

    private static void recursivelyCollectNamespaceMappings(final Map<String, String>     prefixMap,
                                                            final Class<? extends Object> resourceClass)
    {
        final OslcSchema oslcSchemaAnnotation = resourceClass.getPackage().getAnnotation(OslcSchema.class);

        if (oslcSchemaAnnotation != null)
        {
            final OslcNamespaceDefinition[] oslcNamespaceDefinitionAnnotations = oslcSchemaAnnotation.value();

            for (final OslcNamespaceDefinition oslcNamespaceDefinitionAnnotation : oslcNamespaceDefinitionAnnotations)
            {
                final String prefix       = oslcNamespaceDefinitionAnnotation.prefix();
                final String namespaceURI = oslcNamespaceDefinitionAnnotation.namespaceURI();

                prefixMap.put(prefix, namespaceURI);
            }
        }

        final Class<?> superClass = resourceClass.getSuperclass();

        if (superClass != null)
        {
            recursivelyCollectNamespaceMappings(prefixMap,
                    superClass);
        }

        final Class<?>[] interfaces = resourceClass.getInterfaces();

        if (interfaces != null)
        {
            for (final Class<?> interfac : interfaces)
            {
                recursivelyCollectNamespaceMappings(prefixMap,
                        interfac);
            }
        }
    }

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

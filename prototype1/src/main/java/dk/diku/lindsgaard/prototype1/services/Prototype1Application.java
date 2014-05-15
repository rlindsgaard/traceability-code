package dk.diku.lindsgaard.prototype1.services;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.lyo.oslc4j.application.OslcWinkApplication;
import org.eclipse.lyo.oslc4j.core.exception.OslcCoreApplicationException;
import org.eclipse.lyo.oslc4j.core.model.OslcConstants;

public class Prototype1Application extends OslcWinkApplication {
	private static final Set<Class<?>>         RESOURCE_CLASSES                          = new HashSet<Class<?>>();
	private static final Map<String, Class<?>> RESOURCE_SHAPE_PATH_TO_RESOURCE_CLASS_MAP = new HashMap<String, Class<?>>();

	static {
		RESOURCE_CLASSES.add(ServiceProviderCatalogService.class);
		RESOURCE_CLASSES.add(GitRepositoryService.class);
		RESOURCE_CLASSES.add(GitCommitService.class);
	} 
	
	public Prototype1Application()
	           throws OslcCoreApplicationException,
	                  URISyntaxException {
	        super(RESOURCE_CLASSES,
	              OslcConstants.PATH_RESOURCE_SHAPES,
	              RESOURCE_SHAPE_PATH_TO_RESOURCE_CLASS_MAP);
	}
}

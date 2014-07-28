package dk.diku.lindsgaard.prototype1.util;

public interface Constants {

		public static String CHANGE_MANAGEMENT_DOMAIN                    = "http://open-services.net/ns/cm#";
	    public static String CHANGE_MANAGEMENT_NAMESPACE                 = "http://open-services.net/ns/cm#";
	    public static String CHANGE_MANAGEMENT_NAMESPACE_PREFIX          = "oslc_cm";
	    public static String FOAF_NAMESPACE                              = "http://xmlns.com/foaf/0.1/";
	    public static String FOAF_NAMESPACE_PREFIX                       = "foaf";
	    public static String QUALITY_MANAGEMENT_NAMESPACE                = "http://open-services.net/ns/qm#";
	    public static String QUALITY_MANAGEMENT_PREFIX                   = "oslc_qm";
	    public static String REQUIREMENTS_MANAGEMENT_NAMESPACE           = "http://open-services.net/ns/rm#";
	    public static String REQUIREMENTS_MANAGEMENT_PREFIX              = "oslc_rm";
        public static String SOFTWARE_CONFIGURATION_DOMAIN               = "http://open-services.net/ns/scm#";
	    public static String SOFTWARE_CONFIGURATION_MANAGEMENT_NAMESPACE = "http://open-services.net/ns/scm#";
	    public static String SOFTWARE_CONFIGURATION_MANAGEMENT_PREFIX    = "oslc_scm";
	    public static String GIT_DOMAIN							 = "http://www.git-scm.com/rdf#";
	    public static String GIT_NAMESPACE							 = "http://www.git-scm.com/rdf#";
	    public static String GIT_NAMESPACE_PREFIX					 = "git";

	    public static String GIT_COMMIT = "GitCommit";
	    public static String TYPE_GIT_COMMIT = CHANGE_MANAGEMENT_NAMESPACE + GIT_COMMIT;

        public static String CHANGE                     = "Change";
        public static String TYPE_CHANGE                = SOFTWARE_CONFIGURATION_MANAGEMENT_NAMESPACE + CHANGE;
        public static String CHANGESET                  = "ChangeSet";
        public static String TYPE_CHANGESET             = SOFTWARE_CONFIGURATION_MANAGEMENT_NAMESPACE + CHANGESET;
        public static String CONFIGURATION              = "Configuration";
        public static String TYPE_CONFIGURATION         = SOFTWARE_CONFIGURATION_MANAGEMENT_NAMESPACE + CONFIGURATION;
        public static String TYPE_PERSON                = FOAF_NAMESPACE + "Person";
	    /*
	    public static String CHANGE_REQUEST             = "ChangeRequest";
	    public static String TYPE_CHANGE_REQUEST        = CHANGE_MANAGEMENT_NAMESPACE + "ChangeRequest";
	    public static String TYPE_CHANGE_SET            = SOFTWARE_CONFIGURATION_MANAGEMENT_NAMESPACE + "ChangeSet";
	    public static String TYPE_DISCUSSION            = OslcConstants.OSLC_CORE_NAMESPACE + "Discussion";

	    public static String TYPE_REQUIREMENT           = REQUIREMENTS_MANAGEMENT_NAMESPACE + "Requirement";
	    public static String TYPE_TEST_CASE             = QUALITY_MANAGEMENT_NAMESPACE + "TestCase";
	    public static String TYPE_TEST_EXECUTION_RECORD = QUALITY_MANAGEMENT_NAMESPACE + "TestExecutionRecord";
	    public static String TYPE_TEST_PLAN             = QUALITY_MANAGEMENT_NAMESPACE + "TestPlan";
	    public static String TYPE_TEST_RESULT           = QUALITY_MANAGEMENT_NAMESPACE + "TestResult";
	    public static String TYPE_TEST_SCRIPT           = QUALITY_MANAGEMENT_NAMESPACE + "TestScript";
		*/

	    //public static String PATH_CHANGE_REQUEST = "changeRequest";

	    public static String USAGE_LIST = CHANGE_MANAGEMENT_NAMESPACE + "list";
	    
	    public static final String HDR_OSLC_VERSION = "OSLC-Core-Version";
	    public static final String OSLC_VERSION_V2 = "2.0";
	    
	    public static final String NEXT_PAGE = "dk.diku.lindsgaard.prototype1.git.NextPage";
}

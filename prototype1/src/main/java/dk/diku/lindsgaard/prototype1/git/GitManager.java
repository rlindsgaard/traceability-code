package dk.diku.lindsgaard.prototype1.git;

import java.util.ArrayList;
import java.util.List;

import dk.diku.lindsgaard.prototype1.resources.GitCommit;

abstract public class GitManager {
	
	static public List<GitCommit> getCommits(String branch)  {
		List<GitCommit> commits = new ArrayList<GitCommit>();
		
		List<String> creators = new ArrayList<String>();
		creators.add("tracy@diku.dk");
		
		try {
			GitCommit c1 = new GitCommit();
			c1.setCreators(creators);
			c1.setIdentifier("12345");
			
			commits.add(c1);
		} catch(Exception e) {
			System.out.println("Fuck!" + e);
		}
		
		return commits;
	}
}

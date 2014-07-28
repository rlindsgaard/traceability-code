package dk.diku.lindsgaard.prototype1.util;

import java.util.ArrayList;
import java.util.List;

import dk.diku.lindsgaard.prototype1.resources.GitCommit;
import dk.diku.lindsgaard.prototype1.resources.Person;

public class GitManager {
	
	static public List<GitCommit> getCommits(String branch)  {
		List<GitCommit> commits = new ArrayList<GitCommit>();
		
		List<Person> creators = new ArrayList<Person>();
        Person tracy = new Person();
        tracy.setName("Tracy");
        tracy.setMbox("tracy@tracy-tm.org");
		creators.add(tracy);
		
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

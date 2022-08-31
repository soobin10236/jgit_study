package connectGit;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;


public class GitService {
	
	public Git createRepository() throws IOException, GitAPIException {
		
		Git git = Git.cloneRepository()
					 .setURI("https://github.com/soobin10236/test_repo.git")
					 .setDirectory(new File("C:\\Users\\LSB\\Desktop\\test_repo")) 
					 .call();
			
		return git;
	}
	
	public void openGit() throws IOException, RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException, CheckoutConflictException, GitAPIException {
		
		Git.open(new File("C:\\Users\\LSB\\Desktop\\test_repo"))
		             .checkout().call();
		
	}
	
	public void createBranch(Git git) throws RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException, CheckoutConflictException, GitAPIException {
		git.checkout()
		   .setCreateBranch(true)
		   .setName("dev-tnqls7742")
		   .call();
	}
}







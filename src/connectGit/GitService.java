package connectGit;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;


public class GitService {
	
	public Repository createRepository() throws IOException, GitAPIException {
		
		Repository repo = new FileRepositoryBuilder().setGitDir(new File("C:\\Users\\LSB\\Desktop\\git_clone\\study_history/.git")).build();
			
		return repo;
	}
}







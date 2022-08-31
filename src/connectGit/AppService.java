package connectGit;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;


public class AppService {

	public static void main(String[] args) throws IOException, GitAPIException {
		
		GitService gitService = new GitService();
//		
		Repository repo = gitService.createRepository();
//		
		
//		Ref master = repo.findRef("master");
//		
//		System.out.println(master.getName().toString());
//		System.out.println(repo.getConfig().toString());
//		System.out.println(repo.getConfig().getString("user", null, "name"));
//		Repository repo;
		Git git = new Git(repo);
		CredentialsProvider cp = new UsernamePasswordCredentialsProvider("", "");
		Collection<Ref> remoteRefs = git.lsRemote()
		    .setCredentialsProvider(cp)
		    .setRemote("origin")
		    .setTags(true)
		    .setHeads(false)
		    .call();
		for (Ref ref : remoteRefs) {
		    System.out.println(ref.getName() + " -> " + ref.getObjectId().name());
		}
		System.out.println("finish");
	}

}

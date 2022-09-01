package connectGit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.json.JSONArray;
import org.json.JSONObject;


public class AppService {
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("started");
		
		GitService gitService = new GitService();
		
//		gitService.callGitService();
		
//		gitService.apiTestGet();
//		gitService.apiTestPost();
//		gitService.apiTestPut();
		
		JSONArray PRList = gitService.getPullRequestList();
		if(PRList.length() < 1) {
			System.out.println("풀리퀘스트 목록이 존재하지 않음");
		} else {
			for(int i = 0; i < PRList.length(); i++) {
				JSONObject jsonObj = (JSONObject) PRList.get(i);
				
				System.out.println("===========================================");
				System.out.println("repository : " + new JSONObject(new JSONObject(jsonObj.optString("head")).optString("repo")).optString("full_name"));
				System.out.println("title : " + jsonObj.optString("title"));
				System.out.println("number : " + jsonObj.optString("number"));
				System.out.println("===========================================");
				gitService.apiTestPut(jsonObj.optString("number"));
			}	
		}

		
			
		
		System.out.println("finished");
		
	}

}

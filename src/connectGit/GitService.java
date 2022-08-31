package connectGit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import common.util;


public class GitService {
	
	util util = new util();
	Properties prop = util.readProperties("config/git.properties");
	
	
	public static final String localPath = "C:\\Users\\LSB\\Desktop\\test_repo";
	public static final String username = "tnqls7742";
//	public static final String password = "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";
	String password = prop.getProperty("password");
	
	public void callGitService() throws IOException, GitAPIException {
		
		Git git = Git.open(new File(localPath));
		System.out.println(git.toString());
		
		AddCommand add = git.add();
		add.addFilepattern("first_file");
		add.call();
		System.out.println("add call");
		
		CommitCommand commit = git.commit();
		commit.setMessage("java commit");
		commit.call();
		System.out.println("commit call");
		
		PushCommand push = git.push();
		push.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));		
		push.call();
		System.out.println("push call");
	}

	
	public void apiTestGet() throws Exception 
	{
		
		System.out.println(password);
	    URL url = null;
	    String readLine = null;
	    StringBuilder buffer = null;
	    BufferedReader bufferedReader = null;
	    BufferedWriter bufferedWriter = null;
	    HttpURLConnection urlConnection = null;
	    	
	    int connTimeout = 5000;
	    int readTimeout = 3000;
			
	    String apiUrl = "https://jsonplaceholder.typicode.com/todos/1";	// 각자 상황에 맞는 IP & url 사용 		
			
	    try 
	    {
	        url = new URL(apiUrl);
	        urlConnection = (HttpURLConnection)url.openConnection();
	        urlConnection.setRequestMethod("GET");
	        urlConnection.setConnectTimeout(connTimeout);
	        urlConnection.setReadTimeout(readTimeout);
	        urlConnection.setRequestProperty("Accept", "application/json;");
				
	        buffer = new StringBuilder();
	        if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) 
	        {
	            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
	            while((readLine = bufferedReader.readLine()) != null) 
	    	    {
	                buffer.append(readLine).append("\n");
	            }
	        }
	        else 
	        {
	            buffer.append("code : ");
	            buffer.append(urlConnection.getResponseCode()).append("\n");
	            buffer.append("message : ");
	            buffer.append(urlConnection.getResponseMessage()).append("\n");
	        }
	    }
	    catch(Exception ex) 
	    {
	        ex.printStackTrace();
	    }
	    finally 
	    {
	        try 
	        {
	            if (bufferedWriter != null) { bufferedWriter.close(); }
	            if (bufferedReader != null) { bufferedReader.close(); }
	        }
	        catch(Exception ex) 
	        { 
	            ex.printStackTrace();
	        }
	    }
			
			
	        System.out.println("결과 : " + buffer.toString());
	    }
	
	
	
	
	
	
	
	
}







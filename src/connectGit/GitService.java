package connectGit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
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
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import common.util;
import jdk.nashorn.internal.parser.JSONParser;


public class GitService {
	
	private static final Gson gson = new Gson();
	util util = new util();
	Properties prop = util.readProperties("config/local.properties");
	
	
	public static final String localPath = "C:\\Users\\soobin\\Desktop\\test_repo";
	public static final String username = "tnqls7742";
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
			
//	    String apiUrl = "https://jsonplaceholder.typicode.com/todos/1";	// 각자 상황에 맞는 IP & url 사용 		
	    String apiUrl = "https://api.github.com/repos/soobin10236/test_repo/commits/16c3c95bb2848e6281ba54680c6e38283d785f90/pulls";	// 각자 상황에 맞는 IP & url 사용 		
			
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
	        System.out.println(gson.toJson(buffer.toString()));
//	        System.out.println(gson.fromJson(gson.toJson(buffer.toString()),String.class));
	        
//	        JSONObject jsonObj = new JSONObject(buffer.toString());
	        JSONArray jsonArr = new JSONArray(buffer.toString());
	        
	        for(int i = 0; i < jsonArr.length(); i++) {
	        	JSONObject jsonObj = (JSONObject) jsonArr.get(i);
	        	
	        	System.out.println("===========================================");
	 	        System.out.println("repository : " + new JSONObject(new JSONObject(jsonObj.optString("head")).optString("repo")).optString("full_name"));
	 	        System.out.println("title : " + jsonObj.optString("title"));
	 	        System.out.println("===========================================");
	        }
//	        JSONObject jsonObj = (JSONObject) jsonArr.get(0);
//	        
////	        System.out.println(jsonObj.get("head"));
////	        System.out.println(new JSONObject(jsonObj.optString("head")).get("repo"));
////	        System.out.println(new JSONObject(jsonObj.optString("head")).get("ref"));
//	        System.out.println("===========================================");
//	        System.out.println("repository : " + new JSONObject(new JSONObject(jsonObj.optString("head")).optString("repo")).optString("full_name"));
//	        System.out.println("title : " + jsonObj.optString("title"));
//	        System.out.println("===========================================");
	        
	    }
	public JSONArray getPullRequestList() throws Exception 
	{
		
		URL url = null;
		String readLine = null;
		StringBuilder buffer = null;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		HttpURLConnection urlConnection = null;
		
		int connTimeout = 5000;
		int readTimeout = 3000;
			
		String apiUrl = "https://api.github.com/repos/soobin10236/test_repo/pulls";	// 각자 상황에 맞는 IP & url 사용 		
		
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
		System.out.println(gson.toJson(buffer.toString()));
		JSONArray jsonArr = new JSONArray(buffer.toString());
		
		/*for(int i = 0; i < jsonArr.length(); i++) {
			JSONObject jsonObj = (JSONObject) jsonArr.get(i);
			
			System.out.println("===========================================");
			System.out.println("repository : " + new JSONObject(new JSONObject(jsonObj.optString("head")).optString("repo")).optString("full_name"));
			System.out.println("title : " + jsonObj.optString("title"));
			System.out.println("===========================================");
		}	*/
		
		return jsonArr;
	}
	
	public void apiTestPut(String PRNumber) throws Exception 
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
			
//	    String apiUrl = "https://jsonplaceholder.typicode.com/todos/1";	// 각자 상황에 맞는 IP & url 사용 		
	    String apiUrl = "https://api.github.com/repos/soobin10236/test_repo/pulls/" + PRNumber + "/merge";	// 각자 상황에 맞는 IP & url 사용 		
			
	    try 
	    {
	        url = new URL(apiUrl);
	        urlConnection = (HttpURLConnection)url.openConnection();
	        urlConnection.setRequestMethod("PUT");
	        urlConnection.setConnectTimeout(connTimeout);
	        urlConnection.setReadTimeout(readTimeout);
	        urlConnection.setRequestProperty("Accept", "application/json;");
			urlConnection.setRequestProperty("Authorization", "token " + password);
				
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
	            if (urlConnection != null) { urlConnection.disconnect(); }
	        }
	        catch(Exception ex) 
	        { 
	            ex.printStackTrace();
	        }
	    }
	    
	    System.out.println(buffer.toString());
	        
	}
	
	public void apiTestPost() throws Exception 
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
		
//	    String apiUrl = "https://jsonplaceholder.typicode.com/todos/1";	// 각자 상황에 맞는 IP & url 사용 		
		String apiUrl = "https://api.github.com/repos/soobin10236/test_repo/pulls";	// 각자 상황에 맞는 IP & url 사용 		
		
		try 
		{
			url = new URL(apiUrl);
			urlConnection = (HttpURLConnection)url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setConnectTimeout(connTimeout);
			urlConnection.setReadTimeout(readTimeout);
			urlConnection.setRequestProperty("Accept", "application/json;");
			urlConnection.setRequestProperty("Authorization", "token " + password);
			
			urlConnection.setDoOutput(true); //OutputStream을 사용해서 post body 데이터 전송
			
			String ParamData = "";
			ParamData =  "{ \"title\" : \"commit java!!!\", \"head\" : \"dev-tnqls7742\", \"base\" : \"master\" }";
//			ParamData = "title=commit java!!!&head=dev-tnqls7742&base=master";
			try (OutputStream os = urlConnection.getOutputStream()){
				byte request_data[] = ParamData.getBytes("utf-8");
				os.write(request_data);
				os.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			//http 요청 실시
			urlConnection.connect();
			System.out.println("http 요청 방식 : "+"POST BODY JSON");
			System.out.println("http 요청 타입 : "+"application/json");
			System.out.println("http 요청 주소 : "+apiUrl);
			System.out.println("http 요청 데이터 : "+ParamData);
			System.out.println("=============================");
			
			
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
		
		System.out.println(buffer.toString());
		
	}
	
	public void post(String requestURL, String jsonMessage) {
		try {			
			HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성			
			HttpPost httpPost = new HttpPost(requestURL); //POST 메소드 URL 새성 	

			

//			postRequest.get
//			postRequest.setHeader("Accept", "application/json");			
//			postRequest.setHeader("Connection", "keep-alive");			
//			postRequest.setHeader("Content-Type", "application/json");			
//			postRequest.addHeader("x-api-key", RestTestCommon.API_KEY); //KEY 입력 			
//			//postRequest.addHeader("Authorization", token); // token 이용시			
//			postRequest.setEntity(new StringEntity(jsonMessage)); //json 메시지 입력 			
//			HttpResponse response = client.execute(postRequest);			//Response 출력			
//			if (response.getStatusLine().getStatusCode() == 200) {				
//				ResponseHandler<String> handler = new BasicResponseHandler();				
//				String body = handler.handleResponse(response);				
//				System.out.println(body);			
//			} else {				
//				System.out.println("response is error : " + response.getStatusLine().getStatusCode());			
//			}		
		} catch (Exception e){			
			System.err.println(e.toString());		
		}	
	}
	
	
	
	
}







package actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.JavaBean;
import uc.sd.apis.DropBoxApi2;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.github.scribejava.apis.DropBoxApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.exceptions.OAuthException;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.model.Verifier;
import com.github.scribejava.core.oauth.OAuthService;
//import com.sun.javafx.collections.MappingChange.Map;

public class DropBoxAction extends ActionSupport implements SessionAware {
	private static final String API_APP_KEY = "viuyqyu57xm0i1d";
	private static final String API_APP_SECRET = "ejpp4hxxt6agqjf";
	private static final String REDIRECT_URL = "http://localhost:8080/DropMusic2/dropBox.action";
	//private static final String REDIRECT_URL = "https://eden.dei.uc.pt/~fmduarte/echo.php";
	
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String authUrl = null, code = null;

	private String outString = null;
	
	public String execute() throws RemoteException {
		System.out.println("DropBoxAction.execute: entered");
		if(code==null) {
			session.put("auth", getCodeOauth());
			System.out.println("DropBoxAction.execute: code==null - " + authUrl);
			return LOGIN;
		}
		else {
			System.out.println("DropBoxAction.execute: code!=null");
			Map<String, Object> authMap = (Map<String, Object>) session.get("auth");
			OAuthService service = (OAuthService) authMap.get("service");

			Verifier verifier = new Verifier(code);
			Token accessToken = service.getAccessToken(null, verifier);
			System.out.println("DropBoxAction.execute: Define API_USER_TOKEN: " + accessToken.getToken());

			authMap.put("accessToken", accessToken);
		}
		/*
			Verifier verifier = new Verifier(code);
			Token accessToken = service.getAccessToken(null, verifier);
		*/
		return SUCCESS;
	}
	
	public String listFiles() throws RemoteException {
		System.out.println("DropBoxAction.listFiles: entered");
		Map<String, Object> authMap = (Map<String, Object>) session.get("auth");
		OAuthService service = (OAuthService) authMap.get("service");
		Token accessToken = (Token) authMap.get("accessToken");
		
		OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.dropboxapi.com/2/files/list_folder", service);
		System.out.println("DropBoxAction.listFiles: Bearer " + accessToken.getToken());
		request.addHeader("authorization", "Bearer " + accessToken.getToken());
		request.addHeader("Content-Type",  "application/json");
		request.addPayload("{\n" + 
				"    \"path\": \"\",\n" + 
				"    \"recursive\": false,\n" + 
				"    \"include_media_info\": false,\n" + 
				"    \"include_deleted\": false,\n" + 
				"    \"include_has_explicit_shared_members\": false,\n" + 
				"    \"include_mounted_folders\": true\n" + 
				"}");
		
		Response response = request.send();
		
		int id = response.getCode();
		String body = response.getBody();
		
		String out = JavaBean.jsonParaMusica(body);
		session.put("outString", out);
		return SUCCESS;
	}
	
	private Map<String, Object> getCodeOauth() {
		Map<String, Object> authMap = new HashMap<String, Object>();
		OAuthService service = new ServiceBuilder()
				.provider(DropBoxApi2.class)
				.apiKey(API_APP_KEY)
				.apiSecret(API_APP_SECRET)
				.callback(REDIRECT_URL)
				.build();
		authUrl = service.getAuthorizationUrl(null);
		authMap.put("service", service);
		authMap.put("url", authUrl);
		return authMap;
	}	
	
	public JavaBean getJavaBean() {
		if(!session.containsKey("javaBean"))
			this.setJavaBean(new JavaBean());
		return (JavaBean) session.get("javaBean");
	}

	public void setJavaBean(JavaBean javaBean) {
		this.session.put("javaBean", javaBean);
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getAuthUrl() {
		return authUrl;
	}

	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}

	public String getOutString() {
		return outString;
	}

}

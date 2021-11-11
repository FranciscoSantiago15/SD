package actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

import model.JavaBean;

public class LoginAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String username = null, password = null;
	
	public String execute() throws RemoteException {
		if(this.username != null && !username.equals("")) {
			this.getJavaBean().setUsername(this.username);
			this.getJavaBean().setPassword(this.password);
			
			session.put("username", username);
			session.put("password", password);
			
			ArrayList<String> temp = this.getJavaBean().login(username, password);
			
			if(temp.get(0).equals("LOGIN ACEITE !")) {
				int editor = Integer.parseInt(temp.get(1));
				this.getJavaBean().setEditor(editor);
				//System.out.println(editor);
				session.put("editor", editor);
				return SUCCESS;
			} else {
				return LOGIN;
			}			
		}	
		return LOGIN;
		
	}
	
	public void setUsername(String username) {
		this.username = username; // will you sanitize this input? maybe use a prepared statement?
	}

	public void setPassword(String password) {
		this.password = password; // what about this input? 
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

}

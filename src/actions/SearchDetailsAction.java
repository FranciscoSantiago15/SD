package actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

import model.JavaBean;

public class SearchDetailsAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String nomeProcura = null;
	private String tipo = null;
	private String outString = null;
	
	public String execute() throws RemoteException {
		
		if(this.nomeProcura != null ) {
			this.getJavaBean().setNomeProcura(this.nomeProcura);
			this.getJavaBean().setTipo(this.tipo);
			
			if(tipo.equals("Album")) {
				String temp = this.getJavaBean().searchDetails(1, nomeProcura);
				this.getJavaBean().setOutString(this.outString);
				temp = temp.replace("[","");
				temp = temp.replace("]","");
				temp = temp.replace(","," | ");
				temp = temp.replace("\n", "<br>");
				session.put("outString", temp);
				
				return SUCCESS;
			} else if (tipo.equals("Artista")) {
				String temp = this.getJavaBean().searchDetails(2, nomeProcura);
				this.getJavaBean().setOutString(this.outString);
				temp = temp.replace("->",": <br>");
				temp = temp.replace("[","&bull;");
				temp = temp.replace(",]","");
				temp = temp.replace(",","<br>&bull;");
				temp = temp.replace("\n", "<br>");
				
				session.put("outString", temp);
				
				return SUCCESS;
			}	
		}	
		return LOGIN;
		
	}
	
	public void setNomeProcura(String nomeProcura) {
		this.nomeProcura = nomeProcura; 
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void setOutString(String outString) {
        this.outString = outString;
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

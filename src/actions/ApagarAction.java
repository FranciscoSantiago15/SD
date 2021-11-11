package actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

import model.JavaBean;

public class ApagarAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String nomeFich = null, tipo = null;
	
	public String execute() throws RemoteException {
		this.getJavaBean().setNomeFich(nomeFich);
		this.getJavaBean().setTipo(tipo);
		
		String temp = this.getJavaBean().apagar(tipo, nomeFich);
		
		if(temp.equals("Operacao executada !!")) {
			return SUCCESS;
		} else {
			return LOGIN;
		}
	}


	public void setNomeFich(String nomeFich) {
		this.nomeFich = nomeFich;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
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

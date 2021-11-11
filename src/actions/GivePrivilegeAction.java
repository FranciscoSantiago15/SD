package actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

import model.JavaBean;

public class GivePrivilegeAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String nomeParaEditor = null;
	
	public String execute() throws RemoteException {
		if(this.getJavaBean().getEditor() == 0) {
			return LOGIN;
		} else if (this.getJavaBean().getEditor() == 1) {
			this.getJavaBean().setNomeParaEditor(nomeParaEditor);
			
			String temp = this.getJavaBean().GivePrivilege(nomeParaEditor);
			
			if(temp.equals("Utilizador nao recebeu privilegio\n")) {
				return LOGIN;
			} else {
				return SUCCESS;
			}
		}
		return LOGIN;
	}
	

	
	public void setNomeParaEditor(String nomeParaEditor) {
		this.nomeParaEditor = nomeParaEditor;
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

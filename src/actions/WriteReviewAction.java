package actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

import model.JavaBean;

public class WriteReviewAction extends ActionSupport implements SessionAware{
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private int Album;
	private String critica = null;
	private double pontuacao = 0.0;
	
	public String execute() throws RemoteException {
		this.getJavaBean().setAlbum(Album);
		this.getJavaBean().setPontuacao(pontuacao);
		this.getJavaBean().setCritica(critica);
		
		String temp = this.getJavaBean().writeCritic(Album, pontuacao, critica);
		
		if(temp.equals("Critica Inserida !!")) {
			return SUCCESS;
		} else {
			return LOGIN;
		}	
	}	
	
	
	public void setAlbum(int Album) {
		this.Album = Album; 
	}
	
	public void setPontuacao(double pontuacao) {
		pontuacao = pontuacao / 100;
		this.pontuacao = pontuacao; 
	}
	
	public void setCritica(String critica) {
		this.critica = critica; 
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

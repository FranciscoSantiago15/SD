package actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

import model.JavaBean;

public class SearchSongAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String nomeMusica = null;
	private String tipo = null;
	private String outString = null;
	
	public String execute() throws RemoteException {
		
		if(this.nomeMusica != null ) {
			this.getJavaBean().setNomeMusica(this.nomeMusica);
			this.getJavaBean().setTipo(this.tipo);
			
			if(tipo.equals("Album")) {
				ArrayList<String> aux = this.getJavaBean().searchSong(nomeMusica);
				String temp= "";
				int aux2 = Integer.parseInt(aux.get(0));
				
				if(aux2 == 0) {
					temp = "Sem Musicas";
				} else {
					for(int i=1; i <= aux2; i++) {
						temp += "-> "+ aux.get(i) +"<br>";
					}	
				}
				this.getJavaBean().setOutString(this.outString);
				session.put("outString", temp);
				
				return SUCCESS;
			} else if (tipo.equals("Musica")) {	
				String temp = this.getJavaBean().searchSong2(nomeMusica).replace("\n","<br>");
				this.getJavaBean().setOutString(this.outString);
				session.put("outString", temp);
				
				return SUCCESS;
			}	
		}	
		return LOGIN;
		
	}
	
	public void setNomeMusica(String nomeMusica) {
		this.nomeMusica = nomeMusica; 
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

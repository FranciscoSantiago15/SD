package actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

import model.JavaBean;


public class CriarAlbumAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String nomeFich = null, listaMusicas = null, dataLancamento = null, generos = null, concertos = null;
	
	
	public String execute() throws RemoteException {
		this.getJavaBean().setNomeFich(nomeFich);
		this.getJavaBean().setListaMusicas(listaMusicas);
		this.getJavaBean().setDataLancamento(dataLancamento);
		this.getJavaBean().setGeneros(generos);
		this.getJavaBean().setConcertos(concertos);
		
		String info = "{ titulo = " + nomeFich + "! Musicas = [" + listaMusicas + "]! data = " + dataLancamento + "! generos = [" + generos+ "]! concertos = [" + concertos+"]}";
		 
		String temp = this.getJavaBean().criar("Album", info);
		
		if(temp.equals("Operacao executada !!")) {
			return SUCCESS;
		} else {
			return LOGIN;
		}
	}


	public void setNomeFich(String nomeFich) {
		this.nomeFich = nomeFich;
	}	
	
	public void setListaMusicas(String listaMusicas) {
		this.listaMusicas = listaMusicas;
	}

	public void setDataLancamento(String dataLancamento) {
		this.dataLancamento = dataLancamento;
	}
	
	public void setGeneros(String generos) {
		this.generos = generos;
	}

	public void setConcertos(String concertos) {
		this.concertos = concertos;
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

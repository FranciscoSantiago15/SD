package actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

import model.JavaBean;


public class CriarMusicaAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String nomeFich = null, listaArtistas = null, listaGrMusicais = null, listaAlbuns = null, historia = null;
	
	public String execute() throws RemoteException {
		this.getJavaBean().setNomeFich(nomeFich);
		this.getJavaBean().setListaArtistas(listaArtistas);
		this.getJavaBean().setListaGrMusicais(listaGrMusicais);
		this.getJavaBean().setHistoria(historia);
		
		String info = "{titulo = " + nomeFich + "! Artistas = [" + listaArtistas + "]! Gr_Musicais = " + listaGrMusicais + "! Albuns = [" + listaAlbuns + "]! historia = " + historia+"}";
		 
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

	public void setListaArtistas(String listaArtistas) {
		this.listaArtistas = listaArtistas;
	}

	public void setListaGrMusicais(String listaGrMusicais) {
		this.listaGrMusicais = listaGrMusicais;
	}
	
	public void setListaAlbuns(String listaAlbuns) {
		this.listaAlbuns = listaAlbuns;
	}

	public void setHistoria(String historia) {
		this.historia = historia;
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

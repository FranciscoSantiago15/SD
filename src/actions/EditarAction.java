package actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

import model.JavaBean;

public class EditarAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String nomeProcura = null, info = null, tipo = null;
	private String temp = null;
	
	
	public String execute() throws RemoteException {
		this.getJavaBean().setNomeFich(nomeProcura);
		this.getJavaBean().setTipo(tipo);
		this.getJavaBean().setInfo(info);
		
		//System.out.println(nomeProcura+" - "+tipo+" -> "+info);
		
		if(this.getJavaBean().getEditor() == 0) {
			return LOGIN;
		}
		
		else if(this.getJavaBean().getEditor() == 1) {
			if(tipo.equals("Album_Titulo")) {
				info = "{"+nomeProcura + " ! " +"titulo = " + info + "}";
				temp = this.getJavaBean().editar("Album", info);
				if(temp.equals("Operacao executada !!")) {
					return SUCCESS; 
				} else {
					return LOGIN;
				}
				
			} else if(tipo.equals("Album_data")) {
				info = "{"+nomeProcura + " ! " +"data = " + info + "}";
				temp = this.getJavaBean().editar("Album", info);
				if(temp.equals("Operacao executada !!")) {
					return SUCCESS; 
				} else {
					return LOGIN;
				}
				
			} else if(tipo.equals("Album_Generos")) {
				info = "{"+nomeProcura + " ! " +"Generos = " + info + "}";
				temp = this.getJavaBean().editar("Album", info);
				if(temp.equals("Operacao executada !!")) {
					return SUCCESS; 
				} else {
					return LOGIN;
				}
				
			} else if(tipo.equals("Album_Concertos")) {
				info = "{"+nomeProcura + " ! " +"Concertos = " + info + "}";
				temp = this.getJavaBean().editar("Album", info);
				if(temp.equals("Operacao executada !!")) {
					return SUCCESS; 
				} else {
					return LOGIN;
				}
				
			} else if(tipo.equals("Musica_Nome")) {
				info = "{"+nomeProcura + " ! " +"Nome = " + info + "}";
				temp = this.getJavaBean().editar("Musica", info);
				if(temp.equals("Operacao executada !!")) {
					return SUCCESS; 
				} else {
					return LOGIN;
				}
				
			} else if(tipo.equals("Muscia_Artistas")) {
				info = "{"+nomeProcura + " ! " +"Artistas = " + info + "}";
				temp = this.getJavaBean().editar("Musica", info);
				if(temp.equals("Operacao executada !!")) {
					return SUCCESS; 
				} else {
					return LOGIN;
				}
				
			} else if(tipo.equals("Musica_GrMusicais")) {
				info = "{"+nomeProcura + " ! " +"Gr_Musicais = " + info + "}";
				temp = this.getJavaBean().editar("Musica", info);
				if(temp.equals("Operacao executada !!")) {
					return SUCCESS; 
				} else {
					return LOGIN;
				}
				
			} else if(tipo.equals("Musica_Historia")) {
				info = "{"+nomeProcura + " ! " +"historia = " + info + "}";
				temp = this.getJavaBean().editar("Musica", info);
				if(temp.equals("Operacao executada !!")) {
					return SUCCESS; 
				} else {
					return LOGIN;
				}
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
	
	public void setInfo(String info) {
		this.info = info;
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

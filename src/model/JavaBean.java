package model;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import projsd.RMIInterface;

public class JavaBean {	
	private RMIInterface server;
	private String username; 
	private String password;
	private String tipo;
	private int editor;
	private String nomeMusica;
	private String nomeProcura;
	private int Album;
	private double pontuacao;
	private String critica;
	private String nomeParaEditor;
	private String outString;
	
	private String nomeFich;
	private String listaMusicas;
	private String dataLancamento;
	private String generos; 
	private String concertos;

	private String listaArtistas, listaGrMusicais , listaAlbuns, historia;
	
	private String info; 
	
	public JavaBean() {
		try {
			server = (RMIInterface) LocateRegistry.getRegistry(7000).lookup("DropMusic");
		}
		catch(NotBoundException|RemoteException e) {
			e.printStackTrace(); 
		}
	}
	
	public ArrayList<String> login(String username, String password) throws RemoteException {
		ArrayList<String> lista;
        lista = server.Login(username, password);
        return lista; 
    }
	
	public String registar(String username, String password) throws RemoteException {
        String aux = server.Registar(username, password);
        return aux; 
    }
	
	public ArrayList<String> searchSong(String nome) throws RemoteException {
		ArrayList<String> aux = server.SearchSong(nome);
        return aux; 
    }
	
	public String searchSong2(String nome) throws RemoteException {
        String aux = server.SearchSong2(nome);
        return aux; 
    }
	
	public String searchDetails(int tipo, String nome) throws RemoteException {
        String aux = server.SearchDetails(tipo,nome);
        return aux; 
    }
	
	public String writeCritic(int Album, double pontuacao, String critica) throws RemoteException {
        
		String auxAlbum = server.getAlbumName(Album);
		String aux = server.AlbumCritic(auxAlbum, pontuacao, critica);
        return aux; 
    }
	
	public String GivePrivilege(String nomeParaEditor)throws RemoteException {
		String aux = server.GivePrivilege(nomeParaEditor);
		return aux;
	}
	
	public ArrayList<String> getAllUsersNoPrivilege() throws RemoteException {
		ArrayList<String> aux = server.getAllUsersNoPrivilege();
		return aux;
	}

	public String criar(String tipo, String info) throws RemoteException {
		String aux = server.Gerir(tipo, 1, info);
		return aux;
	}
	
	public String editar(String classe, String info) throws RemoteException {
		String aux = server.Gerir(classe, 2, info);
		return aux;
	}
	
	public String apagar(String tipo, String nomeFich) throws RemoteException {
		String aux = server.Gerir(tipo, 3, "{titulo = "+nomeFich+"}");
		return aux;
	}
	
	
	public static String jsonParaMusica(String in) {
		StringBuilder out = new StringBuilder();
		
		JSONObject rj = (JSONObject) JSONValue.parse(in);
		JSONArray contents = (JSONArray) rj.get("entries");
		for (int i=0; i<contents.size(); i++) {
			JSONObject item = (JSONObject) contents.get(i);
			String path = (String) item.get("name");
			System.out.println("JavaBean.jsonParaMusica:  - " + path);
			out.append(path).append("\n");
			//System.out.println("DropBoxAction.listFiles: GOT HERE 4.5");
		}
		return out.toString();
	}
	
	/*--------------------------------------------------------------------------------------------------------*/
	public int getEditor() {
		return this.editor;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setEditor(int editor) {
		this.editor = editor;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setNomeMusica(String nomeMusica) {
		this.nomeMusica = nomeMusica;		
	}
	
	public void setNomeProcura(String nomeProcura) {
		this.nomeProcura = nomeProcura;		
	}
	
	public void setAlbum(int Album) {
		this.Album = Album;
	}
	
	public void setPontuacao(double pontuacao) {
		this.pontuacao = pontuacao;		
	}

	public void setCritica(String critica) {
		this.critica = critica;		
	}
	
	public void setNomeParaEditor(String nomeParaEditor) {
		this.nomeParaEditor = nomeParaEditor;		
	}
	
	public void setOutString(String outString) {
		this.outString = outString;		
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

	public void setInfo(String info) {
		this.info = info;
	}
}

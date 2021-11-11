package projsd;

import java.rmi.*;
import java.util.ArrayList;

public interface RMIInterface extends Remote {
	public ArrayList<String> Login(String username, String password) throws RemoteException;
	public String Registar(String username, String password) throws RemoteException;
	public String Gerir(String classe, int tipo, String info)  throws RemoteException;
	public ArrayList<String> SearchSong(String nome) throws RemoteException;
	public String SearchSong2(String opcao) throws RemoteException;
	public String SearchDetails(int tipo,String nome)throws RemoteException;
	public String AlbumCritic(String album, double pontuacao, String critica)throws RemoteException;
	public String GivePrivilege(String nome)throws RemoteException;
	public ArrayList<String> getAllUsersNoPrivilege() throws RemoteException;
	public ArrayList<String> getAlbuns() throws RemoteException;
 	public String getAlbumName(int num) throws RemoteException;
	
	public boolean isActive() throws RemoteException;
}

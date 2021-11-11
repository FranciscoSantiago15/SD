package projsd;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;
import java.net.MulticastSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.io.IOException;

public class RMIServer extends UnicastRemoteObject implements RMIInterface {

	private static final long serialVersionUID = 1L;
	
	private String MULTICAST_ADDRESS = "224.0.224.0";
    private int PORT = 4444;

	public RMIServer() throws RemoteException {
		super();
	}

	// =========================================================
	public static void main(String[] args) {
        initiate();
	}

	private String multicastCom(String data) {
		String message = "ERROR";
		MulticastSocket socketRcv = null;
		MulticastSocket socket = null;
		
		try {
			// Init Receive
			socketRcv = new MulticastSocket(PORT);
            InetAddress groupRcv = InetAddress.getByName(MULTICAST_ADDRESS);
            socketRcv.joinGroup(groupRcv);
			// Init Send
			socket = new MulticastSocket();
	        InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);

			// Send
	        byte[] buffer = data.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
            socket.send(packet);
            //System.out.println("SENT");

            // Receive
            while (true) {
                buffer = new byte[256];
                packet = new DatagramPacket(buffer, buffer.length);
                socketRcv.receive(packet);
                message = new String(packet.getData(), 0, packet.getLength());

                if (message.startsWith("Server")) {
                    //System.out.println(message);	
                	break;
                }
                else {
                	message = "ERROR";
                }
            }
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message = "ERROR: " + e; // TODO: DELETE --------------------------------------------
		} finally {
			socket.close();
			socketRcv.close();
		}
		
		return message;
	}
	
	
	 public synchronized static void initiate() {
	        try {
	            RMIServer server = new RMIServer();
	            LocateRegistry.createRegistry(7000).rebind("DropMusic", server);
	             
	            System.out.println("\n*** Servidor RMI a operar no porto 7000 ***");
	        } catch (RemoteException e) {
	            RMIInterface server = null;
	             
	            try {
	                server = (RMIInterface) Naming.lookup("rmi://localhost:7000/DropMusic");
	            } catch (RemoteException | NotBoundException | MalformedURLException ex) {}
	             
	            int counter = 0;
	             
	            while (true) {
	                try {
	                    if (server != null) {
	                        server.isActive();
	                        counter = 0;
	                    } else
	                        throw new RemoteException();
	                } catch (RemoteException ex) {
	                    System.out.println("!");
	                     
	                    counter++;
	                     
	                    if (counter >= 5) {
	                        System.out.println("\n!!! A ligar o servidor RMI secundario... !!!");
	                        initiate();
	                        return;
	                    }
	                     
	                    try {
	                        server = (RMIInterface) Naming.lookup("rmi://localhost:7000/DropMusic");
	                        System.out.println("\n*** Conexao ao servidor RMI reposta ***");
	                    } catch (RemoteException | NotBoundException | MalformedURLException exc) {}
	                }
	                 
	                try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException ex) {}
	            }
	        }
	    }
	     
	    @Override
	    public boolean isActive() throws RemoteException {
	        return true;
	    }
	
	/*+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-*/
	public CopyOnWriteArrayList<String[]> splittrim(String line) throws NullPointerException{
        CopyOnWriteArrayList<String[]> array = new CopyOnWriteArrayList<String[]>();       
        String[] linesplit;

        if (line != null)
            linesplit = line.split(";");
        else{
            return null;
        }  
        
        for (String i : linesplit) {            
            String[] elem = i.split(Pattern.quote("|"));
            
            if (elem.length == 2){
                elem[0] = elem[0].trim();
                elem[1] = elem[1].trim();
                array.add(elem);    
            }
        }
        /*for (String[] elem : array) {
             System.out.println(elem[0] + " -> " + elem[1]);
        }*/

        return array;
    } 
	
	/*+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-*/
	public String Registar(String username, String password)  throws RemoteException {
        String data = "description | request; type | register; username | "+username+"; password | "+password; 
        System.out.println("SENDING: " + data);
        data = multicastCom(data);
        if (data.split(":", 2)[0].startsWith("Server")) {
        	data = data.split(":", 2)[1];
        	System.out.println("RECEIVED: " + data);
        	if(data.equals("description | response; type | register;  ok | true")) {
        		data = "UTILIZADOR REGISTADO !";
        	} else {
        		if(data.equals("description | response; type | register;  ok | false; msg | Username already exists")) {
        			data = "UTILIZADOR JA EXISTE";
        		} else {
        			data = "ERRO NA INSERÇAO !";
        		}
        	}
        }
        return data;
    }
	
    public ArrayList<String> Login(String username, String password)  throws RemoteException {
        String data = "description | request; type | login; username | "+username+"; password | "+password; 
        System.out.println("SENDING: " + data);
        data = multicastCom(data); 
        ArrayList<String> resposta = new ArrayList<String>();
        
        if (data.split(":", 2)[0].startsWith("Server")) {
        	data = data.split(":", 2)[1];
        	System.out.println("RECEIVED: " + data);
        	if(data.equals("description | response; type | login; editor | 0; ok | true")) {
        		resposta.add("LOGIN ACEITE !");
        		resposta.add("0");
        	}else if(data.equals("description | response; type | login; editor | 1; ok | true")) {
        		resposta.add("LOGIN ACEITE !"); 
        		resposta.add("1");
        	}else {
        		resposta.add("LOGIN FAIL !");
        	}
        } else {
        	resposta.add("LOGIN FAIL !");
        }
        //System.out.println(resposta);
        return resposta;
    }
	
    public String Gerir(String classe, int tipo, String info)  throws RemoteException {
        String data = "";
		
        data = "description | request; type | manage; class | "+classe+"; type2 | "+tipo+"; info | "+info; 

		
		System.out.println("SENDING: " + data);
     	data = multicastCom(data); 
	     
     	if (data.split(":", 2)[0].startsWith("Server")) {
     		data = data.split(":", 2)[1];
	     	System.out.println("RECEIVED: " + data);
	     	if(data.equals("description | request; type | manage; ok | true")) {
	     		data = "Operacao executada !!"; 
	     	} else {
	     		data = "Erro: Operacao nao executada !!";
	     	}
     	}		
        return data;
    }
	
    public ArrayList<String> SearchSong(String nome) throws RemoteException {
		String data = "description | request; type | search_song; name | "+nome;
		System.out.println("SENDING: " + data);
     	data = multicastCom(data); 
     	ArrayList<String> resposta = new ArrayList<String>();
	     
     	if (data.split(":", 2)[0].startsWith("Server")) {
     		data = data.split(":", 2)[1];
	     	System.out.println("RECEIVED: " + data);
	     	CopyOnWriteArrayList<String[]> arrayP = splittrim(data);
	     	
	     	synchronized (arrayP) {
	            for (String[] i : arrayP) {
	                if (i[0].equals("description")) {
	                    arrayP.remove(i);
	                }
	            }
	        }
	     	synchronized (arrayP) {
	            for (String[] i : arrayP) {
	                if (i[0].equals("type")) {
	                    arrayP.remove(i);
	                }
	            }
	        }
	     	int aux = Integer.parseInt(arrayP.get(0)[1]);
	     	resposta.add(arrayP.get(0)[1]);
	     	for(int i=1; i<=aux;i++) {
	     		resposta.add(arrayP.get(i)[1]);
	     	}
	     }
	     return resposta;
    }
    
    public String SearchSong2(String opcao) throws RemoteException {
    	String data = "description | request; type | search_song2; name | "+opcao;
		System.out.println("SENDING: " + data);
     	data = multicastCom(data); 
	     
     	if (data.split(":", 2)[0].startsWith("Server")) {
     		data = data.split(":", 2)[1];
	     	System.out.println("RECEIVED: " + data);
	     	
	     	CopyOnWriteArrayList<String[]> arrayP = splittrim(data);
	     	
	     	synchronized (arrayP) {
	            for (String[] i : arrayP) {
	                if (i[0].equals("description")) {
	                    arrayP.remove(i);
	                }
	            }
	        }
	     	synchronized (arrayP) {
	            for (String[] i : arrayP) {
	                if (i[0].equals("type")) {
	                    arrayP.remove(i);
	                }
	            }
	        }	 
	     	synchronized (arrayP) {
	            for (String[] i : arrayP) {
	                if (i[0].equals("name")) {
	                    arrayP.remove(i);
	                }
	            }
	        }
	     	
	     	
	     	String[] listaArtistas = arrayP.get(0)[1].split(",");
	     	String[] listaGrupos = arrayP.get(1)[1].split(",");
	     	//String[] listaAlbuns = arrayP.get(2)[1].split(",");
	     	
	     	
	     	for(int i=0; i<listaArtistas.length; i++) {
	     		listaArtistas[i] = listaArtistas[i].replace("[","");
	     		listaArtistas[i] = listaArtistas[i].replace("]","");	     		
	     	}
	     	for(int i=0; i<listaGrupos.length; i++) {
	     		listaGrupos[i] = listaGrupos[i].replace("[","");
	     		listaGrupos[i] = listaGrupos[i].replace("]","");
	     	}
	     	/*for(int i=0; i<listaAlbuns.length; i++) {
	     		listaAlbuns[i] = listaAlbuns[i].replace("[","");
	     		listaAlbuns[i] = listaAlbuns[i].replace("]","");	     		
	     	}*/	 
	   
	     	
	     	data = "NOME: "+opcao+"\nARTISTAS:\n";
	     	for(int i=0; i<listaArtistas.length; i++) {
	     		data += "-> "+listaArtistas[i]+"\n";
	     	}
	     	data += "GRUPOS MUSICAIS:\n";
 			for(int i=0; i<listaGrupos.length; i++) {
	     		data += "-> "+listaGrupos[i]+"\n";	     		
	     	}
	     	data += "ALBUNS:\n";
	     	/*for(int i=0; i<listaAlbuns.length; i++) {
	     		data += "-> "+listaAlbuns[i]+"\n"; 		
	     	}*/

	     	
	     	data += "HISTORIA: sem historia";//+arrayP.get(3)[1]+"\n";
     	}     	
		return data;
    }
    	
    public String SearchDetails(int tipo, String nome)throws RemoteException {
        String data = "description | request; type | search_details; tipo | "+tipo+";name | "+nome;
        System.out.println("SENDING: " + data);
        data = multicastCom(data); 
        
        String resposta = "VAZIO";
        
        data = data.split(":", 2)[1];
     	System.out.println("RECEIVED: " + data);
        
        if(tipo == 1) {
	        String n = "NOME: ", musicas = "Musicas ", criticas = "Criticas ", pontuacao = "Pontuacao = ", dataLancameto = "Lancamento ", concertos = "Concertos ";
	        	
        	CopyOnWriteArrayList<String[]> arrayP = splittrim(data);
	     	
	     	synchronized (arrayP) {
	            for (String[] i : arrayP) {
	                if (i[0].equals("description")) {
	                    arrayP.remove(i);
	                }
	            }
	        }
	     	synchronized (arrayP) {
	            for (String[] i : arrayP) {
	                if (i[0].equals("type")) {
	                    arrayP.remove(i);
	                }
	            }
	        }	 
	     	synchronized (arrayP) {
	            for (String[] i : arrayP) {
	                if (i[0].equals("name")) {
	                	n += i[1]+"\n"; 
	                    arrayP.remove(i);
	                }
	            }
	        }
	     	synchronized (arrayP) {
	            for (String[] i : arrayP) {
	                if (i[0].equals("lista_musicas")) {
	                	musicas += "-> "+i[1]+"\n"; 
	                    arrayP.remove(i);
	                }
	            }
	        }
	     	synchronized (arrayP) {
	            for (String[] i : arrayP) {
	                if (i[0].equals("criticas")) {
	                	criticas += "-> "+i[1]+"\n"; 
	                    arrayP.remove(i);
	                }
	            }
	        }
	     	synchronized (arrayP) {
	            for (String[] i : arrayP) {
	                if (i[0].equals("data_lancamento")) {
	                	dataLancameto += "-> "+i[1]+"\n"; 
	                    arrayP.remove(i);
	                }
	            }
	        }
	     	synchronized (arrayP) {
	            for (String[] i : arrayP) {
	                if (i[0].equals("pontuacao")) {
	                	pontuacao += i[1]+"\n"; 
	                    arrayP.remove(i);
	                }
	            }
	        }
	     	synchronized (arrayP) {
	            for (String[] i : arrayP) {
	                if (i[0].equals("concertos")) {
	                	concertos += i[1]+"\n"; 
	                    arrayP.remove(i);
	                }
	            }
	        }
	        
	        resposta = n+musicas+dataLancameto+concertos+pontuacao+criticas;

        } else if(tipo == 2) {
        	CopyOnWriteArrayList<String[]> arrayP = splittrim(data);
        	
	        String n = "NOME: ", musicas = "Musicas ";
	        
	        synchronized (arrayP) {
	            for (String[] i : arrayP) {
	                if (i[0].equals("description")) {
	                    arrayP.remove(i);
	                }
	            }
	        }
	     	synchronized (arrayP) {
	            for (String[] i : arrayP) {
	                if (i[0].equals("type")) {
	                    arrayP.remove(i);
	                }
	            }
	        }	 
	     	synchronized (arrayP) {
	            for (String[] i : arrayP) {
	                if (i[0].equals("name")) {
	                	n += i[1]+"\n"; 
	                    arrayP.remove(i);
	                }
	            }
	        }
	     	synchronized (arrayP) {
	            for (String[] i : arrayP) {
	                if (i[0].equals("lista_musicas")) {
	                	musicas += "-> "+i[1]+"\n"; 
	                    arrayP.remove(i);
	                }
	            }
	        }
	     	resposta = n+musicas;
        }
        return resposta;        
    }
    
    public String AlbumCritic(String album, double pontuacao, String critica)throws RemoteException {    	
    	String data = "description | request; type | album_critic; album | "+album+"; points | "+pontuacao+"; critic | "+critica;
        System.out.println("SENDING: " + data);
        data = multicastCom(data); 
        
        if (data.split(":", 2)[0].startsWith("Server")) {
        	data = data.split(":", 2)[1];
        	System.out.println("RECEIVED: " + data);
        	if(data.equals("description | response; type | album_critic;  ok | true")) {
        		data = "Critica Inserida !!";
        	} else {
        		data = "Critica nao inserida (erro)";
        	}
        }
        return data;        
    }
    
    public String GivePrivilege(String nome)throws RemoteException {
        String data = "description | request; type | give_privilege; name | "+nome;
        
        System.out.println("SENDING: " + data);
        data = multicastCom(data);
        if (data.split(":", 2)[0].startsWith("Server")) {
        	data = data.split(":", 2)[1];
        	System.out.println("RECEIVED: " + data);
        	if(data.equals("description | request; type | give_privilege;  ok | true")) {
        		data = "Utilizador «"+nome+"» tornou-se editor\n";
        	} else {
        		data = "Utilizador nao recebeu privilegio\n";
        	}
        	
        }
        return data;        
    }	
    
    public ArrayList<String> getAllUsersNoPrivilege() throws RemoteException {
        String data = "description | request; type | allUsers";

        ArrayList<String> resposta = new ArrayList<String>();
        
        System.out.println("SENDING: " + data);
        data = multicastCom(data);
        
        if (data.split(":", 2)[0].startsWith("Server")) {
        	data = data.split(":", 2)[1];
        	System.out.println("RECEIVED: " + data);
        	
        	CopyOnWriteArrayList<String[]> arrayP = splittrim(data);
        	
        	synchronized (arrayP) {
	            for (String[] i : arrayP) {
	                if (i[0].equals("description")) {
	                    arrayP.remove(i);
	                }
	            }
	        }
	     	synchronized (arrayP) {
	            for (String[] i : arrayP) {
	                if (i[0].equals("type")) {
	                    arrayP.remove(i);
	                }
	            }
	        }
	     	arrayP.get(0)[1] = arrayP.get(0)[1].replace("[", "");
	     	arrayP.get(0)[1] = arrayP.get(0)[1].replace("]", "");	     	
	     	String[] temp = arrayP.get(0)[1].split(",");
	     	for(String i : temp) {
	     		resposta.add(i);
	     	}
        }
    	return resposta;    	
    }
    
    public ArrayList<String> getAlbuns() throws RemoteException {
    	 String data = "description | request; type | getAlbuns";
    	 
    	 ArrayList<String> resposta = new ArrayList<String>();
         
         System.out.println("SENDING: " + data);
         data = multicastCom(data);
         
         if (data.split(":", 2)[0].startsWith("Server")) {
         	data = data.split(":", 2)[1];
         	System.out.println("RECEIVED: " + data);
         	
         	CopyOnWriteArrayList<String[]> arrayP = splittrim(data);
         	
        	synchronized (arrayP) {
	            for (String[] i : arrayP) {
	                if (i[0].equals("description")) {
	                    arrayP.remove(i);
	                }
	            }
	        }
	     	synchronized (arrayP) {
	            for (String[] i : arrayP) {
	                if (i[0].equals("type")) {
	                    arrayP.remove(i);
	                }
	            }
	        }
	     	arrayP.get(0)[1] = arrayP.get(0)[1].replace("[", "");
	     	arrayP.get(0)[1] = arrayP.get(0)[1].replace("]", "");	     	
	     	String[] temp = arrayP.get(0)[1].split(",");
	     	for(String i : temp) {
	     		resposta.add(i);
	     	}
         }
	     	
    	return resposta;    	
    }

    public String getAlbumName(int num) throws RemoteException {
    	String data = "description | request; type | getAlbumName; Pos | "+num;
		 
		 String resposta = null;
		    
	    System.out.println("SENDING: " + data);
		data = multicastCom(data);
		
		if (data.split(":", 2)[0].startsWith("Server")) {
			data = data.split(":", 2)[1];
			System.out.println("RECEIVED: " + data);
				
			CopyOnWriteArrayList<String[]> arrayP = splittrim(data);
				
			synchronized (arrayP) {
		        for (String[] i : arrayP) {
		        	if (i[0].equals("description")) {
		        		arrayP.remove(i);
		            }
		        }
		    }
			
			synchronized (arrayP) {
		        for (String[] i : arrayP) {
		        	if (i[0].equals("type")) {
		        		arrayP.remove(i);
		            }
		        }
		    }
			synchronized (arrayP) {
		        for (String[] i : arrayP) {
		        	if (i[0].equals("Album")) {
		        		resposta = i[1];
		            }
		        }
		    }
	    } 	
		return resposta;    	
   }
    
    
}

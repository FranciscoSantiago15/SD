package projsd;


import java.net.MulticastSocket;
import java.rmi.RemoteException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;
import java.awt.List;
import java.io.*;

public class MulticastServer extends Thread{
	
	private String MULTICAST_ADDRESS = "224.0.224.0";
    private int PORT = 4444;
    
    public ArrayList<Utilizador> listaUtilizadores = null;
    public ArrayList<Musica> listaMusicas = null;
    public ArrayList<Album> listaAlbuns = null;    

        
    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {
    	
    	System.out.println("DADOS CARREGADOS !!");   
    	MulticastServer server = new MulticastServer();        
    	server.CarregarDados(); 
    	server.start();
    }

    public void run() {
        MulticastSocket socketRcv = null;
        MulticastSocket socket = null;
        String message;
        try {
            socketRcv = new MulticastSocket(PORT);  // create socket and bind it
            socket = new MulticastSocket();  // create socket and bind it
			InetAddress groupRcv = InetAddress.getByName(MULTICAST_ADDRESS);
			socketRcv.joinGroup(groupRcv);

			while (true) {
                byte[] bufferRcv = new byte[256];
                DatagramPacket packetRcv = new DatagramPacket(bufferRcv, bufferRcv.length);
                socketRcv.receive(packetRcv);

                message = new String(packetRcv.getData(), 0, packetRcv.getLength());
                
                /*--- FILTRAR DADOS---*/
                if (!message.split(":", 2)[0].startsWith("Server")) { // Send
                    System.out.println("FOUND: " + message);
                    
                    CopyOnWriteArrayList<String[]> arrayP = null;
                    String resposta = "";
                    int flag = 0;
                    arrayP = splittrim(message);
                    
                    String description = null;
                    String type = null;
                    
                    synchronized (arrayP) {
                        for (String[] i : arrayP) {
                            if (i[0].equals("description")) {
                                description = i[1];
                                arrayP.remove(i);
                                flag++;
                            }
                        }
                    }
                    
                    switch (description){
	                    //////////////////REQUEST//////////////////
	                    case("request"): 
	                        synchronized (arrayP) {
	                            for (String[] i : arrayP) {
	                                if (i[0].equals("type")) {
	                                    type = i[1];
	                                    arrayP.remove(i);
	                                    flag++;
	                                }
	                            }
	                        }
	                        switch(type){
	                            case("register"):
	                                resposta = Registar(arrayP, listaUtilizadores);
	                                break;
	                            case("login"):
	                                resposta = Login(arrayP, listaUtilizadores);
	                                break;
	                            case("manage"):
	                            	resposta = Gerir(arrayP, listaMusicas, listaAlbuns);
	                            	break;
	                            case("search_song"):
	                            	resposta = SearchSong(arrayP, listaMusicas, listaAlbuns);
	                            	break;
	                            case("search_song2"):
	                            	resposta = SearchSong2(arrayP, listaMusicas);
	                            	break;
                            	case("search_details"):
	                            	resposta = SearchDetails(arrayP, listaAlbuns, listaMusicas);
                        			break;
                            	case("album_critic"):
	                            	resposta = WriteCritic(arrayP, listaAlbuns);
	                            	break;
	                            case("give_privilege"):
	                            	resposta = GivePrivilege(arrayP, listaUtilizadores);
	                            	break;
	                            case("allUsers"):
	                            	resposta = AllUsers(arrayP, listaUtilizadores);
	                            	break;
	                            case("getAlbuns"):
	                            	resposta = getAlbuns(arrayP, listaAlbuns);
	                            	break;
	                            case("getAlbumName"):
	                            	resposta = getAlbumName(arrayP,listaAlbuns);
	                            	break;
	                        }                            
	                        break;
	                }
	                flag = 0;
	                //System.out.println(resposta);
                    
                    /*--- SEND ---*/
	                message = "Server:" + resposta;
                    byte[] buffer = message.getBytes();
                    InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                    socket.send(packet);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            socketRcv.close();
            socket.close();
        }
    }
    
    /*--- CARREGAR/GUARDAR DADOS ---*/
    public void CarregarDados() throws FileNotFoundException, IOException, ClassNotFoundException {
    	FileInputStream fis = new FileInputStream("UTILIZADORES.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);         
        listaUtilizadores = (ArrayList<Utilizador>) ois.readObject();        
        //System.out.println(listaUtilizadores);        
        ois.close();
        
        FileInputStream fis2 = new FileInputStream("MUSICAS.ser");
        ObjectInputStream ois2 = new ObjectInputStream(fis2);         
        listaMusicas = (ArrayList<Musica>) ois2.readObject();        
        //System.out.println(listaMusicas);  
        ois2.close();
        
        FileInputStream fis3 = new FileInputStream("ALBUNS.ser");
        ObjectInputStream ois3 = new ObjectInputStream(fis3);         
        listaAlbuns = (ArrayList<Album>) ois3.readObject();        
        //System.out.println(listaAlbuns);        
        ois3.close();
        
    }    
    
    public void GuardarDados() throws FileNotFoundException, IOException, ClassNotFoundException{
    	FileOutputStream fos = new FileOutputStream("UTILIZADORES.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(listaUtilizadores);
        oos.close();
        
        FileOutputStream fos2 = new FileOutputStream("MUSICAS.ser");
        ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
        oos2.writeObject(listaMusicas);
        oos2.close();
        
        FileOutputStream fos3 = new FileOutputStream("ALBUNS.ser");
        ObjectOutputStream oos3 = new ObjectOutputStream(fos3);
        oos3.writeObject(listaAlbuns);
        oos3.close();
    }
    
    /*--- PARTIR DADOS ---*/
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
    
    /*--- FUNCOES ---*/
    public String Gerir(CopyOnWriteArrayList<String[]> arrayP, ArrayList<Musica> listaMusicas, ArrayList<Album> listaAlbuns) throws FileNotFoundException, ClassNotFoundException, IOException {
    	String resposta = null;
    	String classe = "";
    	int tipo = 0;
    	String info = "";
    	
    	synchronized (arrayP) {
            for (String[] i : arrayP) {
                if (i[0].equals("class")) {
                    classe = i[1];
                    arrayP.remove(i);
                }
            }
        }
    	synchronized (arrayP) {
            for (String[] i : arrayP) {
                if (i[0].equals("type2")) {
                    tipo = Integer.parseInt(i[1]);
                    arrayP.remove(i);
                }
            }
        }
    	synchronized (arrayP) {
            for (String[] i : arrayP) {
                if (i[0].equals("info")) {
                    info = i[1];
                    arrayP.remove(i);
                }
            }
        }  
    	
    	info = info.replace("{","");
    	info = info.replace("}","");
    	info = info.replace("=","|");
    	info = info.replace("!",";");
    	    	
    	arrayP = splittrim(info);
    	
    	if(tipo == 1) {                                     /* ------ CRIAR ------ */
    		if(classe.equals("Album")) {
    			String titulo = arrayP.get(0)[1];
    			String[] listaM = arrayP.get(1)[1].replace(" ", "").replace("[","").replace("]", "").split(",");
    			String data = arrayP.get(2)[1];
    			String[] a1 = arrayP.get(3)[1].replace("[","").replace("]", "").split(",");
    			ArrayList<String> listaG = new ArrayList<String>(Arrays.asList(a1));
    			String[] a2 = arrayP.get(4)[1].replace("[","").replace("]", "").split(",");
    			ArrayList<String> listaC = new ArrayList<String>(Arrays.asList(a2));
    			
    			ArrayList<Musica> auxL = new ArrayList<Musica>();
    			for(int i=0; i<listaM.length; i++) {
    				Musica m = new Musica(listaM[i], null, null);
    				auxL.add(m);
    			}
    			Album aux = new Album(titulo,auxL,null,data,listaG,0.0,listaC);
    			listaAlbuns.add(aux);
    			resposta ="description | request; type | manage; ok | true";
    			
    		} else if(classe.equals("Musica")) {
    			String nome = arrayP.get(0)[1];
    			String[] a1 = arrayP.get(1)[1].replace(" ", "").replace("[","").replace("]", "").split(",");
    			ArrayList<String> listaArtistas = new ArrayList<String>(Arrays.asList(a1));
    			String[] a2 = arrayP.get(2)[1].replace("[","").replace("]", "").split(",");
    			ArrayList<String> GruposMusicais = new ArrayList<String>(Arrays.asList(a2));
    			String[] listaAlb  = arrayP.get(3)[1].replace("[","").replace("]", "").split(",");
    			String historia = arrayP.get(4)[1];
    			
    			ArrayList<Album> auxL = new ArrayList<Album>();
    			for(int i=0; i<listaAlb.length; i++) {
    				Album a = new Album(listaAlb[i], null, null);
    				auxL.add(a);
    			}
    			Musica aux = new Musica(nome,listaArtistas,GruposMusicais,auxL,historia);
    			listaMusicas.add(aux);
    			resposta ="description | request; type | manage; ok | true";
    		}

    	} else if(tipo == 2) {						/* ------ EDITAR ------ */
    		if(classe.equals("Album")) {
    			String nomeOriginal = info.split(";")[0];
    			
    			String atribEdit = arrayP.get(0)[0];
    			String valorEdit = arrayP.get(0)[1];
    			
    			if(atribEdit.equals("titulo")) {
    				for(int i=0; i<listaAlbuns.size(); i++) {
    					if(listaAlbuns.get(i).getTitulo().equals(nomeOriginal)) {
    						listaAlbuns.get(i).setTitulo(valorEdit);
    						resposta ="description | request; type | manage; ok | true";
    					}
    				}
    			} else if(atribEdit.equals("Musicas")) {
    				for(int i=0; i<listaAlbuns.size(); i++) {
    					if(listaAlbuns.get(i).getTitulo().equals(nomeOriginal)) {
    						valorEdit = valorEdit.replace("[","");
    						valorEdit = valorEdit.replace("]","");    						
    						String[] ed = valorEdit.split(",");
    						ArrayList<String> auxEdit = new ArrayList<String>(Arrays.asList(ed));
    						
    						//listaAlbuns.get(i).setMusicas(valorEdit);
    						
    						resposta ="description | request; type | manage; ok | true";
    					}
    				}
    			} else if(atribEdit.equals("data")) {
    				for(int i=0; i<listaAlbuns.size(); i++) {
    					if(listaAlbuns.get(i).getTitulo().equals(nomeOriginal)) {
    						listaAlbuns.get(i).setDataLancamento(valorEdit);
    						resposta ="description | request; type | manage; ok | true";
    					}
    				}
    			} else if(atribEdit.equals("Generos")) {
    				for(int i=0; i<listaAlbuns.size(); i++) {
    					if(listaAlbuns.get(i).getTitulo().equals(nomeOriginal)) {
    						valorEdit = valorEdit.replace("[","");
    						valorEdit = valorEdit.replace("]","");    						
    						String[] ed = valorEdit.split(",");
    						ArrayList<String> auxEdit = new ArrayList<String>(Arrays.asList(ed));
    						
    						listaAlbuns.get(i).setGeneros(auxEdit);
    						
    						resposta ="description | request; type | manage; ok | true";
    					}
    				}
    			} else if(atribEdit.equals("Concertos")) {
    				for(int i=0; i<listaAlbuns.size(); i++) {
    					if(listaAlbuns.get(i).getTitulo().equals(nomeOriginal)) {
    						valorEdit = valorEdit.replace("[","");
    						valorEdit = valorEdit.replace("]","");    						
    						String[] ed = valorEdit.split(",");
    						ArrayList<String> auxEdit = new ArrayList<String>(Arrays.asList(ed));
    						
    						listaAlbuns.get(i).setConcertos(auxEdit);
    						
    						resposta ="description | request; type | manage; ok | true";
    					}
    				}
    			}
    			
    		}
    			
    			resposta ="description | request; type | manage; ok | true";
    			
    		} else if(classe.equals("Musica")) {
    			String nomeOriginal = info.split(";")[0];
    			//String infoEdit = info.split(";")[1];
    			
    			String atribEdit = arrayP.get(0)[0];
    			String valorEdit = arrayP.get(0)[1];
    			
    			if(atribEdit.equals("nome")) {
    				for(int i=0; i<listaMusicas.size(); i++) {
    					if(listaMusicas.get(i).getNome().equals(nomeOriginal)) {
    						listaMusicas.get(i).setNome(valorEdit);
    						resposta ="description | request; type | manage; ok | true";
    					}
    				}
    			} else if(atribEdit.equals("Artistas")) {
    				for(int i=0; i<listaMusicas.size(); i++) {
    					if(listaMusicas.get(i).getNome().equals(nomeOriginal)) {
    						valorEdit = valorEdit.replace("[","");
    						valorEdit = valorEdit.replace("]","");    						
    						String[] ed = valorEdit.split(",");
    						ArrayList<String> auxEdit = new ArrayList<String>(Arrays.asList(ed));
    						
    						listaMusicas.get(i).setArtistas(auxEdit);
    						
    						resposta ="description | request; type | manage; ok | true";
    					}
    				}
    			}else if(atribEdit.equals("Gr_Musicas")) {
    				for(int i=0; i<listaMusicas.size(); i++) {
    					if(listaMusicas.get(i).getNome().equals(nomeOriginal)) {
    						valorEdit = valorEdit.replace("[","");
    						valorEdit = valorEdit.replace("]","");    						
    						String[] ed = valorEdit.split(",");
    						ArrayList<String> auxEdit = new ArrayList<String>(Arrays.asList(ed));
    						
    						listaMusicas.get(i).setGruposMusicais(auxEdit);
    						
    						resposta ="description | request; type | manage; ok | true";
    					}
    				}
    			}else if(atribEdit.equals("historia")) {
    				for(int i=0; i<listaMusicas.size(); i++) {
    					if(listaMusicas.get(i).getNome().equals(nomeOriginal)) {
    						listaMusicas.get(i).setHistoria(valorEdit);
    						resposta ="description | request; type | manage; ok | true";
    					}
    				}
    			}
    		
    	} else if(tipo == 3) {							/* ------ APAGAR ------ */
    		if(classe.equals("Album")) {
    			String auxT = arrayP.get(0)[1];
    			for(int i=0; i<listaAlbuns.size(); i++) {
    				if(auxT.equals(listaAlbuns.get(i).getTitulo())){
    					listaAlbuns.remove(i);
    					resposta ="description | request; type | manage; ok | true";
    				}
    			}
    		} else if(classe.equals("Musica")) {
    			String auxT = arrayP.get(0)[1];
    			for(int i=0; i<listaMusicas.size(); i++) {
    				if(auxT.equals(listaMusicas.get(i).getNome())){
    					listaMusicas.remove(i);
    					resposta ="description | request; type | manage; ok | true";
    				}
    			}
    		}
    	} else {
    		resposta ="description | request; type | manage; class | "+classe+"; type2 | "+tipo+"; ok | false";
    	}
    	
    	//GuardarDados();
    	return resposta;
    }
    public String Registar(CopyOnWriteArrayList<String[]> arrayP, ArrayList<Utilizador> listaUtilizadores) throws FileNotFoundException, ClassNotFoundException, IOException {
        int flag = 0;
        String resposta = null;
        String username = "";
        String password = "";
        
        if (arrayP.size() != 2) {
            return resposta = "description | response; type | register;  ok:false; msg | Incorrect command format";
        }
        for (String[] elem : arrayP) {
            if (elem[0].equals("username")) {
                flag++;
            }
        }
        if (flag != 1) {
            return resposta = "description | response; type | register;  ok:false; msg | Incorrect command format";
        }
        for (String[] elem : arrayP) {
            if (elem[0].equals("password")) {
                flag++;
            }
        }
        
        if (flag != 2) {
            return resposta = "description | response; type | register;  ok | false; msg | Incorrect command format";
        }
        
        username = arrayP.get(0)[1];
        password = arrayP.get(1)[1];
        
        for(int i=0; i<listaUtilizadores.size();i++) {
        	if(username.equals(listaUtilizadores.get(i).getNome())) {
        		resposta = "description | response; type | register;  ok | false; msg | Username already exists";
        		return resposta;
        	}
        }
        
        Utilizador u = new Utilizador(username, password);
        listaUtilizadores.add(u);
        GuardarDados();
        resposta = "description | response; type | register;  ok | true";
       
        return resposta;
    }
    
    public String Login(CopyOnWriteArrayList<String[]> arrayP, ArrayList<Utilizador> listaUtilizadores) throws RemoteException {
    	int flag = 0;
        String resposta = null;
        String username = "";
        String password = "";
        
        if (arrayP.size() != 2) {
            return resposta = "description | response; type | login;  ok | false; msg | Incorrect command format";
        }
        for (String[] elem : arrayP) {
            if (elem[0].equals("username")) {
                flag++;
            }
        }
        if (flag != 1) {
            return resposta = "description | response; type | login;  ok | false; msg | Incorrect command format";
        }
        for (String[] elem : arrayP) {
            if (elem[0].equals("password")) {
                flag++;
            }
        }
        
        if (flag != 2) {
            return resposta = "description | response; type | login;  ok | false; msg | Incorrect command format";
        }
        
        username = arrayP.get(0)[1];
        password = arrayP.get(1)[1];
        
        for(int i=0; i<listaUtilizadores.size(); i++) {
        	if(username.equals(listaUtilizadores.get(i).getNome()) && password.equals(listaUtilizadores.get(i).getPass())){
        		resposta = "description | response; type | login; editor | "+listaUtilizadores.get(i).getEditor()+"; ok | true";
        	}
        }
        
        return resposta;

    }

    public String SearchSong(CopyOnWriteArrayList<String[]> arrayP, ArrayList<Musica> listaMusicas, ArrayList<Album> listaAlbuns) throws RemoteException {
    	int flag = 0;
        String resposta = null;
        String nome = "";
        ArrayList<String> lista = new ArrayList<String>();
        
        if (arrayP.size() != 1) {
            resposta = "description | response; type | search_song;  ok | false; msg | Incorrect command format";
        }
        
        for (String[] elem : arrayP) {
            if ((elem[0].equals("name") && flag == 0)) {
                flag++;
            }
        }
                
        if (flag == 1) {
        	nome = arrayP.get(0)[1];        	
        	for(int i=0; i<listaAlbuns.size(); i++) {
        		if(nome.equals(listaAlbuns.get(i).getTitulo())) {
        			for(int j=0; j<listaAlbuns.get(i).getMusicas().size(); j++) {
        				lista.add(listaAlbuns.get(i).getMusicas().get(j).getNome());
        			}
        		}
        		for(int j=0; j<listaAlbuns.get(i).getGeneros().size(); j++) {
        			if(nome.equals(listaAlbuns.get(i).getGeneros().get(j))) {
        				for(int k=0; k<listaAlbuns.get(i).getMusicas().size(); k++) {
            				lista.add(listaAlbuns.get(i).getMusicas().get(k).getNome());
            			}
            		}
        		}
        	}  
        	
        	for(int i=0; i<listaMusicas.size(); i++) {
        		for(int j=0; j<listaMusicas.get(i).getArtistas().size(); j++) {
        			if(nome.equals(listaMusicas.get(i).getArtistas().get(j))) {
            			lista.add(listaMusicas.get(i).getNome());
            		}
        		}
        	}          	
        }    
        
        resposta = "description | response; type | search_song;  item_count | "+lista.size();

        for(int i=0; i<lista.size(); i++) {
        	resposta += "; item_"+i+"_name | "+lista.get(i);
        }
        
        
    	return resposta;
    }

    public String SearchSong2(CopyOnWriteArrayList<String[]> arrayP, ArrayList<Musica> listaMusicas) throws RemoteException {
    	String nome = arrayP.get(0)[1];
    	String artistas = "artistas | [";
    	String gruposMusicais = "]; gruposMusicais | [";
    	String albuns = "]; albuns | [";
    	String historia = "] historia | ";
        String resposta = "description | response; type | search_song2; name | "+nome+"; ";
        
        for(int i=0; i<listaMusicas.size();i++) {
        	if(nome.equals(listaMusicas.get(i).getNome())) {
        		for(int j=0; j<listaMusicas.get(i).getArtistas().size(); j++) {
        			if(j<listaMusicas.get(i).getArtistas().size() - 1) {
        				artistas += listaMusicas.get(i).getArtistas().get(j)+",";
        			} else {
        				artistas += listaMusicas.get(i).getArtistas().get(j);
        			}
        		}
        		for(int j=0; j<listaMusicas.get(i).getGruposMusicais().size(); j++) {
        			if(j<listaMusicas.get(i).getGruposMusicais().size() - 1) {
        				gruposMusicais += listaMusicas.get(i).getGruposMusicais().get(j)+",";
        			} else {
        				gruposMusicais += listaMusicas.get(i).getGruposMusicais().get(j);
        			}
        		}
        		/*for(int j=0; j<listaMusicas.get(i).getAlbuns().size(); j++) {
        			if(j<listaMusicas.get(i).getAlbuns().size() - 1) {
        				albuns += listaMusicas.get(i).getAlbuns().get(j)+",";
        			} else {
        				albuns += listaMusicas.get(i).getAlbuns().get(j);
        			}
        		}*/
        		historia += listaMusicas.get(i).getHistoria();
        	}        	
        }
        resposta += artistas + gruposMusicais + albuns + historia;
        
        return resposta;
    }
    
    public String SearchDetails(CopyOnWriteArrayList<String[]> arrayP, ArrayList<Album> listaAlbuns, ArrayList<Musica> listaMusicas) throws RemoteException {
    	int tipo = Integer.parseInt(arrayP.get(0)[1]);
    	String nome = arrayP.get(1)[1];
    	String musicas = "; lista_musicas | [";
    	//String artistas = "; artistas | [";
    	String criticas = "; criticas | [";
    	String concertos = "; concertos | [";
    	
    	String resposta = "description | response; type | search_details; name | "+nome;
    
    	if(tipo == 1) {
    		for(int i=0; i<listaAlbuns.size(); i++) {
        		if(nome.equals(listaAlbuns.get(i).getTitulo())) {
        			for(int j=0; j<listaAlbuns.get(i).getMusicas().size(); j++) { // Listamusicas
        				if(j<listaAlbuns.get(i).getMusicas().size() - 1) {
    						musicas += listaAlbuns.get(i).getMusicas().get(j).getNome()+",";
            			} else {
            				musicas += listaAlbuns.get(i).getMusicas().get(j).getNome();
            			}
        				/*String aux = "musica"+j+" | ";
        				aux += listaAlbuns.get(i).getMusicas().get(j).getNome();                                         // nome
        				for(int k=0; k<listaAlbuns.get(i).getMusicas().get(j).getArtistas().size(); k++) {               // artristas
        					if(k<listaAlbuns.get(i).getMusicas().get(j).getArtistas().size() - 1) {
        						artistas += listaAlbuns.get(i).getMusicas().get(j).getArtistas().get(k)+",";
                			} else {
                				artistas += listaAlbuns.get(i).getMusicas().get(j).getArtistas().get(k);
                			}
        				}
        				
        				resposta += "; "+aux+artistas+"]";*/
        			}
    				resposta += musicas + "]";
        			if(listaAlbuns.get(i).getCriticas() == null) {
        				criticas += "";
        			} else {
    	    			for(int j=0; j<listaAlbuns.get(i).getCriticas().size(); j++) {
    	    				if(j<listaAlbuns.get(i).getCriticas().size() - 1) {
    							criticas += listaAlbuns.get(i).getCriticas().get(j)+",";
    	        			} else {
    	        				criticas += listaAlbuns.get(i).getCriticas().get(j);
    	        			} 
    	    			}
        			}
        			resposta += criticas;
        			resposta += "]; data_lancamento | "+listaAlbuns.get(i).getDataLancamento()+"; pontuacao | "+listaAlbuns.get(i).getPontuacao();
        			if(listaAlbuns.get(i).getConcertos() == null) {
        				concertos += "";
        			} else {
    					for(int j=0; j<listaAlbuns.get(i).getConcertos().size(); j++) {
    						if(j<listaAlbuns.get(i).getConcertos().size() - 1) {
    							concertos += listaAlbuns.get(i).getConcertos().get(j)+",";
    		    			} else {
    		    				concertos += listaAlbuns.get(i).getConcertos().get(j);
    		    			} 
    					}
        			}
        			resposta += concertos + "]";
        		}
        	}
    	} else if (tipo == 2) {
    		for(int i=0; i<listaMusicas.size(); i++) {
    			for(int j=0; j<listaMusicas.get(i).getArtistas().size(); j++) {
    				if(nome.equals(listaMusicas.get(i).getArtistas().get(j))) {
						musicas += listaMusicas.get(i)+",";
    				}
    			}
			}
    		resposta += musicas+"]";
    	}    	
    	    	
    	return resposta;
    }
    
    public String WriteCritic(CopyOnWriteArrayList<String[]> arrayP, ArrayList<Album> listaAlbuns) throws FileNotFoundException, ClassNotFoundException, IOException{
    	int flag = 0;
        String resposta = null;
        String critica = "";
        int pontuacao = 0;
        
        if (arrayP.size() != 3) {
            return resposta = "description | response; type | album_critic;  ok:false; msg | Incorrect command format";
        }
        for (String[] elem : arrayP) {
            if (elem[0].equals("album")) {
                flag++;
            }
        }
        if (flag != 1) {
            return resposta = "description | response; type | album_critic;  ok:false; msg | Incorrect command format";
        }
        for (String[] elem : arrayP) {
            if (elem[0].equals("points")) {
                flag++;
            }
        }        
        if (flag != 2) {
            return resposta = "description | response; type | album_critic;  ok | false; msg | Incorrect command format";
        }
        for (String[] elem : arrayP) {
            if (elem[0].equals("critic")) {
                flag++;
            }
        }        
        if (flag != 3) {
            return resposta = "description | response; type | album_critic;  ok | false; msg | Incorrect command format";
        }
        
        for(int i=0; i<listaAlbuns.size(); i++) {
        	if(arrayP.get(0)[1].equals(listaAlbuns.get(i).getTitulo())) {
        		
        		double auxPont = Double.parseDouble(arrayP.get(1)[1]);
        		double pont;
        		if(listaAlbuns.get(i).getPontuacao() == 0.0) {
        			pont = auxPont;
        		} else {
        			pont = (listaAlbuns.get(i).getPontuacao() + auxPont) / 2;
        		}
        		
        		listaAlbuns.get(i).setPontuacao(pont);
        		
        		ArrayList<String> aux = new ArrayList<String>();
        		if(listaAlbuns.get(i).getCriticas() == null) {
        			aux.add(arrayP.get(2)[1]);
        		} else {
        			aux = listaAlbuns.get(i).getCriticas();
        			aux.add(arrayP.get(2)[1]);
        		}
        		
        		listaAlbuns.get(i).setCriticas(aux);
        		
        		resposta = "description | response; type | album_critic;  ok | true";
        	}
        }
        
        GuardarDados();
    	return resposta;
    }
    
    public String GivePrivilege(CopyOnWriteArrayList<String[]> arrayP, ArrayList<Utilizador> listaUtilizadores) throws FileNotFoundException, ClassNotFoundException, IOException {
    	int flag = 0;
        String resposta = null;
        
    	if (arrayP.size() != 1) {
            return resposta = "description | request; type | give_privilege;  ok | false; msg | Incorrect command format";
        }
        
        for (String[] elem : arrayP) {
            if ((elem[0].equals("name") && flag == 0)) {
                flag++;
            }
        }
        
        if (arrayP.size() != 1) {
            return resposta = "description | request; type | give_privilege;  ok | false; msg | Incorrect command format";
        }
        
        for(int i=0; i<listaUtilizadores.size();i++) {
        	if(arrayP.get(0)[1].equals(listaUtilizadores.get(i).getNome())) {
        		listaUtilizadores.get(i).setEditor(1);
        		resposta = "description | request; type | give_privilege;  ok | true";
        	}
        }
        
    	GuardarDados();
    	return resposta;
    }
    
    
    public String AllUsers(CopyOnWriteArrayList<String[]> arrayP, ArrayList<Utilizador> listaUtilizadores) throws FileNotFoundException, ClassNotFoundException, IOException {
        String resposta = "description | request; type | allUsers;  Utilizadores | [";        
        
        for(int i=0; i<listaUtilizadores.size();i++) {
        	if(listaUtilizadores.get(i).getEditor() == 0) {
				resposta += listaUtilizadores.get(i).getNome() + ",";
        	}
        }
        resposta += "]";
    	return resposta;
    }
    
    public String getAlbuns(CopyOnWriteArrayList<String[]> arrayP, ArrayList<Album> listaAlbuns) throws FileNotFoundException, ClassNotFoundException, IOException {
        String resposta = "description | request; type | getAlbuns;  Albuns | [";        
        
        for(int i=0; i<listaAlbuns.size();i++) {
			resposta += listaAlbuns.get(i).getTitulo() + ",";
        }
        resposta += "]";
    	return resposta;
    }
    
    
    public String getAlbumName(CopyOnWriteArrayList<String[]> arrayP, ArrayList<Album> listaAlbuns) throws FileNotFoundException, ClassNotFoundException, IOException {
        String resposta = "description | request; type | getAlbumName;  Album | "; 
        
        int pos = Integer.parseInt(arrayP.get(0)[1]);
        
        resposta += listaAlbuns.get(pos).getTitulo();
        
    	return resposta;
    }
    
}
	
	
package projsd;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RMIUSer {
	
	public static void main(String args[]) {

		/* This might be necessary if you ever need to download classes: 
		System.getProperties().put("java.security.policy", "policy.all");
		System.setSecurityManager(new RMISecurityManager());*/		
		
		int editor;

		try {
			
			RMIInterface server = (RMIInterface) LocateRegistry.getRegistry(7000).lookup("DropMusic");
			System.out.println("RMI User: Connected !!");	
			
			/*    LOGIN    
			System.out.println(server.Login("USER1","PASS1"));
			// meter dados ao criar utilizador
			*/
			
			/* REGISTAR */
			//System.out.println(server.Registar("USER4","PASS4"));
			
			/* PESQUISAR MUSICA */
			/*ArrayList<String> a = server.SearchSong("Pop");
			System.out.println(a);
			System.out.println(server.SearchSong2(a.get(1)));*/
			
			/* Critica */
			//System.out.println(server.AlbumCritic("Album 1", 3, "Podia estar melhor"));
			
			/* Editor */
			//System.out.println(server.GivePrivilege("USER2"));
			
			//-----------------------------------------------------------------------------------
			
			
			Scanner sc = new Scanner(System.in); 
			Scanner sc2 = new Scanner(System.in); 
			int op = -1;
	        int op2 = -1;
	        int op3 = -1;
	        int auxOp;
	        
	        System.out.println("\tBEM VINDO");
	        while (op != 0) {
	        	System.out.println("1 - Login");
	        	System.out.println("2 - Registar");
	        	System.out.println("3 - Todos Utilizadores");
	        	System.out.println("4 - Todos Albuns");
	        	System.out.println("5 - Album pos [2]");
	        	System.out.print("Op: ");
	        	op = sc.nextInt();
	        	
	        	if((op < 0) || (op > 5)) {
	                System.out.println("\n\t* Opcao invalida *");
	                continue;
	            }
	        	
	        	switch(op){
	            	case 1:
	            		System.out.println("LOGIN");
		                System.out.print("Insira o seu nome: ");
		                String nome = sc.next();
		                System.out.print("Insira a sua password: ");
		                String pass = sc.next();
		                ArrayList<String> aux = server.Login(nome, pass);
		                
		                if(aux.get(0).equals("LOGIN ACEITE !")) {
		                	
		                	editor = Integer.parseInt(aux.get(1));
		                	
		                	System.out.println("_______MENU "+editor+"_______"); 
		        	        while (op2 != 0) {
		        	            System.out.print("\nINTRODUZA A OPCAO:\n");          
		        	            System.out.print("1- Gerir artistas, albuns e musicas\n");
		        	            System.out.print("2- Pesquisar musicas\n");
		        	            System.out.print("3- Consultar detalhes sobre albuns e artistas\n");
		        	            System.out.print("4- Escrever critica a album\n");
		        	            System.out.print("5- Dar privilégios de editor a um utilizador\n");
		        	            System.out.print("6- Transferir musicas para o servidor\n");
		        	            System.out.print("7- Partilhar musicas\n");
		        	            System.out.print("8- Transferir musicas do servidor\n");
		        	            System.out.print("0- Sair\n");
		        	            System.out.print("----------------------------------------------------\n");
		        	            System.out.print("Opcao: ");
		        	            op2 = sc2.nextInt();
		        	 
		        	            if((op2 < 0) || (op2 > 8)) {
		        	                System.out.println("\n\t* Opcao invalida *");
		        	                continue;
		        	            }
		        	            switch(op2){
		        		            case 1:	
		        		            	if(editor == 1) {
			        		            	System.out.println("\tGESTOR\n1 - Criar\n2 - Editar\n3 - Apagar");
			        		            	System.out.print("Op: ");
			        			        	op3 = sc.nextInt();
			        			        	
			        			        	System.out.print ("Tipo da Classe (Album,Musica,...): ");
			        			        	sc.nextLine();
			        		            	nome = sc.nextLine();
			        		            	switch(op3) {
			        		            		case 1:
					        		            	int aux2=0;
					        		            	String aux3;
					        		            	String titulo="titulo = ";
					        		            	ArrayList<String> listaMusicas = new ArrayList<String>();
					        		            	String dataLancamento = "data = ";
					        		            	ArrayList<String> generos = new ArrayList<String>();
					        		            	ArrayList<String> concertos = new ArrayList<String>();
					        		            	String historia = "";
					        		            	
					        		            	if(nome.equals("album")) {
					        		            		System.out.print("Titulo: ");
					        		            		titulo += sc.nextLine();
					        		            		System.out.print("Quantas musicas quer adicionar ?\n-> ");
					        		            		aux2 = sc.nextInt();
					        		            		sc.nextLine();
					        		            		for(int i=0; i<aux2; i++) {
					        		            			System.out.print("Musica "+(i+1)+": ");
					        		            			aux3 = sc.nextLine();
					        		            			listaMusicas.add(aux3);
					        		            		}
					        		            		System.out.print("Data Lancamento: ");
					        		            		dataLancamento = sc.nextLine();
					        		            		System.out.print("Quantas generos tem ?\n-> ");
					        		            		aux2 = sc.nextInt();
					        		            		sc.nextLine();
					        		            		for(int i=0; i<aux2; i++) {
					        		            			System.out.print("Genero "+(i+1)+": ");
					        		            			aux3 = sc.nextLine();
					        		            			generos.add(aux3);
					        		            		}
					        		            		System.out.print("Quantas concertos irao ter ?\n-> ");
					        		            		aux2 = sc.nextInt();
					        		            		sc.nextLine();
					        		            		for(int i=0; i<aux2; i++) {
					        		            			System.out.print("Concerto "+(i+1)+": ");
					        		            			aux3 = sc.nextLine();
					        		            			concertos.add(aux3);
					        		            		}
					        		            		
					        		            		String info ="{"+titulo+"! Musicas = "+listaMusicas+"! data = "+dataLancamento+"! generos = "+generos+"! concertos = "+concertos+"}"; 
					        		            		
					        		            		System.out.println(server.Gerir("Album", op3, info));
					        		            		
					        		            	} else if(nome.equals("musica")) {
					        		            		System.out.print("Nome: ");
					        		            		titulo += sc.nextLine();
					        		            		System.out.print("Quantas artistas quer adicionar ?\n-> ");
					        		            		aux2 = sc.nextInt();
					        		            		sc.nextLine();
					        		            		for(int i=0; i<aux2; i++) {
					        		            			System.out.print("Artista "+(i+1)+": ");
					        		            			aux3 = sc.nextLine();
					        		            			listaMusicas.add(aux3);
					        		            		}
					        		            		System.out.print("Quantas Grupos Musicais tem ?\n-> ");
					        		            		aux2 = sc.nextInt();
					        		            		sc.nextLine();
					        		            		for(int i=0; i<aux2; i++) {
					        		            			System.out.print("Grupo nº "+(i+1)+": ");
					        		            			aux3 = sc.nextLine();
					        		            			generos.add(aux3);
					        		            		}
					        		            		System.out.print("Numero de Albuns -> ");
					        		            		aux2 = sc.nextInt();
					        		            		sc.nextLine();
					        		            		for(int i=0; i<aux2; i++) {
					        		            			System.out.print("Album nº "+(i+1)+": ");
					        		            			aux3 = sc.nextLine();
					        		            			concertos.add(aux3);
					        		            		}
					        		            		System.out.print("Historia: ");
					        		            		historia = sc.nextLine();
					        		            		
					        		            		String info ="{"+titulo+"! Artistas = "+listaMusicas+"! Gr_Musicais = "+generos+"! Albuns = "+concertos+"! historia = "+historia+"}"; 
					        		            		
					        		            		System.out.println(server.Gerir("Musica", op3, info));
					        		            		
					        		            	} else {
					        		            		System.out.println("Nao da\n");
					        		            	}
					        		            	break;
			        		            		case 2:
				        		            		String info =""; 

			        		            			if(nome.equals("album")) {
			        		            				System.out.print("Nome Album a alterar: ");
			        		            				String muOrig = sc.nextLine();
			        		            				
			        		            				info += "{"+muOrig+"! ";
			        		            				
			        		            				System.out.println("1 - Titulo");
			        		            				//System.out.println("2 - Musicas");
			        		            				System.out.println("3 - Data Lancamento");
			        		            				System.out.println("4 - Generos");
			        		            				System.out.println("5 - Concertos");
			        		            				
			        		            				int opEd = sc.nextInt();
			        		            				sc.nextLine();
			        		            				switch(opEd) {
			        		            					case 1:
			        		            						System.out.print("Titulo: ");
			        		            						String auxEd = sc.nextLine();
			        		            						info += "titulo = "+auxEd+"}";
			        		            						break;
			        		            					case 2:
			        		            						/*System.out.print("Num Musicas: ");
			        		            						int nArt = sc.nextInt();
			        		            						ArrayList<String> art = new ArrayList<String>();
			        		            						for(int i=0; i<nArt; i++) {
			        		            							System.out.print("Musica "+i+": ");
			        		            							String a = sc.nextLine();
			        		            							art.add(a);
			        		            						}
			        		            						info += "Musicas = "+art+"}"; */
			        		            						break;
			        		            					case 3:
			        		            						System.out.print("Data lancamento: ");
			        		            						String auxHist = sc.nextLine();
			        		            						info += "data = "+auxHist+"}";
			        		            						break;			        		            						
			        		            					case 4:
			        		            						System.out.print("Num Generos: ");
			        		            						int nGM = sc.nextInt();
			        		            						ArrayList<String> gm = new ArrayList<String>();
			        		            						for(int i=0; i<nGM; i++) {
			        		            							System.out.print("Genero "+i+": ");
			        		            							String a = sc.nextLine();
			        		            							gm.add(a);
			        		            						}
			        		            						info += "Generos = "+gm+"}";
			        		            						break;
			        		            					case 5:
			        		            						System.out.print("Num concertos: ");
			        		            						int nC = sc.nextInt();
			        		            						ArrayList<String> auxC = new ArrayList<String>();
			        		            						for(int i=0; i<nC; i++) {
			        		            							System.out.print("Genero "+i+": ");
			        		            							String a = sc.nextLine();
			        		            							auxC.add(a);
			        		            						}
			        		            						info += "Concertos = "+auxC+"}";
			        		            						break;
			        		            				}
			        		            				
			        		            				System.out.println(server.Gerir("Album", op3, info));
			        		            				
			        		            			} else if(nome.equals("musica")) {
			        		            				System.out.print("Nome musica a alterar: ");
			        		            				String muOrig = sc.nextLine();
			        		            				
			        		            				info += "{"+muOrig+"! ";
			        		            				
			        		            				System.out.println("1 - Nome");
			        		            				System.out.println("2 - Artistas");
			        		            				System.out.println("3 - Grupos Musicais");
			        		            			    //protected ArrayList<Album> albuns;
			        		            				System.out.print("4 - historia\nOp: ");
			        		            				int opEd = sc.nextInt();
			        		            				sc.nextLine();
			        		            				switch(opEd) {
			        		            					case 1:
			        		            						System.out.print("Nome: ");
			        		            						String auxEd = sc.nextLine();
			        		            						info += "nome = "+auxEd+"}";
			        		            						break;
			        		            					case 2:
			        		            						System.out.print("Num Artistas: ");
			        		            						int nArt = sc.nextInt();
			        		            						ArrayList<String> art = new ArrayList<String>();
			        		            						for(int i=0; i<nArt; i++) {
			        		            							System.out.print("Nome "+i+": ");
			        		            							String a = sc.nextLine();
			        		            							art.add(a);
			        		            						}
			        		            						info += "Artistas = "+art+"}"; 
			        		            						break;
			        		            					case 3:
			        		            						System.out.print("Num Gr Musicais: ");
			        		            						int nGM = sc.nextInt();
			        		            						ArrayList<String> gm = new ArrayList<String>();
			        		            						for(int i=0; i<nGM; i++) {
			        		            							System.out.print("Grupo "+i+": ");
			        		            							String a = sc.nextLine();
			        		            							gm.add(a);
			        		            						}
			        		            						info += "Gr_Musicais = "+gm+"}";
			        		            						break;
			        		            					case 4:
			        		            						System.out.print("Historia: ");
			        		            						String auxHist = sc.nextLine();
			        		            						info += "historia = "+auxHist+"}";
			        		            						break;
			        		            				}
			        		            				System.out.println(server.Gerir("Musica", op3, info));

			        		            			}
			        		            			break;
			        		            		case 3:
			        		            			if(nome.equals("album")) {
				        		            			String titulo2 = "{titulo = ";
				        		            			System.out.print("Nome: ");
					        		            		titulo2 += sc.nextLine() + "}";
					        		            		System.out.println(server.Gerir("Album", op3, titulo2));
			        		            			} else if(nome.equals("musica")) {
			        		            				String titulo2 = "{titulo = ";
				        		            			System.out.print("Nome: ");
					        		            		titulo2 += sc.nextLine() + "}";
					        		            		System.out.println(server.Gerir("Musica", op3, titulo2));
			        		            			} else {
			        		            				System.out.println("Nao da\n");
			        		            			}
			        		            			break;
			        		            	}
			        			        	//System.out.println(Gerir());			        			        	
		        		            	} else {
		        		            		System.out.println("NAO É EDITOR\n(Nao pode realizar esta operacao)");
		        		            	}
		        		                break;
		        		            case 2:
		        		            	System.out.print("\tPESQUISAR MUSICA\n1 - Procurar por Album\n2 - Procurar diretamente\nOp: ");
		        		            	op = sc.nextInt();
		        		            	
		        		            	if(op == 1) {
		        		            		System.out.print("Nome do Album: "); //  ---------------------------------------------------------------------PROCURAR POR ALBUM OU DIRETAMENTE
			        		            	sc.nextLine();
			        		            	nome = sc.nextLine();		        		            	
			        		            	ArrayList<String> a = server.SearchSong(nome);
			        		    			System.out.println("Resultado de pesquisa:\n ");
			        		    			for(int i=1; i<=Integer.parseInt(a.get(0));i++) {
			        		    				System.out.println(i+" - "+a.get(i));
			        		    			}
			        		    			System.out.print("Op: ");
			        		    			auxOp = sc.nextInt();
			        		    			System.out.println(server.SearchSong2(a.get(auxOp)));
		        		            	} else if(op == 2) {
		        		            		System.out.print("Nome da Musica: "); //  ---------------------------------------------------------------------PROCURAR POR ALBUM OU DIRETAMENTE
			        		            	sc.nextLine();
			        		            	nome = sc.nextLine();
			        		            	System.out.println(server.SearchSong2(nome));
		        		            	}
		        		                break;    
		        		            case 3:	
		        		            	System.out.print("\tCONSULTAR DETALHES\n1 - Album\n2 - Artistas\nOp: ");
		        		            	op = sc.nextInt();
		        		            	if(op == 1) {
		        		            		System.out.println("Nome: ");
		        		            		sc.nextLine();
			        		            	nome = sc.nextLine();
			        		            	System.out.print(server.SearchDetails(1,nome));
		        		            	} else if(op == 2) {
		        		            		System.out.println("Nome: ");
		        		            		sc.nextLine();
			        		            	nome = sc.nextLine();
			        		            	System.out.print(server.SearchDetails(2,nome));
		        		            	} 
		        		                break;
		        		            case 4:	
		        		            	System.out.print("\tCRITICA A ALBUM\nNome: ");
		        		            	sc.nextLine();
		        		            	nome = sc.nextLine();		        		            	
		        		            	System.out.print("Pontuacao: ");
		        		            	double pontuacao = sc.nextDouble();
		        		            	sc.nextLine();
		        		            	System.out.print("Critica: ");
		        		            	String critica = sc.nextLine();
		        		            	System.out.println(server.AlbumCritic(nome, pontuacao, critica));
		        		                break;
		        		            case 5:	
		        		            	if(editor == 1) {
		        		            		System.out.print("\tATRIBUIR PRIVILEGIOS\nNome: ");		        		            		
			        		            	sc.nextLine();
			        		            	nome = sc.nextLine();		       		          	
			        		            	System.out.println(server.GivePrivilege(nome));
		        		            	} else {
		        		            		System.out.println("NAO É EDITOR\n(Nao pode realizar esta operacao)");
		        		            	}		        		            	
		        		                break;
		        		                
		        		            case 0:
		        		            	op=0;
	        		            		op2=0;
		        		            	break;
		        	            }
		        	            System.out.println("\n\nPress Any Key To Continue...");
		        	            new java.util.Scanner(System.in).nextLine();
		        	        }
		                } else {
		                	System.out.println(aux.get(0));
		                }
	            		
	                	break;
	            	case 2:
	            		System.out.println("Registar");
		                System.out.print("Insira o seu nome: ");
		                String nome2 = sc.next();
		                System.out.print("Insira a sua password: ");
		                String pass2 = sc.next();
		                System.out.println(server.Registar(nome2, pass2));
	            	case 3:
	                    System.out.println(server.getAllUsersNoPrivilege());
	            	case 4:
	                    System.out.println(server.getAlbuns());
	            	case 5:
	                    System.out.println(server.getAlbumName(2));
	        	}
	        }
		} catch (Exception e) {
			System.out.println("Exception in main: " + e);
			e.printStackTrace();
		}
	}
}

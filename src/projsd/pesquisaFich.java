package projsd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class pesquisaFich {
	
	public static ArrayList<Utilizador> listaUtilizadores = null;
    public static ArrayList<Musica> listaMusicas = null;
    public static ArrayList<Album> listaAlbuns = null; 
    
	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {
    
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

}

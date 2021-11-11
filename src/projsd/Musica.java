package projsd;

import java.io.Serializable;
import java.util.ArrayList;


public class Musica implements Serializable {
    protected String nome;
    protected ArrayList<String> artistas;
    protected ArrayList<String> gruposMusicais;
    protected ArrayList<Album> albuns;
    protected String historia;

    public Musica(String nome, ArrayList<String> artistas, ArrayList<String> gruposMusicais) {
        this.nome = nome;
        this.artistas = artistas;
        this.gruposMusicais = gruposMusicais;
        this.historia = "";
    }
    
    public Musica(String nome, ArrayList<String> artistas, ArrayList<String> gruposMusicais, ArrayList<Album> albuns) {
        this.nome = nome;
        this.artistas = artistas;
        this.gruposMusicais = gruposMusicais;
        this.albuns = albuns;
        this.historia = "";
    }
    
    public Musica(String nome, ArrayList<String> artistas, ArrayList<String> gruposMusicais, ArrayList<Album> albuns, String historia) {
        this.nome = nome;
        this.artistas = artistas;
        this.gruposMusicais = gruposMusicais;
        this.albuns = albuns;
        this.historia = historia;
    }
      
    
    

    /*--- GETTERS --- SETTERS --- ADDERS ---*/
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {    
        this.nome = nome;
    }

    public ArrayList<String> getArtistas() {
        return artistas;
    }

    public void setArtistas(ArrayList<String> artistas) {
        this.artistas = artistas;
    }

    public void addArtista(String artista){
        this.artistas.add(artista);
    }
    
    public ArrayList<String> getGruposMusicais() {
        return gruposMusicais;
    }

    public void setGruposMusicais(ArrayList<String> gruposMusicais) {
        this.gruposMusicais = gruposMusicais;
    }
    
    public void addGruposMusicais(String grupo){
        this.gruposMusicais.add(grupo);
    }
    
    public ArrayList<Album> getAlbuns() {
        return albuns;
    }

    public void setAlbuns(ArrayList<Album> albuns) {
        this.albuns = albuns;
    }

    public void addAlbum(Album a){
        this.albuns.add(a);
    }
    
    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }
   
    public String imprime(){
        return "NOME: "+nome+"\nARTISTAS: "+artistas+"\nGRUPOS MUSICAIS: "+gruposMusicais+"\nALBUNS: "+albuns+"\nHISTORIA: "+historia+"\n";
    }
    
    public String toString(){
        return this.getNome();
    }
    
}
package projsd;

import java.io.Serializable;
import java.util.ArrayList;


public class Utilizador implements Serializable {
    protected String nome;
    protected String pass;
    protected int editor;
    protected ArrayList<Musica> playlist;
    
    public Utilizador(String nome, String pass) {
        this.nome = nome;
        this.pass = pass;
    }

    public Utilizador(String nome, String pass, int editor) {
        this.nome = nome;
        this.pass = pass;
        this.editor = editor;
    }
    
    public Utilizador(String nome, String pass, int editor, ArrayList<Musica> playlist) {
        this.nome = nome;
        this.pass = pass;
        this.editor = editor;
        this.playlist = playlist;
    }

    
    /*--- GETTERS --- SETTERS --- ADDERS ---*/
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getEditor() {
        return editor;
    }

    public void setEditor(int editor) {
        this.editor = editor;
    }

    public ArrayList<Musica> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(ArrayList<Musica> playlist) {
        this.playlist = playlist;
    }
    
    public String toString(){
        return "NOME: "+this.getNome()+"\t PASSWORD: "+this.getPass()+"\t EDITOR: "+this.getEditor()+"\tPLAYLIST: "+this.getPlaylist();
    }
}

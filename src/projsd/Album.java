package projsd;


import java.io.Serializable;
import java.util.ArrayList;


public class Album implements Serializable {
    protected String titulo;
    protected ArrayList<Musica> musicas;
    protected ArrayList<String> criticas;
    protected String dataLancamento;
    protected ArrayList<String> generos; 
    protected double pontuacao;
    protected ArrayList<String> concertos;  

    public Album(String titulo, String dataLancamento, ArrayList<String> generos) {
        this.titulo = titulo;
        this.musicas = new ArrayList<Musica>();
        this.dataLancamento = dataLancamento;
        this.generos = generos;
    }
        
    public Album(String titulo, ArrayList<Musica> musicas, ArrayList<String> criticas, String dataLancamento, ArrayList<String> generos, double pontuacao, ArrayList<String> concertos){
        this.titulo = titulo;
        this.musicas = musicas;
        this.criticas = criticas;
        this.dataLancamento = dataLancamento;
        this.generos = generos; 
        this.pontuacao = pontuacao;
        this.concertos = concertos;
    }
	
    
    /*--- GETTERS --- SETTERS --- ADDERS ---*/
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public ArrayList<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(ArrayList<Musica> musicas) {
        this.musicas = musicas;
    }
    
    public void AddMusicas(Musica m){
        this.musicas.add(m);
    }

    public ArrayList<String> getCriticas() {
        return criticas;
    }

    public void setCriticas(ArrayList<String> criticas) {
        this.criticas = criticas;
    }
    
    public void AddCritica(String c){
        this.criticas.add(c);
    }
    
    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public ArrayList<String> getGeneros() {
        return generos;
    }

    public void setGeneros(ArrayList<String> generos) {
        this.generos = generos;
    }

    public void AddGenero(String g){
        this.generos.add(g);
    }
    
    public double getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(double pontuacao) {
        this.pontuacao = pontuacao;
    }

    public ArrayList<String> getConcertos() {
        return concertos;
    }

    public void setConcertos(ArrayList<String> concertos) {
        this.concertos = concertos;
    }
    
    public void AddConcerto(String con){
        this.concertos.add(con);
    }
    
    public int countCriticas(){
        return this.criticas.size();
    }
    
    public String imprime(){
        return "TITULO: "+titulo+"\nMUSICAS: "+musicas+"\nCRITICAS: "+criticas+"\nDATA LANCAMENTO: "+dataLancamento+"\nGENEREOS: "+generos+"\nPONTUACAO: "+pontuacao+"\nCONCERTOS: "+concertos;
    }
    
    public String toString(){
        return "TITULO: "+titulo;
    }
}


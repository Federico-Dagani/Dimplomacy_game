package it.unibs.arnaldo.Diplomacy;

import java.util.ArrayList;

public class Territorio {

    //attributi
    private String nome;
    private String tipo;
    private boolean occupato;
    private Unita unita;
    private ArrayList<String> territori_vicini;

    //costruttore
    public Territorio(String nome, String tipo, boolean occupato, Unita unita, ArrayList<String> territori_vicini) {
        this.nome = nome;
        this.tipo = tipo;
        this.occupato = occupato;
        this.unita = unita;
        this.territori_vicini = territori_vicini;
    }

    public Territorio(){ }

    //get e set
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isOccupato() {
        return occupato;
    }

    public void setOccupato(boolean occupato) {
        this.occupato = occupato;
    }

    public Unita getUnita() {
        return unita;
    }

    public void setUnita(Unita unita) {
        this.unita = unita;
    }

    public ArrayList<String> getTerritori_vicini() {
        return territori_vicini;
    }

    public void setTerritori_vicini(ArrayList<String> territori_vicini) {
        this.territori_vicini = territori_vicini;
    }

    public void setTerritorioVicino(String territorio_vicino){
        territori_vicini.add(territorio_vicino);
    }

    /**
     * <h3>Metodo che controlla se il terriorio passato è vicino al territorio su cui chiamoil metodo</h3>
     * @param territorio_da_confrontare
     * @return
     */
    public boolean seVicino(String territorio_da_confrontare){
        for (int i=0; i<territori_vicini.size(); i++){
            if(territori_vicini.get(i).equals(territorio_da_confrontare)){
                return true;
            }
        }
        return false;
    }
}

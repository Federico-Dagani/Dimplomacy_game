package it.unibs.arnaldo.Diplomacy;

import java.util.ArrayList;

public class Mappa {

    //attributi
    private ArrayList<Territorio> mappa_gioco;
    private String nome;

    //costruttore
    public Mappa(ArrayList<Territorio> mappa_gioco, String nome) {
        this.mappa_gioco = mappa_gioco;
        this.nome = nome;
    }

    public Mappa(){}

    //get e set
    public ArrayList<Territorio> getMappa_gioco() {
        return mappa_gioco;
    }

    public void setMappa_gioco(ArrayList<Territorio> mappa_gioco) {
        this.mappa_gioco = mappa_gioco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * <h3>Metodo che dato il nome del territorio, restituisce il corispondente oggetto con tutte le info</h3>
     * @param nome_territorio
     * @return l'oggetto Territorio
     */
    public Territorio getTerritorio(String nome_territorio){
        for (int i=0; i<mappa_gioco.size(); i++){
            if(mappa_gioco.get(i).getNome().equals(nome_territorio)){
                return mappa_gioco.get(i);
            }
        }
        return null;
    }
}

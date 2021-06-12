package it.unibs.arnaldo.Diplomacy;

public class Unita {

    //attributi
    private int giocatore; //per ora solo 1
    private String tipologia;

    //costruttore
    public Unita(int giocatore, String tipologia) {
        this.giocatore = giocatore;
        this.tipologia = tipologia;
    }

    //get e set
    public int getGiocatore() {
        return giocatore;
    }

    public void setGiocatore(int giocatore) {
        this.giocatore = giocatore;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }
}

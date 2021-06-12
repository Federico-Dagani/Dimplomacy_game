package it.unibs.arnaldo.Diplomacy;

import it.unibs.arnaldo.Diplomacy.mylib.ControlloDati;

public class Mossa {

    //attributi
    private String stringa_mossa;
    private String territorio_locale;
    private String territorio_spostamento;
    private String unita_agente;
    private boolean valida_sintatticamente;
    private String tipologia_mossa;

    //costruttore
    public Mossa(String stringa_mossa) {
        this.stringa_mossa = stringa_mossa;
        valida_sintatticamente = true;
        territorio_locale = new String();
        territorio_spostamento = new String();
        unita_agente = new String();
        valutaSintassiMossa();
    }

    //get e set
    public String getTerritorio_locale() {
        return territorio_locale;
    }

    public void setTerritorio_locale(String territorio_locale) {
        this.territorio_locale = territorio_locale;
    }

    public String getTerritorio_spostamento() {
        return territorio_spostamento;
    }

    public void setTerritorio_spostamento(String territorio_spostamento) {
        this.territorio_spostamento = territorio_spostamento;
    }

    public String getUnita_agente() {
        return unita_agente;
    }

    public void setUnita_agente(String tipologia_unita) {
        this.unita_agente = tipologia_unita;
    }

    public boolean getValida_sintatticamente() {
        return valida_sintatticamente;
    }

    public void setValida_sintatticamente(boolean valida_sintatticamente) {
        this.valida_sintatticamente = valida_sintatticamente;
    }

    public String getTipologia_mossa() {
        return tipologia_mossa;
    }

    public void setTipologia_mossa(String tipologia_mossa) {
        this.tipologia_mossa = tipologia_mossa;
    }

    /**
     * <h3>Metodo per una prima valutazione sintattica</h3>
     */
    public void valutaSintassiMossa(){
        char[] char_mossa = stringa_mossa.toCharArray();
        switch (char_mossa[0]){
            case 'A':
                controllaCoerenzaAzione(char_mossa);
                break;
            case 'F':
                controllaCoerenzaAzione(char_mossa);
                break;
            default: this.valida_sintatticamente = false;
        }
    }

    /**
     * <h3>Metodo che valuta se ogni carattere si trova al posto giusto</h3>
     * @param char_mossa ovvero un array di caratteri, rappresenta la mossa
     */
    private void controllaCoerenzaAzione(char[] char_mossa){
        if(char_mossa[0] == Costanti.ARMATA_CHAR.charAt(0)) this.unita_agente = Costanti.ARMATA_CHAR;
        if(char_mossa[0] == Costanti.FLOTTA_CHAR.charAt(0)) this.unita_agente = Costanti.FLOTTA_CHAR;
        //controllo primo spazio
        if(char_mossa[1] != ' ') this.valida_sintatticamente = false;
        //controllo primo territorio
        char[] char_territorio_locale = new char[3];
        int c=0;
        for (int i=2; i<5; i++){
            if(ControlloDati.seConsonante(char_mossa[i]) || ControlloDati.seVocale(char_mossa[i])){
                char_territorio_locale[c] = char_mossa[i];
                c++;
            }else {
                this.valida_sintatticamente = false;
            }
        }
        //se per ora è valida la aggiungo
        if(valida_sintatticamente) territorio_locale = new String(char_territorio_locale);
        // contollo spazio dopo
        if(char_mossa[5] != ' ') this.valida_sintatticamente = false;
        //eventuale secondo terriorio
        char[] char_territorio_move = new char[3];
        //controllo se vuole fare l'hold oppure move
        if(char_mossa[6] == Costanti.HOLD_CHAR.charAt(0)) {
            tipologia_mossa = Costanti.HOLD_CHAR;
        }else{
            //controllo territorio dove vuole muoversi
            c=0;
            for (int i=6; i<9; i++){
                if(!ControlloDati.seConsonante(char_mossa[i]) || !ControlloDati.seVocale(char_mossa[i])){
                    char_territorio_move[c] = char_mossa[i];
                    c++;
                }else {
                    this.valida_sintatticamente = false;
                }
                //arrivato correttamente a fine territorio e prima era corretta
                if(c==2 && valida_sintatticamente==true){
                    tipologia_mossa = Costanti.MOVE_CHAR;
                }
            }
            //se per ora è valida la aggiungo
            if(valida_sintatticamente) this.territorio_spostamento = new String(char_territorio_move);
        }
    }

    @Override
    public String toString() {
        return "Mossa{" +
                "comando='" + stringa_mossa + '\'' +
                '}';
    }
}

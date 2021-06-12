package it.unibs.arnaldo.Diplomacy;

import java.util.ArrayList;

//dedico una classe apposita per la valutazione della mossa
public class ValutaMossa {

    //metodo per la valutazione
    public static ArrayList<Mossa> valuta(ArrayList<Mossa> mosse, Mappa mappa_gioco){
        //rimozione mosse sintatticamente scorrette
        rimozioneErrate(mosse);
        //tolgo le mosse che vogliono spostare unita da un campo all'altro senza essere occupate
        togliMovimentiSenzaOccupazione(mosse, mappa_gioco);
        //valuta se le armate si muovono verso la terra o costa
        //valuta se una flotta non va sulla terra
        valutaDirezioneCorretta(mosse, mappa_gioco);
        //confronto se 2 territori sono vicini per lo spostamento
        seVicini(mosse, mappa_gioco);
        //controllo se 2 unita vanno verso lo stesso territorio
        seVersoStessoTerritorio(mosse);
        //controllo se si scambiano di posto
        seScambianoPosto(mosse);
        //controllo che si spostino in un territorio libero, o che il territorio di destinazione si sta spostando le sue truppe
        seSpostamentoCorretto(mosse, mappa_gioco);
        return mosse;
    }

    /**
     * <h3>Meotodo che controlla se l'unità si sta muovendo verso un territorio coerente con la tipologia di unità</h3>
     * @param mosse ovvero un ArrayList di mosse
     * @param mappa_gioco Overro una mappa(nome e territori)
     */
    private static void valutaDirezioneCorretta(ArrayList<Mossa> mosse, Mappa mappa_gioco){
        for (int i=0; i<mosse.size(); i++){
            //controllo che solo le armate stiano sulla terra
            if(mosse.get(i).getUnita_agente().equals(Costanti.ARMATA_CHAR)){
                //se un armata non va verso la terra o costa
                if(valutaSeDi(mosse.get(i).getTerritorio_spostamento(), mappa_gioco.getMappa_gioco(), Costanti.MARE)){
                    mosse.remove(i);
                }
            }
            if (mosse.get(i).getUnita_agente().equals(Costanti.FLOTTA_CHAR)){
                if(valutaSeDi(mosse.get(i).getTerritorio_spostamento(), mappa_gioco.getMappa_gioco(), Costanti.TERRA)){
                    mosse.remove(i);
                }
            }
        }
    }

    /**
     * <h3>Metodo che valuta se il territorio inserito corrisponde al tipo specificato</h3>
     * @param territorio ovvero il suo nome
     * @param mappa_gioco
     * @param tipo_luogo ovvero se è land, water or coast
     * @return
     */
    private static boolean valutaSeDi(String territorio, ArrayList<Territorio> mappa_gioco, String tipo_luogo){
        //scorro i territori
        for (Territorio territorio_da_analizzare : mappa_gioco){
            //guardo se lo trovo
            if(territorio_da_analizzare.getNome().equals(territorio)){
                if(territorio_da_analizzare.getTipo().equals(tipo_luogo)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <h3>Meotodo che controlla se i territori nelle mosse sono vicini</h3>
     * @param mosse ovvero un ArrayList di mosse
     * @param mappa_gioco
     */
    private static void seVicini(ArrayList<Mossa> mosse, Mappa mappa_gioco){
        for (int i=0; i<mosse.size(); i++){
            Territorio territorio_locale = mappa_gioco.getTerritorio(mosse.get(i).getTerritorio_locale());
            if(mosse.get(i).getTipologia_mossa().equals(Costanti.MOVE_CHAR) ){
                //recupero il territorio che corrisponde al nome
                territorio_locale = mappa_gioco.getTerritorio(mosse.get(i).getTerritorio_locale());
                //se 2 territori non sono vicini
                if(!territorio_locale.seVicino(mosse.get(i).getTerritorio_spostamento())) {
                    mosse.remove(i);
                }
            }
        }
    }

    /**
     *<h3>Metodo che controlla se 2 o più mosse si dirigono verso lo stesso territorio</h3>
     * @param mosse ArrayList di Mosse
     */
    private static void seVersoStessoTerritorio(ArrayList<Mossa> mosse){
        for (int i=0; i<mosse.size(); i++){
            ArrayList<Mossa> mosse_non_valide = new ArrayList<>();
            //se trovo un movimento
            if(mosse.get(i).getTipologia_mossa().equals(Costanti.MOVE_CHAR)){
                //salvo dove va quel movimento
                String verso_territorio = mosse.get(i).getTerritorio_spostamento();
                //faccio scorrere le restanti mosse per capire se altri vanno verso quel territorio
                for (int j=i+1; j<mosse.size(); j++){
                    if(mosse.get(j).getTerritorio_spostamento().equals(verso_territorio)){
                        mosse_non_valide.add(mosse.get(j));
                    }
                }
                //se l'array non è vuoto, aggiungo la mossa iniziale, che non fa ancora parte di quelle non valide
                if(!mosse_non_valide.isEmpty()){
                    mosse_non_valide.add(mosse.get(i));
                }
                //elimino tutte dall'array di mosse
                for (int k=0; k<mosse_non_valide.size(); k++){
                    mosse.remove(mosse_non_valide.get(k));
                }
            }
        }
    }

    /**
     * <h3>Metodo che controlla se 2 territori si stanno scambiando unità reciprocamente</h3>
     * @param mosse ArrayList di mosse
     */
    private static void seScambianoPosto(ArrayList<Mossa> mosse){
        for (int i=0; i<mosse.size(); i++){
            //se trovo una mossa di movimento
            if(mosse.get(i).getTipologia_mossa().equals(Costanti.MOVE_CHAR)){
                //salvo il territorio
                String verso_territorio = mosse.get(i).getTerritorio_spostamento();
                for (int j=0; j< mosse.size(); j++){
                    //se la j-esima mossa ha come territorio locale il territorio dove la i-esima mossa voleva andare
                    if (mosse.get(j).getTerritorio_locale().equals(verso_territorio)){
                        String verso_territorio_altro = mosse.get(j).getTerritorio_spostamento();
                        //guardo se il luogo di arrivo della j-esima mossa va al luogo di partenza della i-esima
                        if(mosse.get(i).getTerritorio_locale().equals(verso_territorio_altro)){
                            //si stanno scambiando dunque le rimuovo
                            mosse.remove(j);
                            mosse.remove(i);
                        }
                    }
                }
            }
        }
    }

    /**
     * <h3>Metodo che controlla le mosse dove si vuole uno spostamento di unità senza effettivamente averle presenti sul territorio</h3>
     * @param mosse
     * @param mappa_gioco
     */
    private static void togliMovimentiSenzaOccupazione(ArrayList<Mossa> mosse, Mappa mappa_gioco){
        Territorio territorio_locale;
        for(int i=0; i<mosse.size(); i++){
            territorio_locale = mappa_gioco.getTerritorio(mosse.get(i).getTerritorio_locale());
            //guardo se il territorio di PARTENZA della mossa non è occupato
            if(mosse.get(i).getTipologia_mossa().equals(Costanti.MOVE_CHAR)){
                if(!territorio_locale.isOccupato()) mosse.remove(i);
            }
        }
    }

    /**
     * <h3>Metodo che rimuove le mosse errate sintatticamente</h3>
     * @param mosse
     */
    private static void rimozioneErrate(ArrayList<Mossa> mosse){
        for(Mossa mossa : mosse){
            if(!mossa.getValida_sintatticamente()){
                mosse.remove(mossa);
            }
        }
    }

    /**
     * <h3>Metodo che guarda se avviene una catena consecutiva di spostamenti(es: land1 vuole andare in land2, e land2 vuole andare in land3...)</h3>
     * @param mosse
     * @param mappa_gioco
     */
    private static void seSpostamentoCorretto(ArrayList<Mossa> mosse, Mappa mappa_gioco){
        for (int i=0; i<mosse.size(); i++){
            Territorio verso_territorio = mappa_gioco.getTerritorio(mosse.get(i).getTerritorio_spostamento());
            if(controllaSpostamento(verso_territorio, mosse, mappa_gioco));
        }
    }

    /**
     * <h3>Metodo che controlla tramite ricorsione se il luogo dove la mossa vuole andare è occupato o si sposta a sua volta</h3>
     * @param verso_territorio ovvero il territorio dove la mossa vuole spostare l'unità
     * @param mosse
     * @param mappa_gioco
     * @return booleano rappresentante se la eventuale catena di mosse può avvenire
     */
    private static boolean controllaSpostamento(Territorio verso_territorio, ArrayList<Mossa> mosse, Mappa mappa_gioco){
        for (int i=0; i<mosse.size(); i++){
            //controllo se la i-esima mossa (ovvero quella del territorio dove voglio andare) ha come luogo di partenza il mio luogo di arrivo
            if(mosse.get(i).getTerritorio_locale().equals(verso_territorio.getNome())){
                //guardo se la i-esima mossa ne fa un'altra a sua volta o no
                if(mosse.get(i).getTipologia_mossa().equals(Costanti.HOLD_CHAR)){
                    if(verso_territorio.isOccupato()){
                        return false;
                    }else return true;
                }else if(mosse.get(i).getTipologia_mossa().equals(Costanti.MOVE_CHAR)){
                    //se si sposta anche il luogo dove andare, rieseguo il metodo cambiando i parametri di una passaggio in avanti
                    String nuovo_territorio_nome = mosse.get(i).getTerritorio_spostamento();
                    Territorio nuovo_terrtitorio = mappa_gioco.getTerritorio(nuovo_territorio_nome);
                    return controllaSpostamento(nuovo_terrtitorio, mosse, mappa_gioco);
                }
            }
        }
        //se non trova più mosse con il luogo dove voglio andare, allora la esegue
        return true;
    }

}

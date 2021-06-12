package it.unibs.arnaldo.Diplomacy;

import it.unibs.arnaldo.Diplomacy.ServiziFileXML.LetturaXML;
import it.unibs.arnaldo.Diplomacy.mylib.InputDati;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Mappa mappa_gioco = LetturaXML.leggiMappa(Costanti.DIR_STANDARD_MAP);
        ArrayList<Mossa> mosse = new ArrayList<>();
        do{
            //inserimento mosse
            do{
                Scanner in = new Scanner(System.in);
                System.out.println(Costanti.INS_MOSSA);
                String mossa_stringa;
                mossa_stringa = in.nextLine();
                Mossa mossa = new Mossa(mossa_stringa);
                if(mossa.getValida_sintatticamente()) mosse.add(mossa);
            }while (InputDati.yesOrNo(Costanti.DOMANDA_NEW_MOSSA));
            //valuto le mosse, scremando quelle non valide
            mosse = ValutaMossa.valuta(mosse, mappa_gioco);
            //eseguo quelle valide
            eseguiMosse(mosse, mappa_gioco);
        }while (InputDati.yesOrNo(Costanti.NUOVO_TURNO));
        System.out.println(Costanti.END);
    }

    /**
     * <h3>Metodo che esegue le mosse(valide) inserite, andando a cambiare gli stati dei territori(es: occupato o no)</h3>
     * @param mosse
     * @param mappa_gioco
     */
    private static void eseguiMosse(ArrayList<Mossa> mosse, Mappa mappa_gioco){
        //le faccio eseguire dall'ultima per evitare problemi in spostamenti consecutivi(es: land1 vuole andare in land2, e land2 vuole andare in land3...)
        for (int i=mosse.size()-1; i>=0; i--){
            Mossa mossa = mosse.get(i);
            if(mossa.getTipologia_mossa().equals(Costanti.MOVE_CHAR)){
                // salvo il suo territorio da dove la mossas vuole spostar via l'unità
                Territorio terriorio_prima = mappa_gioco.getTerritorio(mossa.getTerritorio_locale());
                // e salvo il territorio dove vuole posizionare l'unità
                Territorio territorio_dopo = mappa_gioco.getTerritorio(mossa.getTerritorio_spostamento());
                for (Territorio territorio_in_mappa : mappa_gioco.getMappa_gioco()){
                    //cambio i loro stati di occupazione
                    if(territorio_in_mappa.equals(terriorio_prima)) territorio_in_mappa.setOccupato(false);
                    if(territorio_in_mappa.equals(territorio_dopo)) {
                        territorio_in_mappa.setOccupato(true);
                        territorio_in_mappa.setUnita(terriorio_prima.getUnita());
                    }
                }
                //comunico di aver eseguito la mossa
                System.out.printf(Costanti.EXE_MOSSA, mossa.toString());

            }
        }
    }
}

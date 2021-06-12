package it.unibs.arnaldo.Diplomacy;

public class Costanti {

    //directory file xml
    public static final String DIR_STANDARD_MAP = "src/it/unibs/arnaldo/Diplomacy/standard_map.xml";
    //tipologie unità
    public static final String ARMATA_CHAR = "A";
    public static final String FLOTTA_CHAR = "F";
    //tipologie azioni
    public static final String HOLD_CHAR = "H";
    public static final String SUPPORTO_CHAR = "S";
    public static final String CONVOGLI_CHAR = "C";
    public static final String MOVE_CHAR = "M";
    //tipologia territorio
    public static final String MARE = "water";
    public static final String TERRA = "land";
    public static final String COSTA = "coast";
    //inserimento utente
    public static final String INS_MOSSA = "Inserisci la mossa da giocare: ";
    //richieste utente
    public static final String DOMANDA_NEW_MOSSA = "Vuoi inserire una nuova mossa? ";
    public static final String NUOVO_TURNO = "Vuoi fare un altro turno di inserimento mosse?";
    //errori vari nei servizi xml
    public static final String ERRORE_INIZIALIZZAZIONE_READER = "Errore nell'inizializzazione del reader: ";
    public static final String ERRORE_LETTURA_FILE = "Errore nella lettura del file: %s, per ulteriori info: %s\n";
    //comunicazioni di inzio/fine lettura/scrittura file
    public static final String INIZIO_FILE = "\nInizio a %s il file: %s ...\n";
    public static final String FINE_FILE = "\nFine della %s del file :)\n";
    public static final String LETTURA = "lettura";
    public static final String SCRITTURA = "scrittura";
    //tag xml
    public static final String NATION = "nation";
    public static final String MAP = "map";
    //attribute xml
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String CENTER = "center";
    public static final String NEIGHBOUR = "neighbour";
    public static final String UNIT = "unit";
    //comunicazioni x utente
    public static final String EXE_MOSSA = "Eseguita mossa: %s\n";
    //fine
    public static final String END = "Fine del programma";
}

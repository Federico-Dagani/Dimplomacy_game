package it.unibs.arnaldo.Diplomacy.ServiziFileXML;

import it.unibs.arnaldo.Diplomacy.Costanti;
import it.unibs.arnaldo.Diplomacy.Mappa;
import it.unibs.arnaldo.Diplomacy.Territorio;
import it.unibs.arnaldo.Diplomacy.Unita;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.util.ArrayList;

public class LetturaXML {

    public static Mappa leggiMappa(String filename){
        XMLInputFactory xmlif;
        XMLStreamReader xmlreader = null;

        Mappa mappa_gioco = new Mappa();
        ArrayList<String> territori_vicini= new ArrayList<>();
        ArrayList<Territorio> territori= new ArrayList<>();
        Territorio nuovo_territorio = new Territorio();

        //try catch per gestire eventuali eccezioni durante l'inizializzazione
        try{
            xmlif=XMLInputFactory.newInstance();
            xmlreader= xmlif.createXMLStreamReader(filename, new FileInputStream(filename));
        }catch(Exception e){
            System.out.println(Costanti.ERRORE_INIZIALIZZAZIONE_READER);
            System.out.println(e.getMessage());
        }

        try{
            while (xmlreader.hasNext()){

                switch (xmlreader.getEventType()){

                    //evento inzio lettura documento
                    case XMLStreamConstants.START_DOCUMENT:
                        System.out.printf(Costanti.INIZIO_FILE, Costanti.LETTURA, filename);
                    break;

                    case XMLStreamConstants.START_ELEMENT:
                        switch (xmlreader.getLocalName()){

                            case Costanti.MAP:
                                mappa_gioco.setNome(xmlreader.getAttributeValue(0));
                            break;

                            case Costanti.NATION:
                                nuovo_territorio = new Territorio();
                                for (int i=0; i<xmlreader.getAttributeCount(); i++){
                                    switch (xmlreader.getAttributeLocalName(i)){
                                        case Costanti.NAME:
                                            nuovo_territorio.setNome(xmlreader.getAttributeValue(i));
                                            break;
                                        case Costanti.TYPE:
                                            nuovo_territorio.setTipo(xmlreader.getAttributeValue(i));
                                            break;
                                        case Costanti.UNIT:
                                            nuovo_territorio.setOccupato(true);
                                            Unita unita_combattimento = new Unita(1, xmlreader.getAttributeValue(i));
                                        break;
                                    }
                                }
                            break;

                            case Costanti.NEIGHBOUR:
                                territori_vicini.add(xmlreader.getAttributeValue(0));
                            break;
                        }
                    break;

                    case XMLStreamConstants.END_ELEMENT:
                        if(xmlreader.getLocalName().equals(Costanti.NATION)){
                            nuovo_territorio.setTerritori_vicini(territori_vicini);
                            territori.add(nuovo_territorio);
                        }
                    break;
                }
                xmlreader.next();
            }
            System.out.printf(Costanti.FINE_FILE, Costanti.LETTURA);
        } catch (XMLStreamException e) {
            System.out.printf(Costanti.ERRORE_LETTURA_FILE, filename, e.getMessage());
        }
        mappa_gioco.setMappa_gioco(territori);
        return mappa_gioco;
    }
}

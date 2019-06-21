package it.unibs.PgArnaldo;

import java.io.*;
import java.util.*;

import javax.xml.stream.*;
import it.unibs.fp.mylib.*;

public class UnipolyMain
{

	public final static String MESS_INIZIO = "INIZIO PARTITA";
	public final static String ERRORE_SELEZ = "Attenzione selezione non valida";

	public final static String DOMANDA_NOME = "Inserisci il nome del giocatore:";
	public final static String SPIEGAZIONE2 = " I€€€ come patrimonio iniziale\n"
			+ "Il tuo obiettivo e' quello di accumulare un patrimonio di 1.000.000 I€€€\n"
			+ "La partita finisce nel momento in cui finisci il denaro a tua disposizione\n" + "Buoona Fortuna!!!\n";
	public final static String SPIEGAZIONE1 = " Ti verranno assegnati ";
	public final static int PATRIMONIO_INIZIALE = 100000;

	/**
	 * Main con il menu per la scelta se continuare a giocare
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		benvenuto();

		boolean fine = false;
		do
		{
			int selezione = InputDati.leggiIntero(
					"VUOI INIZIARE UNA NUOVA PARTITA??\n1\tGioca una partita in giocatore singolo" + "\n0\tNO\n\n", 0,
					1);
			switch (selezione)
			{
			case 1:
				play();
				break;
			case 0:
				fine = true;
				break;
			}

		} while (!fine);
	}

	/**
	 * abbastanza inutile
	 */
	private static void benvenuto()
	{
		System.out.println("BENVENUTO AL GIOCO DELL'UNIPOLY!!!");
	}

	/**
	 * inizializza le varie variabili, quindi il giocatore, la mappa che si
	 * utilizza, facendotela scegliere, ti spiega il gioco e fa partire una partita
	 */
	private static void play()
	{
		System.out.println(MESS_INIZIO);
		String nomeUtente = InputDati.leggiStringaNonVuota(DOMANDA_NOME);
		String mappaScelta = sceltamappa(nomeUtente);
		Mappa mappaGioco = creaMappa(mappaScelta);
		mappaGioco.mostra();
		System.out.println(nomeUtente + SPIEGAZIONE1 + PATRIMONIO_INIZIALE + SPIEGAZIONE2);
		PremiInvio.premiInvio();
		Giocatore player = new Giocatore(nomeUtente, PATRIMONIO_INIZIALE, 0);
		Partita p = new Partita(player, mappaGioco);
		p.esegui();

	}

	/**
	 * crea la mappa a partire dal nome della mappa nella cartella mappe 
	 * in pratica per ogni casella ne memorizza i contenuti e solo quando viene chiuso il tag casella viene creata la vera e propria casella
	 * avremo un arraylist che memorizzera tutte le caselle (il loro contenuto) e diverse mappe (o singole istanze per le caselle uniche)
	 * che memorizzano l'id associato all'interno del primo arraylist (che corrisponde al loro vero id) come chiave, mentre la casella specifica come elemento
	 * alla fine di tutto ritorna la mappa con tutti le mappe
	 * @param mappaScelta
	 * @return la mappa che verra usata per inizializzare la partita
	 */
	private static Mappa creaMappa(String mappaScelta)
	{
		// LETTURA FILE XML

		ArrayList<String> tipiCaselle = new ArrayList<String>();
		Casella casellaIniziale = null;
		CasellaPrigione prigione = null;
		CasellaTicket ticket = null;
		HashMap<Integer, CasellaStazione> caselleStazione = new HashMap<Integer, CasellaStazione>();
		HashMap<Integer, CasellaProbabilita> caselleProbabilita = new HashMap<Integer, CasellaProbabilita>();
		HashMap<Integer, CasellaImprevisto> caselleImprevisto = new HashMap<Integer, CasellaImprevisto>();
		HashMap<Integer, CasellaProprieta> caselleProprieta = new HashMap<Integer, CasellaProprieta>();
		XMLInputFactory xmlif = null;
		XMLStreamReader xmlr = null;
		int idAttuale = 0;
		String nomeAttuale = null;
		String tipoAttuale = null;
		String messaggioAttuale = null;
		String messaggioParziale = "";
		int valoreAttuale = 0;
		int costoBase = 0;
		int costoCasa = 0;
		int costoHotel = 0;
		int guadagnoBase = 0;
		int guadagnoCasa = 0;
		int guadagnoHotel = 0;
		try
		{
			xmlif = XMLInputFactory.newInstance();
			xmlr = xmlif.createXMLStreamReader("mappe//" + mappaScelta, new FileInputStream("mappe//" + mappaScelta));
		}
		catch (Exception e)
		{
			System.out.println("Errore nell'inizializzazione del reader:");
			System.out.println(e.getMessage());
		}
		try
		{
			while (xmlr.hasNext())
			{

				switch (xmlr.getEventType())
				{
				case XMLStreamConstants.START_ELEMENT: // inizio di un elemento
					if (xmlr.getLocalName().equals("cell"))
					{
						idAttuale = Integer.parseInt(xmlr.getAttributeValue(0));
						nomeAttuale = xmlr.getAttributeValue(1);
						tipoAttuale = xmlr.getAttributeValue(2);
					}
					if (xmlr.getLocalName().equals("costs"))
					{
						costoBase = Integer.parseInt(xmlr.getAttributeValue(0));
						costoCasa = Integer.parseInt(xmlr.getAttributeValue(1));
						costoHotel = Integer.parseInt(xmlr.getAttributeValue(2));
					}
					if (xmlr.getLocalName().equals("earnings"))
					{
						guadagnoBase = Integer.parseInt(xmlr.getAttributeValue(0));
						guadagnoCasa = Integer.parseInt(xmlr.getAttributeValue(1));
						guadagnoHotel = Integer.parseInt(xmlr.getAttributeValue(2));
					}
					if (xmlr.getLocalName().equals("amount"))
						valoreAttuale = Integer.parseInt(xmlr.getAttributeValue(0));
					if (xmlr.getLocalName().equals("line"))
					{
						messaggioParziale = messaggioParziale + xmlr.getAttributeValue(0) + "\n";
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
					if (xmlr.getLocalName().equals("message"))
					{
						messaggioAttuale = messaggioParziale;
						messaggioParziale = "";
					}
					if (xmlr.getLocalName().equals("cell"))
					{
						switch (tipoAttuale)
						{
						case "proprieta":
							CasellaProprieta proprieta = new CasellaProprieta(idAttuale, nomeAttuale, costoBase,
									costoCasa, costoHotel, guadagnoBase, guadagnoCasa, guadagnoHotel);
							tipiCaselle.add(tipoAttuale);
							caselleProprieta.put(idAttuale, proprieta);
							break;
						case "prigione":
							prigione = new CasellaPrigione(idAttuale, nomeAttuale, valoreAttuale);
							tipiCaselle.add(tipoAttuale);
							break;
						case "ticket":
							ticket = new CasellaTicket(idAttuale, nomeAttuale);
							tipiCaselle.add(tipoAttuale);
							break;
						case "iniziale":
							casellaIniziale = new Casella(idAttuale, nomeAttuale);
							tipiCaselle.add(tipoAttuale);
							break;
						case "stazione":
							CasellaStazione casellaStaz = new CasellaStazione(idAttuale, nomeAttuale);
							tipiCaselle.add(tipoAttuale);
							caselleStazione.put(idAttuale, casellaStaz);
							break;
						case "probabilita":
							CasellaProbabilita casellaProb = new CasellaProbabilita(idAttuale, nomeAttuale,
									messaggioAttuale, valoreAttuale);
							caselleProbabilita.put(idAttuale, casellaProb);
							tipiCaselle.add(tipoAttuale);
							break;
						case "imprevisto":
							CasellaImprevisto casellaImp = new CasellaImprevisto(idAttuale, nomeAttuale,
									messaggioAttuale, valoreAttuale);
							caselleImprevisto.put(idAttuale, casellaImp);
							tipiCaselle.add(tipoAttuale);
							break;

						}

					}

				}
				xmlr.next();
			}
		}

		catch (Exception e)
		{
			System.out.println("Errore nella lettura da file");
			System.out.println(e.getMessage());
		}
		return new Mappa(tipiCaselle, caselleStazione, caselleProbabilita, caselleImprevisto, casellaIniziale, prigione,
				ticket, caselleProprieta);
	}

	/**
	 * ti fa scegliere le mappe da quelle presenti nella cartella mappe ti presenta
	 * la possibilita di selezionarla per nome, o te le fa visualizzare tutte (ti fa
	 * scegliere se farlo o meno in ordine alfabetico) e ti lascia scegliere
	 * 
	 * @param nome del giocatore
	 * @return restituisce il nome della mappa nella cartella mappe
	 */
	private static String sceltamappa(String nome)
	{

		ArrayList<String> nomiMappe = new ArrayList<String>();
		boolean trovato = false;
		String mappaScelta = null;
		final File folder = new File("mappe");
		for (final File fileEntry : folder.listFiles())
		{
			nomiMappe.add(fileEntry.getName());
		}
		while (!trovato)
		{
			System.out.println(nome + " Scegli la mappa che vuoi utilizzare per il gioco\n"
					+ "Vuoi cercarla per nome(S) o visualizzarle tutte e sceglierle(N)?\n");
			if (InputDati.yesOrNo(""))
			{
				mappaScelta = InputDati.leggiStringaNonVuota("Inserisci il nome della mappa\n");
				for (String nomeMappa : nomiMappe)
				{
					if (nomeMappa.equals(mappaScelta))
					{
						trovato = true;
						System.out.println("Mappa trovata!!");
					}
				}
			}
			else
			{
				System.out
						.println("Vuoi visualizzarle in ordine casuale(S) o visualizzarle in ordine alfabetico(N)?\n");
				if (InputDati.yesOrNo(""))
				{
					System.out.println("Inserisci il numero della tua mappa\n");
					for (int i = 0; i < nomiMappe.size(); i++)
					{
						System.out.println((i + 1) + "\t\t" + nomiMappe.get(i));
					}

					mappaScelta = nomiMappe.get(InputDati.leggiIntero("", 1, nomiMappe.size()) - 1);
					trovato = true;
				}
				else
				{
					Collections.sort(nomiMappe);
					System.out.println("Inserisci il numero della tua mappa\n");
					for (int i = 0; i < nomiMappe.size(); i++)
					{
						System.out.println((i + 1) + "\t\t" + nomiMappe.get(i));
					}

					mappaScelta = nomiMappe.get(InputDati.leggiIntero("", 1, nomiMappe.size()) - 1);
					trovato = true;
				}
			}

		}
		return mappaScelta;
	}

}

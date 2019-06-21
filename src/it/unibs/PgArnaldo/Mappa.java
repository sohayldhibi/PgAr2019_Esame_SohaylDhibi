package it.unibs.PgArnaldo;

import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.PremiInvio;

public class Mappa
{
	private ArrayList<String> tipiCaselle;
	private Casella iniziale;
	private CasellaPrigione prigione;
	private CasellaTicket ticket;
	private HashMap<Integer, CasellaStazione> caselleStazione;

	private HashMap<Integer, CasellaProprieta> caselleProprieta;
	private HashMap<Integer, CasellaProbabilita> caselleProbabilita;
	private HashMap<Integer, CasellaImprevisto> caselleImprevisto;
	public Mappa(ArrayList<String> tipiCaselle, HashMap<Integer, CasellaStazione> caselleStazione,
			HashMap<Integer, CasellaProbabilita> caselleProbabilita,
			HashMap<Integer, CasellaImprevisto> caselleImprevisto, Casella iniziale, CasellaPrigione prigione,
			CasellaTicket ticket, HashMap<Integer, CasellaProprieta> caselleProprieta)
	{
		super();
		this.tipiCaselle = tipiCaselle;
		this.caselleStazione = caselleStazione;
		this.caselleProbabilita = caselleProbabilita;
		this.caselleImprevisto = caselleImprevisto;
		this.iniziale = iniziale;
		this.prigione=prigione;
		this.ticket=ticket;
		this.caselleProprieta=caselleProprieta;
	}

	/**
	 * ti visualizza la mappa (solo le proprieta del percrso, e non i "numeri"
	 */
	public void mostra()
	{
		for (int i = 0; i < tipiCaselle.size(); i++)
		{
			System.out.println((i + 1) + "\t\t" + tipiCaselle.get(i));
		}
		System.out.println("\n");
	}

	/**
	 * Sulla base del lancio ei dadi ottenuto il gicatore muove la pedina e attiva l'effetto della casella su cui e' arrivato
	 * @param spostaCaselle risultato dei dadi
	 * @param player per modificare i suoi parametri in base all'effetto della casella
	 */
	public void muovi(int spostaCaselle, Giocatore player)
	{
		player.setIdCasellaAttuale((player.getIdCasellaAttuale() + spostaCaselle) % tipiCaselle.size());
		switch (tipiCaselle.get(player.getIdCasellaAttuale()))
		{
		case "iniziale":
			iniziale.effetto(player);
			break;
		case "stazione":
			caselleStazione.get(player.getIdCasellaAttuale()).effetto(player);
			scegliProssimaStazione(player);
			break;
		case "probabilita":

			caselleProbabilita.get(player.getIdCasellaAttuale()).effetto(player);
			break;
		case "imprevisto":

			caselleImprevisto.get(player.getIdCasellaAttuale()).effetto(player);
			break;
		case "prigione":
			prigione.effetto(player);
			break;
		case "ticket":
			ticket.effetto(player);
			break;
		case "proprieta":
			caselleProprieta.get(player.getIdCasellaAttuale()).effetto(player);
			break;
		}
		PremiInvio.premiInvio();
	}
/**
 * in caso si finisce su di una casella stazione si avrà la possibilità di scegliere un'altra stazione su cui andare;
 * ho fatto in modo di non permettere la visualizzazione e la scelta della stazione su cui il giocatore gia si trova
 * il metodo per scegliere e quello per spostarsi sono grossomodo gli stessi
 * @param player
 */
	private void scegliProssimaStazione(Giocatore player)
	{
		int i = 0;
		System.out.println("Scegli la stazione in cui vuoi essere trasportato:\n");
		for (Integer key : caselleStazione.keySet())
		{
			if (player.getIdCasellaAttuale() != caselleStazione.get(key).getId())
				System.out.println((i + 1) + "\t\t" + caselleStazione.get(key).getNome() + ", numero= "
						+ (caselleStazione.get(key).getId() + 1));
			else
				i--;
			i++;
		}
		i = 0;
		int scelta = InputDati.leggiIntero("\n", 1, caselleStazione.size() - 1);
		for (Integer key : caselleStazione.keySet())
		{
			if (player.getIdCasellaAttuale() == caselleStazione.get(key).getId())
				i--;
			i++;
			if (i == scelta)
			{
				player.setIdCasellaAttuale(caselleStazione.get(key).getId());
				break;
			}
		}
	}
}

package it.unibs.PgArnaldo;

import it.unibs.fp.mylib.EstrazioniCasuali;

public class Partita
{

	private final static String MESS_FINITI = "GAME OVER";

	private Mappa mappa;
	private Giocatore player;

	public Partita(Giocatore player, Mappa mappa)
	{
		super();
		this.mappa = mappa;
		this.player = player;
	}

	/**
	 * ad ogni turno (a meno che li debba saltare, possibilita' data dalla prigione)
	 * si ha che il giocatore lancia i due dadi e poi si sposta in base alla somma
	 * che ha ottenuto
	 */
	private void giocaUnTurno()
	{
		int mossa;
		System.out.println(player);
		if (player.getTurniInPrigione() == 0)
		{
			System.out.println("LANCIO DEI DADI");
			lancioDeiDadi();
			int primoDado = EstrazioniCasuali.estraiIntero(1, 6);
			System.out.print(primoDado + "   ");
			int secondoDado = EstrazioniCasuali.estraiIntero(1, 6);
			System.out.println(secondoDado);
			mossa = primoDado + secondoDado;
			System.out.println("TI MUOVERAI DI " + mossa + " CASELLE");
		}
		else
		{
			mossa = 0;
		}
		mappa.muovi(mossa, player);

	}
/**
 * per alleggerire il gioco metto dei delay fra un lancio e l'altro
 */
	private void lancioDeiDadi()
	{
		try
		{
			System.out.print("\n. ");
			Thread.sleep(500);
			System.out.print(". ");
			Thread.sleep(500);
			System.out.println(". ");
			Thread.sleep(300);
		}
		catch (InterruptedException ex)
		{
			Thread.currentThread().interrupt();
		}
	}
/**
 * metodo che fa partire una partita; dato che non c'è la modalita multigiocatore, il giocatore continua a giocare finche no vince o perde
 */
	public void esegui()
	{
		boolean continua = true;
		boolean partitaVinta = false;
		do
		{
			giocaUnTurno();
			if (player.getPatrimonio() >= 1000000)
			{
				continua = false;
				System.out.println(MESS_FINITI);
				partitaVinta = true;
			}
			if (player.getPatrimonio() < 0)
			{
				continua = false;
				System.out.println(MESS_FINITI);
				partitaVinta = false;
			}
		} while (continua);

		finePartita(partitaVinta);
	}
/**
 * visualizza il risultato finale
 * @param partitaVinta
 */
	private void finePartita(boolean partitaVinta)
	{
		if (partitaVinta)
			System.out.println("VITTORIA, SEI MILIONARIO!!!");
		else
			System.out.println("SCONFITTA, SEI ANDATO IN BANCAROTTA!!!");
	}
}

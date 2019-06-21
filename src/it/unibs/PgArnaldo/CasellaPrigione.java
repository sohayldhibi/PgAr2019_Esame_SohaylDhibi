package it.unibs.PgArnaldo;

import it.unibs.fp.mylib.EstrazioniCasuali;
import it.unibs.fp.mylib.InputDati;

public class CasellaPrigione extends Casella
{
	int costo;

	public CasellaPrigione(int id, String nome, int costo)
	{
		super(id, nome);
		this.costo = costo;
		messaggioEffetto = "Sei finito in prigione!!";
	}

	/**
	 * Se il giocatore ha ticket (o ha gia passato 3 turni in prigione) finisce subito
	 * altrimenti chiede ad ogni turno di prigione la possibilita di uscire (imponendo i turniinprigione pari a 0) pagando la cauzione o di tentare la sorte
	 */
	@Override
	public void effetto(Giocatore player)
	{
		super.effetto(player);
		if(player.getTurniInPrigione()<=3) {
		if (player.getTicket()) {
			
			System.out.println("Esci gratis di prigione, consumi il tuo ticket");
			player.setTicket(false);
		}
		else
		{
			if (InputDati.yesOrNo("Vuoi pagare " + costo + "I€€€ per uscire di prigione?\n"))
			{
				System.out.println("Esci di prigione pagando " + costo + " I€€€");
				player.setPatrimonio(player.getPatrimonio() - costo);
				player.setTurniInPrigione(0);
			}
			else
			{
				System.out.println("tira due dadi;\n"
						+ " Se sui due dadi escono due numeri uguali allora il giocatore finisce il turno corrente e il "
						+ "turno successivo smette di essere imprigionato\n"
						+ "Se sui due dadi escono due numeri diversi allora il giocatore paga "+(costo/4+costo/3)+" I€€€ e"
						+ "salta il turno\n\n"
						+ "Lancio...");
				int primoDado = EstrazioniCasuali.estraiIntero(1, 6);
				System.out.print(primoDado + "   ");
				int secondoDado = EstrazioniCasuali.estraiIntero(1, 6);
				System.out.println(secondoDado);
				if(primoDado==secondoDado)
					player.setTurniInPrigione(0);
				else
				{
					System.out.println("Paghi "+(costo/4+costo/3)+" I€€€");
					player.setPatrimonio(player.getPatrimonio()-(costo/4+costo/3));
					player.setTurniInPrigione(player.getTurniInPrigione()+1);
				}
				
					
			}
		}
	}
		else {
			System.out.println("Hai passato 3 turni in prigione; puoi uscire");
			player.setTurniInPrigione(0);
		}
	}

}

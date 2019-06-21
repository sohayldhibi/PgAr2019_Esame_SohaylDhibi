package it.unibs.PgArnaldo;

import it.unibs.fp.mylib.InputDati;

public class CasellaProprieta extends Casella
{

	private int[] costo=new int[3];

	private int[] guadagno=new int[3];
	
	int stato=0; //0 sta per ancora non acquistata, 1 per acquistata(base), 2 per acquistata(casa), 3 per acquistata(hotel) 

	public CasellaProprieta(int id, String nome, int costoBase, int costoCasa, int costoHotel, int guadagnoBase,
			int guadagnoCasa, int guadagnoHotel)
	{
		super(id, nome);
		costo[0] = costoBase;
		costo[1] = costoCasa;
		costo[2] = costoHotel;
		guadagno[0] = guadagnoBase;
		guadagno[1] = guadagnoCasa;
		guadagno[2] = guadagnoHotel;
		messaggioEffetto="Questa è una proprieta'";
	}
	
	/**
	 * se lo stato e' diverso da 0 non sarà possibile lavorarci e verra considerata la 
	 * proprieta del giocatore (della tipologia che era quando la aveva acquistata)
	 * altrimenti ti da la possibilita di comprare il terreno, costruirvi la casa o costruirvi l'hotel
	 */
	@Override
	public void effetto(Giocatore player)
	{
		super.effetto(player);
		if(stato==0)
		{
			System.out.println("La proprieta' e' ancora libera\n"
					+ "1\t\tPuoi scegliere se comprarla al prezzo base di "+costo[0]+" I€€€\n"
							+ "2\t\tSe comprarci una casa al prezzo di "+costo[1]+" I€€€\n"
									+ "3\t\tSe comprarci un Hotel al prezzo di "+costo[2]+" I€€€\n"
											+ "0\t\tNon fare nulla\n");
			stato=InputDati.leggiIntero("", 0, 3);
			if(stato!=0)
				if(costo[stato-1]<=player.getPatrimonio())
				{
					player.setPatrimonio(player.getPatrimonio()-costo[stato-1]);
					System.out.println("Hai una nuova proprieta'!!!"
							+ "Quando ripasserai su questa casella guadagnerai "+guadagno[stato-1]+" I€€€");
				}
				else {
					System.out.println("Non hai abbastanza soldi per permettertelo");
				}
			
		}
		else {
			System.out.println("Questa Proprieta' e' tua!\n Guadagni "+guadagno[stato-1]+" I€€€");
			player.setPatrimonio(player.getPatrimonio()+guadagno[stato-1]);
		}
		
	}

}

package it.unibs.PgArnaldo;

public class CasellaImprevisto extends Casella
{
	private int valore;
	
	public CasellaImprevisto(int id, String nome,String messaggio,int valore)
	{
		super(id,nome);
		super.messaggioEffetto=messaggio;
		this.valore=valore;
	}
	/**
	 * il patrimonio viene ridotto di "valore"
	 */
	public void effetto(Giocatore player)
	{
		System.out.println("Ti trovi nella casella "+nome);
		System.out.println(messaggioEffetto);
		player.setPatrimonio(player.getPatrimonio()-valore);
	}
}

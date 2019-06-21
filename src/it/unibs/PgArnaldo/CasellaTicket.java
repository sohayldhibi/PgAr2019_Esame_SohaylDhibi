package it.unibs.PgArnaldo;

public class CasellaTicket extends Casella
{

	public CasellaTicket(int id, String nome)
	{
		super(id, nome);
		messaggioEffetto="Potrai uscire gratis di prigione la prossima volta che ci entrerai!";
	}
	/**
	 * si spiega da sè
	 */
	@Override
	public void effetto(Giocatore player)
	{
		super.effetto(player);
		player.setTicket(true);
	}
}

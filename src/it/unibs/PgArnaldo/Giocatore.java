package it.unibs.PgArnaldo;


public class Giocatore
{
	private String nomeGiocatore;
	private int patrimonio;
	private int idCasellaAttuale;
	private boolean ticket = false;
	private int turniInPrigione=0;//quando e' 0 vuol dire che non si trova in prigione

	public Giocatore(String nomeGiocatore, int patrimonio, int idCasellaAttuale)
	{
		super();
		this.nomeGiocatore = nomeGiocatore;
		this.patrimonio = patrimonio;
		this.idCasellaAttuale = idCasellaAttuale;
	}

	public int getTurniInPrigione()
	{
		return turniInPrigione;
	}
	public void setTurniInPrigione(int turniInPrigione)
	{
		this.turniInPrigione = turniInPrigione;
	}
	public void setTicket(boolean ticket)
	{
		this.ticket = ticket;
	}

	public boolean getTicket()
	{
		return ticket;
	}

	public int getPatrimonio()
	{
		return patrimonio;
	}

	public String getNomeGiocatore()
	{
		return nomeGiocatore;
	}

	public int getIdCasellaAttuale()
	{
		return idCasellaAttuale;
	}

	public void setIdCasellaAttuale(int idCasellaAttuale)
	{
		this.idCasellaAttuale = idCasellaAttuale;
	}

	public void setPatrimonio(int patrimonio)
	{
		this.patrimonio = patrimonio;
	}

	@Override
	public String toString()
	{
		return "E' il turno di " + nomeGiocatore + ", Patrimonio: " + patrimonio + " I€€€, Casella attuale: "
				+ (idCasellaAttuale + 1);
	}
}

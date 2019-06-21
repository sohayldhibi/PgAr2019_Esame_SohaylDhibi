package it.unibs.PgArnaldo;


public class Casella {
	protected int id;
	protected String nome;
	protected String messaggioEffetto="In questa casella non ti succedera' niente";
	/**
	 * casella associata alla casella iniziale, ma anche superclasse di tutte le altre caselle
	 * @param id
	 * @param nome
	 */
	public Casella(int id, String nome)
	{
		super();
		this.id = id;
		this.nome = nome;
	}
	public String getNome()
	{
		return nome;
	}
	public int getId()
	{
		return id;
	}
	public void effetto(Giocatore player)
	{
		System.out.println("Ti trovi nella casella "+nome);
		System.out.println(messaggioEffetto);
	}
}

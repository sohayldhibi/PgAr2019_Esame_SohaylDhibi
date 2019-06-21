package it.unibs.fp.mylib;

import java.util.Scanner;

public class PremiInvio
{
	public static void premiInvio()
	{
		try
		{
			Scanner sc = new Scanner(System.in);
			System.out.print("Premi invio per continuare...");
			sc.nextLine();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}

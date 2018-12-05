package fuzzy;

abstract public class Util 
{
	public double max(double fz1, double fz2)
	{
		if(fz1>fz2)
		{
			return fz1;
		}
		
		return fz2;
	}
	
	public double min(double fz1, double fz2)
	{
		if(fz1<fz2)
		{
			return fz1;
		}
		
		return fz2;
	}
	
	public double stdComplement(double fz)
	{
		return 1 - fz;
	}
	
	public double sugenoComplement(double fz, double y)
	{
		if(y <= -1)
		{
			System.err.println("ERROR INVALID y LESS THAN OR EQUAL TO -1. EXITING.");
			System.exit(1);
		}
		
		return (1-fz)/(1 + (y*fz));
	}
	
	public double yagerCompiment(double fz, double w)
	{
		if(w <= 0)
		{
			System.err.println("ERROR INVALID w LESS THAN OR EQUAL TO 0. EXITING.");
			System.exit(1);
		}
		
		return Math.pow((1 - Math.pow(fz, w)), 1/w);
	}
	
	public String allCaps(String word)
	{
		String st = "";
		char currentChar = ' ';
		
		for(int i = 0; i < word.length(); i++)
		{
			currentChar = word.charAt(i);
			if(currentChar >= 'a' && currentChar <= 'z')
			{
				st += ((char)(currentChar - 32) + "");
			}
			else
			{
				st += currentChar;
			}
		}
		
		return st;
	}
}

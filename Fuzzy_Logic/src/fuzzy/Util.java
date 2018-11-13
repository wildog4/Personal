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
	
	public double sugenoComplement(double fz, double y)	//INCOMPLETE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	{
		return 1.0;
	}
}

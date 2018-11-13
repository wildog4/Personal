package fuzzy;


public class FuzzyArray extends Util
{
	public double[] fuzzy, membership;
	
	public FuzzyArray(double[] f, double[] m)
	{
		if(f!=null && m!=null)
		{
			if(f.length==m.length)
			{
				fuzzy = new double[f.length];
				membership = new double[m.length];
				for(int i=0;i<f.length;i++)
				{
					fuzzy[i] = f[i];
					if(m[i]<0 || m[i]>1)
					{
						System.err.println("INVALID DECLARATION ARGUMENT: MEMBERSHIP NOT WITHIN [0,1]. EXITING.");
						System.exit(1);
					}
					membership[i] = m[i];
				}
			}
			else
			{
				System.err.println("INVALID DECLARATION ARGUMENT: INCONSISTENT MEMBERSHIP AND ARRAY SIZE. EXITING.");
				System.exit(1);
			}
		}
		else
		{
			System.err.println("INVALID DECLARATION ARGUMENT: NULL ARRAY. EXITING.");
			System.exit(1);
		}
	}
	
	
	public double getMembershipGrade(double value)
	{
		for(int i=0; i<fuzzy.length; i++)
		{
			if(fuzzy[i] == value)
			{
				return membership[i];
			}
		}
		return -1.0;
	}
	
	public void printArray()
	{
		System.out.println("Elements:\t\tMembership Grades");
		
		for(int i=0; i<fuzzy.length; i++)
		{
			System.out.println(fuzzy[i] + "\t\t\t" + membership[i]);
		}
		System.out.println("\n");
	}
	
	public double max(double fz, int index)
	{
		if(fz>fuzzy[index])
		{
			return fz;
		}
		
		return fuzzy[index];
	}
	
}

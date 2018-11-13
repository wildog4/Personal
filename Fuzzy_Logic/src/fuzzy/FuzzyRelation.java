package fuzzy;

import java.util.Scanner;

public class FuzzyRelation extends Util
{
	public double [][] fuzzy;
	
	public FuzzyRelation()
	{
		Scanner keyboard = new Scanner(System.in);
		
		System.out.print("Please enter how many rows are in the relation: ");
		int row = keyboard.nextInt();
		while(!(row>0))
		{
			System.out.println("\n\nThe value entered is invalid please put a value greater than 0.\n");
			System.out.print("Please enter how many rows are in the relation: ");
			row = keyboard.nextInt();
		}
		
		System.out.print("Please enter how many columns are in the relation: ");
		int column = keyboard.nextInt();
		while(!(column>0))
		{
			System.out.println("\n\nThe value entered is invalid please put a value greater than 0.\n");
			System.out.print("Please enter how many columns are in the relation: ");
			column = keyboard.nextInt();
		}
		
		fuzzy = new double[row][column];
		for(int r = 0; r < row; r++)
		{
			for(int c = 0; c < column; c++)
			{
				System.out.print("Please enter the value for position " + r + " " + c + " in the relation: ");
				double value = keyboard.nextDouble();
				while(!((value>=0)&&(value<=1)))
				{
					System.out.println("\n\nThe value entered is invalid please put a value between 0 and 1 inclusivly.\n");
					System.out.print("Please enter the value for position " + r + " " + c + " in the relation: ");
					value = keyboard.nextDouble();
				}
				fuzzy[r][c] = value;
			}
		}
	}
	
	public FuzzyRelation(double fz[][])		//fz is a mapping of the indexes
	{
		if(fz != null)
		{
			fuzzy =  new double[fz.length][fz[0].length];
			for(int r = 0; r < fz.length; r++)
			{
				for(int c = 0; c < fz[0].length; c++)
				{
					if(fz[r][c]<0 || fz[r][c]>1)
					{
						System.err.println("INVALID DECLARATION ARGUMENT: MEMBERSHIP NOT WITHIN [0,1]. EXITING.");
						System.exit(1);
					}
					fuzzy[r][c] = fz[r][c];
				}
			}
		}
		else
		{
			System.err.println("INVALID DECLARATION ARGUMENT: NULL MATRIX. EXITING.");
			System.exit(1);
		}
	}
	
	double[][] maxminComposition(FuzzyRelation fr)
	{
		int row = fuzzy.length,
			col = fuzzy[0].length,
			frRow = fr.fuzzy.length,
			frCol = fr.fuzzy[0].length;
		
		if(col != frRow)
		{
			System.err.println("INVALID DECLARATION ARGUMENT: INCONSISTENT RELATIONS. EXITING.");
			System.exit(1);
		}
		
		double[][] comp = new double[row][frCol];
		
		for(int r = 0; r<row; r++)
		{
			for(int fc = 0; fc < frCol; fc++)
			{
				int i = 0;
				double result = 0;
				while(i < col)
				{
					result = max(result,min(fuzzy[r][i],fr.fuzzy[i][fc]));
					i++;
				}
				comp[r][fc] = result;
			}
		}
		
		return comp;
	}
	
	double[][] stdUnion(FuzzyRelation fr)
	{
		int row = fuzzy.length,
				col = fuzzy[0].length,
				frRow = fr.fuzzy.length,
				frCol = fr.fuzzy[0].length;
		
		if((row!=frRow) || (col != frCol))
		{
			System.out.println("INCOMPATIBLE MATRICIES RETURNING NULL");
			return null;
		}
		
		double[][] union = new double [row][col];
		
		for(int r = 0; r < row; r++)
		{
			for(int c = 0; c < col; c++)
			{
				union[r][c] = max(fuzzy[r][c],fr.fuzzy[r][c]);
			}
		}
		return union;
	}
	
	public double[][] maxminTransitive()
	{
		if(fuzzy.length!=fuzzy[0].length)
		{
			System.out.println("INCOMPATIBLE RELATION RETURNING NULL");
			return null;
		}
		
		double[][] temp1 = new double[fuzzy.length][fuzzy.length];
		double[][] temp2 = new double[fuzzy.length][fuzzy.length];
		
		do{
			for(int r=0; r<fuzzy.length;r++)
			{
				for(int c=0; c<fuzzy.length;c++)
				{
					temp1[r][c] = fuzzy[r][c];
					temp2[r][c] = fuzzy[r][c];
				}
				System.out.println();
			}
			temp2 = this.maxminComposition(this);
			FuzzyRelation fr = new FuzzyRelation(temp2);
			temp2 = this.stdUnion(fr);
			fuzzy = temp2;
		}
		while(!isEqual(fuzzy, temp1));
		
		return fuzzy;
	}
	
	
	private boolean isEqual(double[][] t1, double[][] t2) 
	{
		boolean value = true;
		
		for(int r=0; r<fuzzy.length;r++)
		{
			for(int c=0; c<fuzzy.length;c++)
			{
				if(t2[r][c] != t1[r][c])
				{
					value = false;
				}
			}
		}
		
		return value;
	}

	public void printRelation()
	{
		System.out.println("Fuzzy relation:\n");
		
		System.out.print("Positions\t");
		for(int c = 0; c < fuzzy[0].length; c++)
		{
			System.out.print(c + "\t");
		}
		
		System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");		
		
		for(int r = 0; r < fuzzy.length; r++)
		{
			System.out.print(r + "\t\t");
			for(int c = 0; c < fuzzy[0].length; c++)
			{
				System.out.print(fuzzy[r][c] +"\t" );
			}
			System.out.println();
		}
		
		System.out.println("\n");
	}
}

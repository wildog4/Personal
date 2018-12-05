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
	
	public double[][] maxminTransitive()		//Calculates the max-min transitive closure of the fuzzy matrix
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
	
	public boolean isTransitive()		//Checks the fuzzy matrix if it has the transitive property
	{
		if(fuzzy.length!=fuzzy[0].length)	//Can't be transitive without rows equaling columns
		{
			return false;
		}

		for(int x = 0; x < fuzzy.length; x++)
		{
			
			for(int y = 0; y < fuzzy[0].length; y++)
			{
				
				for(int z = 0; z < fuzzy.length; z++)
				{
/*					System.out.println("x: " + x);			//These statements were for debugging purposes
					System.out.println("y: " + y);
					System.out.println("z: " + z);
					System.out.println("fuzzy[x][y]: " + fuzzy[x][y]);
					System.out.println("fuzzy[y][z]: " + fuzzy[y][z]);
					System.out.println("fuzzy[x][z]: " + fuzzy[x][z]);
*/					
					
					double temp = min(fuzzy[x][y],fuzzy[y][z]);	
//					System.out.println("temp: " + temp);
					
					System.out.println();
					if(fuzzy[x][z]<temp)
					{
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public boolean isReflexive()
	{
		if(fuzzy.length!=fuzzy[0].length)	//Can't be reflexive without rows equaling columns
		{
			return false;
		}
		
		for(int r = 0; r < fuzzy.length; r++)
		{
			if(fuzzy[r][r]!=1)
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean isSymmetric()
	{
		if(fuzzy.length!=fuzzy[0].length)	//Can't be symmetric without rows equaling columns
		{
			return false;
		}
		
		for(int x = 0; x < fuzzy.length; x++)
		{
			for(int y = 0; y < fuzzy[0].length; y++)
			{
				if(fuzzy[x][x] != fuzzy[y][x])
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	private boolean isEqual(double[][] t1, double[][] t2) //Checks if two Matrix have the same values 
															//in each position
	{	
		if(t1.length!=t2.length || t1[0].length!=t2[0].length)
		{
			return false;
		}
		
		for(int r=0; r<t1.length;r++)
		{
			for(int c=0; c<t1[0].length;c++)
			{
				if(t2[r][c] != t1[r][c])
				{
					return false;
				}
			}
		}
		
		return true;
	}

	public void printRelation()	//Prints out the fuzzy matrix with formatting
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

package fuzzy;


public class Driver 
{
	public static void main(String[] args) 
	{
		double[] fz= {1.23232, 75.3, 12, 41}, 
				mem = {.6, 1, 0,.32};
				//membad = {3004},		//Test Values
				//bad = null;			//Test Values
		
		double[][] fzr1 = {{.3, .5, .3}, {0, 0, .2}, {.3, 1, 0}};
		double[][] fzr2 = {{.4, .6, .7}, {0, .8, .2}, {.5, .7, 0}};
		double[][] test;
		
		FuzzyArray f1 = new FuzzyArray(fz, mem);
		FuzzyRelation fr1 = new FuzzyRelation(fzr1);
		FuzzyRelation fr2 = new FuzzyRelation(fzr2);
		test = fr1.maxminComposition(fr2);
		for(int r = 0; r < test.length; r++)
		{
			for(int c = 0;c < test[0].length;c++)
			{
				System.out.print(test[r][c] + "\t");
			}
			System.out.println();
		}
		
/*		test = fr1.stdUnion(fr2);
		System.out.println("TESTING UNION: \n");
		
		for(int r = 0; r < test.length; r++)
		{
			for(int c = 0;c < test[0].length;c++)
			{
				System.out.print(test[r][c] + "\t");
			}
			System.out.println();
		}
*/		
		fr1.maxminTransitive();
		fr1.printRelation();
		
//		FuzzyRelation fr = new FuzzyRelation();
//		fr.printRelation();
		
		//System.out.println(f1.fuzzy.length);
		//System.out.println(f1.getMembershipGrade(fz[0]));
		//System.out.println(f1.getMembershipGrade(-23.2));
		//f1.printArray();
		//fr1.printRelation();
		//FuzzyArray badf = new FuzzyArray(bad, mem);	//testing putting in a null array
		//FuzzyArray badf = new FuzzyArray(fz, fz);		//testing putting in a membership array with a number different from the fuzzy set
		
	}
}

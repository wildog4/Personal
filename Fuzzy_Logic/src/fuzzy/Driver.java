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
/*		for(int r = 0; r < test.length; r++)
		{
			for(int c = 0;c < test[0].length;c++)
			{
				System.out.print(test[r][c] + "\t");
			}
			System.out.println();
		}
*/		
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
//		fr1.printRelation();
		
//		System.out.println(fr1.isTransitive());
		
		String[] paramNames = {"b"};
		String[] paramRanges = {"1"};
		double[] paramValues = {1};
		
		FuzzyComplement fc = new FuzzyComplement("5 2 ^ 5 +", paramNames,paramRanges) ;
//		fc.printEquation();
//		fc.printParamNames();
//		System.out.println(fc.evaluate(.1,paramValues));
		
		FuzzyComplement fc1 = new FuzzyComplement("1 a -");
//		fc1.printEquation();
//		fc.printParamNames();
//		System.out.println(fc1.evaluate(.3));
//		System.out.println(fc1.monotonicity());
//		System.out.println(fc1.boundaryCondition());
		
		String[] sugenoNames = {"y"};
		String[] sugenoRanges = {"(-1,3)"};
		
		FuzzyComplement sugeno = new FuzzyComplement("1 a - y a * 1 + /",sugenoNames,sugenoRanges);
		System.out.println(sugeno.monotonicity());
		System.out.println(sugeno.boundaryCondition());
		
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

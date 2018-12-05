package fuzzy;

import java.util.Scanner;

public class Demo
{

	public static void main(String[] args) 
	{
		String input = "NO";
		boolean invalid = true;
		
		FuzzyComplement fc;
		FuzzyRelation fr;
		FuzzyArray fa;
		
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Welcome to the Fuzzy logic demo version 1.0 created by Will Csont! Before we start let's show how it works by showing a few examples.");
		System.out.println("To start we will prompt you to ask what would you like to test. We can do fuzzy relations, fuzzy arrays and fuzzy complements.");
		System.out.println();
		System.out.println("Here's an example to make a fuzzy array. With crisp values of {1.2, 75, 2, 41} and membership grades of {.6, 1 ,0 ,.32}");
		System.out.println("for each of the crisp values such that the position in the crisp values is the position of the membership grade. ");
		System.out.println("(ie. 1.2's membership grade is .6). This is what the array would look like printed:");
		
		double[] fz= {1.2, 75, 2, 41}, 
				mem = {.6, 1, 0,.32};
		
		fa = new FuzzyArray(fz, mem);
		
		fa.printArray();
		
		System.out.println("When you are making a fuzzy array you will need to put in all of the values manually one by one first the crisp values");
		System.out.println("then the membership grades to each of the crisp values.");
		System.out.print("Please enter a character to continue: ");
		keyboard.next();
		
		System.out.println("\n\n\n\n\n\n");
		System.out.println("Here's an example of making a fuzzy relation. It takes a matrix of double values between 0 and 1 inclusivly to represent the");
		System.out.println("membership grades in the relation. We can determine if the fuzzy relation has certain properties for example if we want to");
		System.out.println("make a 3 x 3 fuzzy relation with the members {{.3, .5, .3}, {0, 0, .2}, {.3, 1, 0}} it would look like this:");
		
		double[][] fzr = {{.3, .5, .3}, {0, 0, .2}, {.3, 1, 0}};
		
		fr = new FuzzyRelation(fzr);
		fr.printRelation();
		
		System.out.println("When making a fuzzy relation you will be prompted to put down how many rows followed by collumns are in the relation. Please ");
		System.out.println("put in a positive integer for each of those values. Then you will be prompted to put in each membership grade individually.");
		System.out.print("Please enter a character to continue: ");
		keyboard.next();
		System.out.println("\n\n\n\n\n\n");
		
		System.out.println("Finally we have the abillity to construct your own equation for a complement and be able to test if it satisfies monotonicity and");
		System.out.println("the boundry condition. You can even create your own variables and ranges for the variables (NOTE: The ranges are limited.");
		System.out.println("So it currently not possible to place in 2 ranges ie. x being in the values(1,2] U (3,4] ). The program takes a string for the equation");
		System.out.println(" and two string arrays one to be each name of the variables the other array is to represent the ranges. ");
		System.out.println(" The positions in both arrays match with each other meaning the parameter at position i in the parameter array has their range ");
		System.out.println("defined in position i in the range array. When writing the equation it needs to");
		System.out.println("be written in Reverse Polish Notation. For example the Sugeno complement will look like this '1 a - y a * 1 + /' (y is used for lambda)");
		System.out.println("Also, 'a' represents the membership grade and is necessary to be used. When making a complement you will be prompted to state how many");
		System.out.println("parameters are in the equation. Please enter in a non-negative integer. When writing the parameters do not put any spaces in between the");
		System.out.println("the variables. An example is the Sugeno parameter range would be written like so 'y>-1'. Here is the Sugeno class with its values printed out.");
		System.out.println();
		
		String[] sugenoNames = {"y"};
		String[] sugenoRanges = {"y>-1"};
		
		fc = new FuzzyComplement("1 a - y a * 1 + /",sugenoNames,sugenoRanges);
		fc.printEquation();
		fc.printParamNames();
		System.out.println("Satisfies monotonicity?: " + fc.monotonicity());
		System.out.println("Satisfies boundary condition?: " +fc.boundaryCondition());
		System.out.println();
		System.out.print("Please enter a character to continue: ");
		keyboard.next();
		System.out.println("\n\n\n\n\n\n");
		
		System.out.println("Ok now that the information was given let's begin. You will be prompted which of the stated previously operations you would like to do ");
		System.out.println("(Fuzzy Array, Fuzzy Relation, Fuzzy Complement). And then it will ask if you are done with what you made. Where if you say 'yes'");
		System.out.println("Whatever was made will be discarded. ");
		System.out.println("\n\n");
		
		fc = null;
		fr = null;
		fa = null;
		
		while(!input.equals("YES"))
		{
			System.out.println("Which would you like to use? Type fa for fuzzy array, fr for fuzzy relation, or fc for fuzzy complement. Or type q to quit: ");
			input = keyboard.next();
			input = allCaps(input);
			
			if(input.equals("FA") || input.equals("FUZZY ARRAY"))
			{
				invalid = false;
			}
			else if(input.equals("FR") || input.equals("FUZZY RELATION"))
			{
				invalid = false;
			}
			else if(input.equals("FC") || input.equals("FUZZY COMPLEMENT"))
			{
				invalid = false;
			}
			else if(input.equals("Q") || input.equals("QUIT"))
			{
				System.out.println("Thank you for using this Demo!");
				System.exit(0);
			}
			
			while(invalid)
			{
				System.out.println("I'm sorry you have entered an incorrect key. Please type fa for fuzzy array, ");
				System.out.println("fr for fuzzy relation, or fc for fuzzy complement. Or type q to quit");
				input = keyboard.next();
				input = allCaps(input);
				if(input.equals("FA") || input.equals("FUZZY ARRAY"))
				{
					invalid = false;
				}
				else if(input.equals("FR") || input.equals("FUZZY RELATION"))
				{
					invalid = false;
				}
				else if(input.equals("FC") || input.equals("FUZZY COMPLEMENT"))
				{
					invalid = false;
				}
				else if(input.equals("Q") || input.equals("QUIT"))
				{
					System.out.println("Thank you for using this Demo!");
					System.exit(0);
				}
			}
			
			if(input.equals("FA") || input.equals("FUZZY ARRAY"))
			{
				fa = new FuzzyArray();
				fa.printArray();
				System.out.print("Are you done with this fuzzy array? Type 'yes' if you are done: ");
				input = keyboard.next();
				System.out.println();
				input = allCaps(input);
				while(!input.equals("YES"))
				{
					fa.printArray();
					System.out.print("Are you done with this fuzzy array? Type 'yes' if you are done: ");
					input = keyboard.next();
					System.out.println();
					input = allCaps(input);
				}
			}
			else if(input.equals("FR") || input.equals("FUZZY RELATION"))
			{
				fr = new FuzzyRelation();
				fr.printRelation();
				System.out.println("What would you like to do with this fuzzy relation? Type trans to do the max-min transitive closure, istrans to see if it is transitive,");
				System.out.println("issym to see if it is symmetric, isref to see if it is reflexive Note: these work only if you have the same amount of rows and columns ");
				System.out.print("in the relation. Otherwise don't type any of the selections.");
				input = keyboard.next();
				System.out.println();
				input = allCaps(input);
				switch(input)
				{
					case "TRANS":
						fr.maxminTransitive();
						System.out.println("Your relation has done the max-min transitive closure");
						break;
					case "ISTRANS":
						if(fr.isTransitive())
						{
							System.out.println("The relation is transitive");
						}
						else
						{
							System.out.println("The relation is not transitive");
						}
						break;
					case "ISSYM":
						if(fr.isSymmetric())
						{
							System.out.println("The relation is symmetric");
						}
						else
						{
							System.out.println("The relation is not symmetric");
						}
						break;
					case "ISREF":
						if(fr.isReflexive())
						{
							System.out.println("The relation is reflexive");
						}
						else
						{
							System.out.println("The relation is not reflexive");
						}
						break;
					default:
						System.out.println("You selected to do nothing.");
						break;
				}
				System.out.println("\n");
				System.out.print("Are you done with this fuzzy relation? Type 'yes' if you are done: ");
				input = keyboard.next();
				System.out.println();
				input = allCaps(input);
				while(!input.equals("YES"))
				{
					System.out.println("What would you like to do with this fuzzy relation? Type trans to do the max-min transitive closure, istrans to see if it is transitive,");
					System.out.println("issym to see if it is symmetric, isref to see if it is reflexive Note: these work only if you have the same amount of rows and columns ");
					System.out.print("in the relation. Otherwise don't type any of the selections.");
					input = keyboard.next();
					System.out.println();
					input = allCaps(input);
					switch(input)
					{
						case "TRANS":
							fr.maxminTransitive();
							System.out.println("Your relation has done the max-min transitive closure");
							break;
						case "ISTRANS":
							if(fr.isTransitive())
							{
								System.out.println("The relation is transitive");
							}
							else
							{
								System.out.println("The relation is not transitive");
							}
							break;
						case "ISSYM":
							if(fr.isSymmetric())
							{
								System.out.println("The relation is symmetric");
							}
							else
							{
								System.out.println("The relation is not symmetric");
							}
							break;
						case "ISREF":
							if(fr.isReflexive())
							{
								System.out.println("The relation is reflexive");
							}
							else
							{
								System.out.println("The relation is not reflexive");
							}
							break;
						default:
							System.out.println("You selected to do nothing.");
							break;
					}
					System.out.println("\n");
					System.out.print("Are you done with this fuzzy relation? Type 'yes' if you are done: ");
					input = keyboard.next();
					System.out.println();
					input = allCaps(input);
				}
			}
			else if(input.equals("FC") || input.equals("FUZZY COMPLEMENT"))
			{
				fc = new FuzzyComplement();
				fc.printEquation();
				fc.printParamNames();
				System.out.println("What would you like to do with this fuzzy complement? Type 'eval' to evaluate the equation. Type 'mono' to see if it satisfies ");
				System.out.println("monotonicity. Type 'bound' to see if it satisfies the boundary condition.");
				input = keyboard.next();
				System.out.println();
				input = allCaps(input);
				switch(input)
				{
					case "EVAL":
						
						System.out.println("Please enter the value for a: ");
						double a = keyboard.nextDouble();
						if(!fc.hasParam)
						{
							System.out.println("The evaluated value is: " + fc.evaluate(a));
						}
						else
						{
							double[] params = new double[fc.paramName.length];
							for(int i = 0; i < params.length; i++)
							{
								System.out.print("Please enter the value for the parameter" + fc.paramName[i] +" with the range " + fc.paramRange[i] + ": ");
								params[i] = keyboard.nextDouble();
							}
							System.out.println("The evaluated value is: " + fc.evaluate(a,params));
						}
						break;
					case "MONO":
						if(fc.monotonicity()) 
						{
							System.out.println("The equation you entered satisfies monotonicity.");
						}
						else
						{
							System.out.println("The equation you entered doesn't satisfies monotonicity.");
						}
						break;
					case "BOUND":
						if(fc.boundaryCondition()) 
						{
							System.out.println("The equation you entered satisfy the boundary condition.");
						}
						else
						{
							System.out.println("The equation you entered doesn't satisfy the boundary condition.");
						}
						break;
					default:
						System.out.println("You selected to do nothing.");
						break;
				}
				System.out.println("\n");
				System.out.print("Are you done with this fuzzy Complement? Type 'yes' if you are done: ");
				input = keyboard.next();
				System.out.println();
				input = allCaps(input);
				while(!input.equals("YES"))
				{
					System.out.println("What would you like to do with this fuzzy complement? ");
					input = keyboard.next();
					System.out.println();
					input = allCaps(input);
					switch(input)
					{
						case "EVAL":
							
							System.out.println("Please enter the value for a: ");
							double a = keyboard.nextDouble();
							if(!fc.hasParam)
							{
								System.out.println("The evaluated value is: " + fc.evaluate(a));
							}
							else
							{
								double[] params = new double[fc.paramName.length];
								for(int i = 0; i < params.length; i++)
								{
									System.out.print("Please enter the value for the parameter" + fc.paramName[i] +" with the range " + fc.paramRange[i] + ": ");
									params[i] = keyboard.nextDouble();
								}
								System.out.println("The evaluated value is: " + fc.evaluate(a,params));
							}
							break;
						case "MONO":
							if(fc.monotonicity()) 
							{
								System.out.println("The equation you entered satisfies monotonicity.");
							}
							else
							{
								System.out.println("The equation you entered doesn't satisfy monotonicity.");
							}
							break;
						case "BOUND":
							if(fc.boundaryCondition()) 
							{
								System.out.println("The equation you entered satisfies the boundary condition.");
							}
							else
							{
								System.out.println("The equation you entered doesn't satisfy the boundary condition.");
							}
							break;
						default:
							System.out.println("You selected to do nothing.");
							break;
					}
					System.out.println("\n");
					System.out.print("Are you done with this fuzzy complement? Type 'yes' if you are done: ");
					input = keyboard.next();
					System.out.println();
					input = allCaps(input);
				}
			}
			
			fc = null;
			fr = null;
			fa = null;
			invalid = true;
			System.out.print("Are you finished with this demo? If so type 'yes'");
			input = keyboard.next();
			input = allCaps(input);	
		}
		System.out.println("Thank you for using this Demo!");
	}


	private static String allCaps(String word)
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

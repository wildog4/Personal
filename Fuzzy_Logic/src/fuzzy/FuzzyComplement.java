package fuzzy;

import java.util.Scanner;
import java.util.Stack;

public class FuzzyComplement
{
	public static final double FUZZY_INCREMENTER = .01;		//Used to determine axioms such as monotonecity  must be a value greater than zero
															//the closer the value is to zero the more accurate, but slower the program runs
	
	public static final int FUZZY_TEST_CASES = 100; 		//how many test cases are used to test parameters on axioms
															//the higher the value the more accurate, but slower the program runs
	
	String equation;				//The String of the equation
	Boolean hasParam;				//States if the equation has a parameter
	double[] param;					//The values of the parameters
	String[] paramRange;			//The range of each parameter
	String[] paramName;				// The names of the parameters
	
	public FuzzyComplement()		//Sets the variables without sending them in by taking them on a keyboard
	{
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Please enter in the equation for a fuzzy complement in reverse polish notation seperated by one space. Use a combination of "
				+ "+,-,*,/,^ and any variable with their names. Use single non-numeric/operator characters. "
				+ "Use 'a' \nto denote the membership grade: ");
		do
		{
			equation = keyboard.next();
			if(equation==null || equation == "")
			{
				System.out.println("Please put in a non blank expression");
			}
		}
		while(equation==null || equation == "");
		
		System.out.println("Does the equation have any parameters? Type y for yes or n for no.");
		String question = keyboard.next();	//used to check whether or not the Complement has parameters
		
		while(!question.equals("y") && !question.equals("Y") && !question.equals("n") 
				&& !question.equals("N"))
		{
			System.out.println("Please put in only the character y for yes or n for no. "
					+ "For if this equation has parameters");
			question = keyboard.next();
		}
		
		if(question.equals("n") || question.equals("N"))
		{
			hasParam = false;
			param = null;
			paramRange = null;
			paramName = null;
		}
		if(question.equals("y") || question.equals("Y"))
		{
			System.out.print("Enter how many parameters there are. "
					+ "If this is a mistake and you have no variables in the equation type '0'. : ");
			int paramNum = keyboard.nextInt();
			if(paramNum == 0)
			{
				hasParam = false;
				param = null;
				paramRange = null;
				paramName = null;
				return;
			}
			param = new double[paramNum];
			paramRange = new String[paramNum];
			paramName = new String[paramNum];
			
			for(int i = 0 ;(i < paramNum) && (!question.equals(";")); i++)
			{
				System.out.print("Enter the name of the paramater variable: ");
				question = keyboard.next();
				paramName[i] = question;
				
				System.out.println();
				System.out.print("Now enter the range of the paramater variables. "
						+ "Using >,<,<=,>=,=,!=(For not equals), or n meaning the parameter "
						+ "can be any number. do not put in spaces");
				question = keyboard.next();
				paramRange[i] = question;
				
/*				if(!question.equals("mistake") || !question.equals("Mistake"))
				{
					System.out.println();
					System.out.print("Enter the range of the paramater variables. "
							+ "Using >,<,<=,>=,=,!=(For not equals),[,],(,), or n meaning the parameter "
							+ "can be any number");
					question = keyboard.next();
					paramRange[i] = question;
				}
*/			}
			
/*			if(question.equals("mistake") || question.equals("Mistake"))
			{
				hasParam = false;
				param = null;
				paramRange = null;
				paramName = null;
				return;
			}
*/			
			hasParam = true;
		}
	}
	
	public FuzzyComplement(String equationN)
	{
		if(equationN==null||equationN.equals(""))
		{
			System.err.println("INVALID EQUATION: NULL OR EMPTY EQUATION");
			System.exit(1);
		}
		
		equation = equationN;
		hasParam = false;
		param = null;
		paramRange = null;
		paramName = null;
	}
	
	public FuzzyComplement(String equationN, String[] paramNameN, String[] paramRangeN)
	{
		if(equationN==null || equationN.equals(""))
		{
			System.err.println("INVALID EQUATION: NULL OR EMPTY EQUATION");
			System.exit(1);
		}
		
		equation = equationN;
		hasParam = false;
		
		if(paramNameN!=null && paramNameN.length > 0 && (paramNameN.length == paramRangeN.length))
		{
			paramRange = new String[paramRangeN.length];
			paramName = new String[paramNameN.length];
			param = new double[paramNameN.length];
			
			for(int i = 0; i<paramRange.length; i++)
			{
				paramRange[i] = paramRangeN[i];
				paramName[i] = paramNameN[i];
			}
			
			hasParam = true;
		}
		else
		{
			paramRange = null;
			paramName = null;
			param = null;
		}
	}
	
	public double evaluate(double a)		//Evaluates the equation only if there are no parameters in the
											//equation
	{
		if(hasParam)
		{
			System.out.println("Incorrect method usage send parameters returning -1.0");
			return -1.0;
		}
		
		double result = 0;
		Stack<String> equat = new Stack<>(); 
		Stack<Double> temp = new Stack<>(); 
		char currentChar= ' ';
		
		for(int i = equation.length()-1; i >=0; i-=2)
		{
			currentChar = equation.charAt(i);
			if(currentChar>='0' && currentChar<='9')
			{
				String number = currentChar + "";
				
				while((i > 0) 
						&& (equation.charAt(i-1)>='0' && equation.charAt(i-1)<='9'))
				{
					i--;
					currentChar = equation.charAt(i);
					number += currentChar;
				}
				
				number = reverse(number);
//				System.out.println(number);
				equat.push(number);
			}
			else
			{
				equat.push(equation.substring(i, i + 1));
			}
		}
		
		String token = "";
		
		while(!equat.isEmpty())
		{
			token = equat.pop();
			
//			System.out.print(token + "\t");

            double secondOperand = 0.0;
            double firstOperand = 0.0;

            switch (token) {
                case "+":
//                    System.out.print("Operate\t\t");

                    secondOperand = temp.pop();
                    firstOperand = temp.pop();

                    temp.push(firstOperand + secondOperand);
                    break;
                case "-":
//                    System.out.print("Operate\t\t");

                    secondOperand = temp.pop();
                    firstOperand = temp.pop();

                    temp.push(firstOperand - secondOperand);
                    break;
                case "*":
//                    System.out.print("Operate\t\t");

                    secondOperand = temp.pop();
                    firstOperand = temp.pop();

                    temp.push(firstOperand * secondOperand);
                    break;
                case "/":
 //                   System.out.print("Operate\t\t");

                    secondOperand = temp.pop();
                    firstOperand = temp.pop();

                    if (secondOperand == 0.0) {
                        throw new ArithmeticException("Cannot divide by zero!");
                    }

                    temp.push(firstOperand / secondOperand);
                    break;
                case "^":
 //                   System.out.print("Operate\t\t");

                    secondOperand = temp.pop();
                    firstOperand = temp.pop();

                    temp.push(Math.pow(firstOperand, secondOperand));
                    break;
                    
                case "a":
//                	System.out.print("Push\t\t");
                    temp.push(a);
                	break;
                default:
                
                	if(!(token.charAt(0)>='0' && token.charAt(0)<='9'))
					{
                		System.out.println("AN ERROR OCCURRED: INCORRECTLY DECLARED EQUATION ");
					}
//                    System.out.print("Push\t\t");
                    
                    double value = parseString(token);
                    
                    temp.push(value);
                    break;
            }
		}
		
		
/*		while(!equat.isEmpty())
		{
			System.out.print(equat.pop() + " ");
		}
*/		
		result = temp.pop();
//		System.out.println();
//		System.out.print(result);
		return result;
	}
	
	public double evaluate(double a, double[] params)		//Evaluates the equation only if there are parameters in the
	//equation
	{
		if(!hasParam)
		{
			System.out.println("Incorrect method usage send no parameters returning -1.0");
			return -1.0;
		}
		
		double result = 0;
		Stack<String> equat = new Stack<>(); 
		Stack<Double> temp = new Stack<>(); 
		char currentChar= ' ';
		
		for(int i = equation.length()-1; i >=0; i-=2)
		{
			currentChar = equation.charAt(i);
			if(currentChar>='0' && currentChar<='9')
			{
				String number = currentChar + "";
				
				while((i > 0) 
						&& (equation.charAt(i-1)>='0' && equation.charAt(i-1)<='9'))
				{
					i--;
					currentChar = equation.charAt(i);
					number += currentChar;
				}
				
				number = reverse(number);
//				System.out.println(number);
				equat.push(number);
			}
			else
			{
				equat.push(equation.substring(i, i + 1));
			}
		}
		
		String token = "";
		
		while(!equat.isEmpty())
		{
			token = equat.pop();
			
//			System.out.print(token + "\t");

            double secondOperand = 0.0;
            double firstOperand = 0.0;

            switch (token) {
                case "+":
//                    System.out.print("Operate\t\t");

                    secondOperand = temp.pop();
                    firstOperand = temp.pop();

                    temp.push(firstOperand + secondOperand);
                    break;
                case "-":
//                    System.out.print("Operate\t\t");

                    secondOperand = temp.pop();
                    firstOperand = temp.pop();

                    temp.push(firstOperand - secondOperand);
                    break;
                case "*":
//                    System.out.print("Operate\t\t");

                    secondOperand = temp.pop();
                    firstOperand = temp.pop();

                    temp.push(firstOperand * secondOperand);
                    break;
                case "/":
 //                   System.out.print("Operate\t\t");

                    secondOperand = temp.pop();
                    firstOperand = temp.pop();

                    if (secondOperand == 0.0) {
                        throw new ArithmeticException("Cannot divide by zero!");
                    }

                    temp.push(firstOperand / secondOperand);
                    break;
                case "^":
 //                   System.out.print("Operate\t\t");

                    secondOperand = temp.pop();
                    firstOperand = temp.pop();

                    temp.push(Math.pow(firstOperand, secondOperand));
                    break;
                    
                case "a":
//                	System.out.print("Push\t\t");
                    temp.push(a);
                	break;
                default:
                
                	
//                  System.out.print("Push\t\t");
                	
                	if(!((token.charAt(0)>='0' && token.charAt(0)<='9') || isParam(token)))
					{
                		System.out.println("AN ERROR OCCURRED: INCORRECTLY DECLARED EQUATION ");
					}
                	
                	double value = -1;
                	if(isParam(token))
                	{
                		for(int i= 0; i < paramName.length; i++)
                		{
                			if(token.equals(paramName[i]))
                			{
                				value = params[i];
                			}
                		}
                	}
                	else
                	{
                		 value = parseString(token);
                	}
                    
                    temp.push(value);
                    break;
            }
		}
		
		
/*		while(!equat.isEmpty())
		{
			System.out.print(equat.pop() + " ");
		}
*/		
		result = temp.pop();
//		System.out.println();
//		System.out.print(result);
		return result;
	}
	
	public boolean boundaryCondition()		//Returns true if the equation satisfies the boundary condition
	{
		boolean bool = true;
		if(!hasParam)
		{
			double value1 = evaluate(1);
			double value0 = evaluate(0);
			if(value1 != 0 || value0 != 1)
			{
				bool = false;
			}
		}
		else
		{
			double[][] testVals = new double [FUZZY_TEST_CASES][paramRange.length];
			for(int i = 0; i < paramRange.length; i++)
			{
					int j = 0;
					boolean getOut = true;
						
					while(j < paramRange[i].length() - 1 && getOut)
					{
						if( paramRange[i].substring(j, j + 1).equals("<") && 
								!(paramRange[i].substring(j, j + 2).equals("<=")))
						{
							char currentChar = ' ';
							if(isParam(paramRange[i].substring(j+1, j + 2)))
							{
								
								String number = "";
										
								while((j > 0) 
										&& ((paramRange[i].charAt(j-1)>='0' && 
										paramRange[i].charAt(j-1)<='9') || paramRange[i].charAt(j-1)=='-'))
								{
									j--;
									currentChar = paramRange[i].charAt(j);
									number += currentChar;
								}
										
								number = reverse(number);
								double num = parseString(number);
//								System.out.println(num);
								for(int k = 1; k <= FUZZY_TEST_CASES; k++)
								{
									testVals[k-1][i] = num + k;
								}
							}
							else
							{
								String number = "";
								
								while((j+1 < paramRange[i].length()) 
										&& ((paramRange[i].charAt(j+1)>='0' && 
										paramRange[i].charAt(j+1)<='9') || paramRange[i].charAt(j+1)=='-'))
								{
									j++;
									currentChar = paramRange[i].charAt(j);
									
									number += currentChar;
								}
										
//								System.out.println(number);
								double num = parseString(number);
								
								for(int k = 1; k <= FUZZY_TEST_CASES; k++)
								{
									testVals[k-1][i] = num - k;
								}
							}
							
							getOut = false;
						}
						else if( paramRange[i].substring(j, j + 1).equals(">") &&
								!(paramRange[i].substring(j, j + 2).equals("<=")))
						{
							char currentChar = ' ';
							if(isParam(paramRange[i].substring(j+1, j + 2)))
							{
								
								String number = "";
										
								while((j > 0) 
										&& ((paramRange[i].charAt(j-1)>='0' && 
										paramRange[i].charAt(j-1)<='9') || paramRange[i].charAt(j-1)=='-'))
								{
									j--;
									currentChar = paramRange[i].charAt(j);
									number += currentChar;
								}
										
								number = reverse(number);
								double num = parseString(number);
//								System.out.println(num);
								for(int k = 1; k <= FUZZY_TEST_CASES; k++)
								{
									testVals[k-1][i] = num - k;
								}
							}
							else
							{
								String number = "";
								
								while((j+1 < paramRange[i].length()) 
										&& ((paramRange[i].charAt(j+1)>='0' && 
										paramRange[i].charAt(j+1)<='9') || paramRange[i].charAt(j+1)=='-'))
								{
									j++;
									currentChar = paramRange[i].charAt(j);
									
									number += currentChar;
								}
										
//								System.out.println(number);
								double num = parseString(number);
								
								for(int k = 1; k <= FUZZY_TEST_CASES; k++)
								{
									testVals[k-1][i] = num + k;
//									System.out.println(testVals[k-1][i]);
								}
							}
							
							getOut = false;
						}
						else if( paramRange[i].substring(j, j + 2).equals("<="))
						{
							char currentChar = ' ';
							if(isParam(paramRange[i].substring(j+2, j + 3)))
							{
								
								String number = "";
										
								while((j > 0) 
										&& ((paramRange[i].charAt(j-1)>='0' && 
										paramRange[i].charAt(j-1)<='9') || paramRange[i].charAt(j-1)=='-'))
								{
									j--;
									currentChar = paramRange[i].charAt(j);
									number += currentChar;
								}
										
								number = reverse(number);
								double num = parseString(number);
//								System.out.println(num);
								for(int k = 0; k < FUZZY_TEST_CASES; k++)
								{
									testVals[k][i] = num + k;
//									System.out.println(testVals[k][i]);
								}
							}
							else
							{
								String number = "";
								
								
								while((j+2 < paramRange[i].length()) 
										&& ((paramRange[i].charAt(j+2)>='0' && 
										paramRange[i].charAt(j+2)<='9') || paramRange[i].charAt(j+2)=='-'))
								{
									j++;
									currentChar = paramRange[i].charAt(j+1);
									
									number += currentChar;
								}
										
//								System.out.println(number);
								double num = parseString(number);
								
								for(int k = 0; k < FUZZY_TEST_CASES; k++)
								{
									testVals[k][i] = num - k;
//									System.out.println(testVals[k][i]);
								}
							}
							
							getOut = false;
						}
						else if( paramRange[i].substring(j, j + 2).equals(">="))
						{
							char currentChar = ' ';
							if(isParam(paramRange[i].substring(j+2, j + 3)))
							{
								
								String number = "";
										
								while((j > 0) 
										&& ((paramRange[i].charAt(j-1)>='0' && 
										paramRange[i].charAt(j-1)<='9') || paramRange[i].charAt(j-1)=='-'))
								{
									j--;
									currentChar = paramRange[i].charAt(j);
									number += currentChar;
								}
										
								number = reverse(number);
								double num = parseString(number);
//								System.out.println(num);
								for(int k = 0; k < FUZZY_TEST_CASES; k++)
								{
									testVals[k][i] = num - k;
//									System.out.println(testVals[k][i]);
								}
							}
							else
							{
								String number = "";
								
								
								while((j+2 < paramRange[i].length()) 
										&& ((paramRange[i].charAt(j+2)>='0' && 
										paramRange[i].charAt(j+2)<='9') || paramRange[i].charAt(j+2)=='-'))
								{
									j++;
									currentChar = paramRange[i].charAt(j+1);
									
									number += currentChar;
								}
										
//								System.out.println(number);
								double num = parseString(number);
								
								for(int k = 0; k < FUZZY_TEST_CASES; k++)
								{
									testVals[k][i] = num + k;
//									System.out.println(testVals[k][i]);
								}
							}
							
							getOut = false;
						}
						else if(paramRange[i].substring(j, j + 1).equals("["))
						{
							String number = "";
							char currentChar = ' ';
							
							while((j+1 < paramRange[i].length()) 
									&& ((paramRange[i].charAt(j+1)>='0' && 
									paramRange[i].charAt(j+1)<='9') || paramRange[i].charAt(j+1)=='-'))
							{
								j++;
								currentChar = paramRange[i].charAt(j);
								
								number += currentChar;
							}
									
//							System.out.println(number);
							double begin = parseString(number);
							j++;
							
							number = "";
							
							while((j+1 < paramRange[i].length()) 
									&& ((paramRange[i].charAt(j+1)>='0' && 
									paramRange[i].charAt(j+1)<='9') || paramRange[i].charAt(j+1)=='-'))
							{
								j++;
								currentChar = paramRange[i].charAt(j);
								
								number += currentChar;
							}
							
//							System.out.println(number);
							
							double end = parseString(number);
							
							
							j++;
							
//							System.out.println(paramRange[i].charAt(j));
							
							if(paramRange[i].charAt(j)==')')
							{
								double increment = (end - begin)/FUZZY_TEST_CASES;
								for(int k = 0; k < FUZZY_TEST_CASES; k ++)
								{
									testVals[k][i] = begin + (k * increment);
//									System.out.println(testVals[k][i]);
								}
							}
							else if(paramRange[i].charAt(j)==']')
							{
								double increment = (end - begin)/(FUZZY_TEST_CASES-1);
								for(int k = 0; k < FUZZY_TEST_CASES; k ++)
								{
									testVals[k][i] = begin + (k * increment);
//									System.out.println(testVals[k][i]);
								}
							}
							else
							{
								System.err.println("ERROR: INVALID RANGE");
								System.exit(1);
							}
							
							getOut = false;
						}
						else if(paramRange[i].substring(j, j + 1).equals("("))
						{
							String number = "";
							char currentChar = ' ';
							
							while((j+1 < paramRange[i].length()) 
									&& ((paramRange[i].charAt(j+1)>='0' && 
									paramRange[i].charAt(j+1)<='9') || paramRange[i].charAt(j+1)=='-'))
							{
								j++;
								currentChar = paramRange[i].charAt(j);
								
								number += currentChar;
							}
									
//							System.out.println(number);
							double begin = parseString(number);
							j++;
							
							number = "";
							
							while((j+1 < paramRange[i].length()) 
									&& ((paramRange[i].charAt(j+1)>='0' && 
									paramRange[i].charAt(j+1)<='9') || paramRange[i].charAt(j+1)=='-'))
							{
								j++;
								currentChar = paramRange[i].charAt(j);
								
								number += currentChar;
							}
							
//							System.out.println(number);
							
							double end = parseString(number);
							
							
							j++;
							
//							System.out.println(paramRange[i].charAt(j));
							
							if(paramRange[i].charAt(j)==')')
							{
								double increment = (end - begin)/(FUZZY_TEST_CASES + 1);
								for(int k = 1; k <= FUZZY_TEST_CASES; k ++)
								{
									testVals[k-1][i] = begin + (k * increment);
//									System.out.println(testVals[k-1][i]);
								}
							}
							else if(paramRange[i].charAt(j)==']')
							{
								double increment = (end - begin)/(FUZZY_TEST_CASES);
								for(int k = 1; k <= FUZZY_TEST_CASES; k ++)
								{
									testVals[k-1][i] = begin + (k * increment);
//									System.out.println(testVals[k-1][i]);
								}
							}
							else
							{
								System.err.println("ERROR: INVALID RANGE");
								System.exit(1);
							}
							
							getOut = false;
						}
						else if(paramRange[i].substring(j, j + 2).equals("!="))
						{
							double num = 0;
							if(isParam(paramRange[i].substring(j+2, j + 3)))
							{
								
								String number = "";
								char currentChar = ' ';
										
								while((j > 0) 
										&& ((paramRange[i].charAt(j-1)>='0' && 
										paramRange[i].charAt(j-1)<='9') || paramRange[i].charAt(j-1)=='-'))
								{
									j--;
									currentChar = paramRange[i].charAt(j);
									number += currentChar;
								}
										
								number = reverse(number);
								num = parseString(number);
//								System.out.println(num);
							}
							else
							{
								String number = "";
								char currentChar = ' ';
								
								while((j+2 < paramRange[i].length()) 
										&& ((paramRange[i].charAt(j+2)>='0' && 
										paramRange[i].charAt(j+2)<='9') || paramRange[i].charAt(j+2)=='-'))
								{
									j++;
									currentChar = paramRange[i].charAt(j+1);
									
									number += currentChar;
								}
										
//								System.out.println(number);
								num = parseString(number);
								
							}
							for(int k = 0; k < FUZZY_TEST_CASES; k++)
							{
								if((double)(k) != num)
								{
									testVals[k][i] = k;
								}
								else
								{
									testVals[k][i] = k + .1;
								}
							}
						}
						else if(paramRange[i].substring(j, j + 1).equals("n"))
						{
							for(int k = 0; k < FUZZY_TEST_CASES; k++)
							{
								testVals[k][i] = Math.pow(k, k);
							}
						}
							
						j++;
						
					}
			}
			double value1 = 1;
			double value0 = 0;
			
			for(int r = 0; r < testVals.length; r++)
			{
			
				value1 = evaluate(1,testVals[r]);
				value0 = evaluate(0,testVals[r]);
				if(value1 != 0 || value0 != 1)
				{
					bool = false;
				}
			}
		}
		
		return bool;
	}
	
	public boolean monotonicity()		//Returns true if the equation satisfies the monotonicity condition
	{
		if(!hasParam)
		{
			for(double a = 0; a <= 1; a +=FUZZY_INCREMENTER)
			{
				for(double b = a+.001; b <= 1; b +=FUZZY_INCREMENTER)
				{
					if(!(evaluate(a) >= evaluate(b)))
					{
						return false;
					}
				}
			}
		}
		else
		{
			double[][] testVals = new double [FUZZY_TEST_CASES][paramRange.length];
			for(int i = 0; i < paramRange.length; i++)
			{
					int j = 0;
					boolean getOut = true;
						
					while(j < paramRange[i].length() - 1 && getOut)
					{
						if( paramRange[i].substring(j, j + 1).equals("<") && 
								!(paramRange[i].substring(j, j + 2).equals("<=")))
						{
							char currentChar = ' ';
							if(isParam(paramRange[i].substring(j+1, j + 2)))
							{
								
								String number = "";
										
								while((j > 0) 
										&& ((paramRange[i].charAt(j-1)>='0' && 
										paramRange[i].charAt(j-1)<='9') || paramRange[i].charAt(j-1)=='-'))
								{
									j--;
									currentChar = paramRange[i].charAt(j);
									number += currentChar;
								}
										
								number = reverse(number);
								double num = parseString(number);
//								System.out.println(num);
								for(int k = 1; k <= FUZZY_TEST_CASES; k++)
								{
									testVals[k-1][i] = num + k;
								}
							}
							else
							{
								String number = "";
								
								while((j+1 < paramRange[i].length()) 
										&& ((paramRange[i].charAt(j+1)>='0' && 
										paramRange[i].charAt(j+1)<='9') || paramRange[i].charAt(j+1)=='-'))
								{
									j++;
									currentChar = paramRange[i].charAt(j);
									
									number += currentChar;
								}
										
//								System.out.println(number);
								double num = parseString(number);
								
								for(int k = 1; k <= FUZZY_TEST_CASES; k++)
								{
									testVals[k-1][i] = num - k;
								}
							}
							
							getOut = false;
						}
						else if( paramRange[i].substring(j, j + 1).equals(">") &&
								!(paramRange[i].substring(j, j + 2).equals("<=")))
						{
							char currentChar = ' ';
							if(isParam(paramRange[i].substring(j+1, j + 2)))
							{
								
								String number = "";
										
								while((j > 0) 
										&& ((paramRange[i].charAt(j-1)>='0' && 
										paramRange[i].charAt(j-1)<='9') || paramRange[i].charAt(j-1)=='-'))
								{
									j--;
									currentChar = paramRange[i].charAt(j);
									number += currentChar;
								}
										
								number = reverse(number);
								double num = parseString(number);
//								System.out.println(num);
								for(int k = 1; k <= FUZZY_TEST_CASES; k++)
								{
									testVals[k-1][i] = num - k;
								}
							}
							else
							{
								String number = "";
								
								while((j+1 < paramRange[i].length()) 
										&& ((paramRange[i].charAt(j+1)>='0' && 
										paramRange[i].charAt(j+1)<='9') || paramRange[i].charAt(j+1)=='-'))
								{
									j++;
									currentChar = paramRange[i].charAt(j);
									
									number += currentChar;
								}
										
//								System.out.println(number);
								double num = parseString(number);
								
								for(int k = 1; k <= FUZZY_TEST_CASES; k++)
								{
									testVals[k-1][i] = num + k;
//									System.out.println(testVals[k-1][i]);
								}
							}
							
							getOut = false;
						}
						else if( paramRange[i].substring(j, j + 2).equals("<="))
						{
							char currentChar = ' ';
							if(isParam(paramRange[i].substring(j+2, j + 3)))
							{
								
								String number = "";
										
								while((j > 0) 
										&& ((paramRange[i].charAt(j-1)>='0' && 
										paramRange[i].charAt(j-1)<='9') || paramRange[i].charAt(j-1)=='-'))
								{
									j--;
									currentChar = paramRange[i].charAt(j);
									number += currentChar;
								}
										
								number = reverse(number);
								double num = parseString(number);
//								System.out.println(num);
								for(int k = 0; k < FUZZY_TEST_CASES; k++)
								{
									testVals[k][i] = num + k;
//									System.out.println(testVals[k][i]);
								}
							}
							else
							{
								String number = "";
								
								
								while((j+2 < paramRange[i].length()) 
										&& ((paramRange[i].charAt(j+2)>='0' && 
										paramRange[i].charAt(j+2)<='9') || paramRange[i].charAt(j+2)=='-'))
								{
									j++;
									currentChar = paramRange[i].charAt(j+1);
									
									number += currentChar;
								}
										
//								System.out.println(number);
								double num = parseString(number);
								
								for(int k = 0; k < FUZZY_TEST_CASES; k++)
								{
									testVals[k][i] = num - k;
//									System.out.println(testVals[k][i]);
								}
							}
							
							getOut = false;
						}
						else if( paramRange[i].substring(j, j + 2).equals(">="))
						{
							char currentChar = ' ';
							if(isParam(paramRange[i].substring(j+2, j + 3)))
							{
								
								String number = "";
										
								while((j > 0) 
										&& ((paramRange[i].charAt(j-1)>='0' && 
										paramRange[i].charAt(j-1)<='9') || paramRange[i].charAt(j-1)=='-'))
								{
									j--;
									currentChar = paramRange[i].charAt(j);
									number += currentChar;
								}
										
								number = reverse(number);
								double num = parseString(number);
//								System.out.println(num);
								for(int k = 0; k < FUZZY_TEST_CASES; k++)
								{
									testVals[k][i] = num - k;
//									System.out.println(testVals[k][i]);
								}
							}
							else
							{
								String number = "";
								
								
								while((j+2 < paramRange[i].length()) 
										&& ((paramRange[i].charAt(j+2)>='0' && 
										paramRange[i].charAt(j+2)<='9') || paramRange[i].charAt(j+2)=='-'))
								{
									j++;
									currentChar = paramRange[i].charAt(j+1);
									
									number += currentChar;
								}
										
//								System.out.println(number);
								double num = parseString(number);
								
								for(int k = 0; k < FUZZY_TEST_CASES; k++)
								{
									testVals[k][i] = num + k;
//									System.out.println(testVals[k][i]);
								}
							}
							
							getOut = false;
						}
						else if(paramRange[i].substring(j, j + 1).equals("["))
						{
							String number = "";
							char currentChar = ' ';
							
							while((j+1 < paramRange[i].length()) 
									&& ((paramRange[i].charAt(j+1)>='0' && 
									paramRange[i].charAt(j+1)<='9') || paramRange[i].charAt(j+1)=='-'))
							{
								j++;
								currentChar = paramRange[i].charAt(j);
								
								number += currentChar;
							}
									
//							System.out.println(number);
							double begin = parseString(number);
							j++;
							
							number = "";
							
							while((j+1 < paramRange[i].length()) 
									&& ((paramRange[i].charAt(j+1)>='0' && 
									paramRange[i].charAt(j+1)<='9') || paramRange[i].charAt(j+1)=='-'))
							{
								j++;
								currentChar = paramRange[i].charAt(j);
								
								number += currentChar;
							}
							
//							System.out.println(number);
							
							double end = parseString(number);
							
							
							j++;
							
//							System.out.println(paramRange[i].charAt(j));
							
							if(paramRange[i].charAt(j)==')')
							{
								double increment = (end - begin)/FUZZY_TEST_CASES;
								for(int k = 0; k < FUZZY_TEST_CASES; k ++)
								{
									testVals[k][i] = begin + (k * increment);
//									System.out.println(testVals[k][i]);
								}
							}
							else if(paramRange[i].charAt(j)==']')
							{
								double increment = (end - begin)/(FUZZY_TEST_CASES-1);
								for(int k = 0; k < FUZZY_TEST_CASES; k ++)
								{
									testVals[k][i] = begin + (k * increment);
//									System.out.println(testVals[k][i]);
								}
							}
							else
							{
								System.err.println("ERROR: INVALID RANGE");
								System.exit(1);
							}
							
							getOut = false;
						}
						else if(paramRange[i].substring(j, j + 1).equals("("))
						{
							String number = "";
							char currentChar = ' ';
							
							while((j+1 < paramRange[i].length()) 
									&& ((paramRange[i].charAt(j+1)>='0' && 
									paramRange[i].charAt(j+1)<='9') || paramRange[i].charAt(j+1)=='-'))
							{
								j++;
								currentChar = paramRange[i].charAt(j);
								
								number += currentChar;
							}
									
//							System.out.println(number);
							double begin = parseString(number);
							j++;
							
							number = "";
							
							while((j+1 < paramRange[i].length()) 
									&& ((paramRange[i].charAt(j+1)>='0' && 
									paramRange[i].charAt(j+1)<='9') || paramRange[i].charAt(j+1)=='-'))
							{
								j++;
								currentChar = paramRange[i].charAt(j);
								
								number += currentChar;
							}
							
//							System.out.println(number);
							
							double end = parseString(number);
							
							
							j++;
							
//							System.out.println(paramRange[i].charAt(j));
							
							if(paramRange[i].charAt(j)==')')
							{
								double increment = (end - begin)/(FUZZY_TEST_CASES + 1);
								for(int k = 1; k <= FUZZY_TEST_CASES; k ++)
								{
									testVals[k-1][i] = begin + (k * increment);
//									System.out.println(testVals[k-1][i]);
								}
							}
							else if(paramRange[i].charAt(j)==']')
							{
								double increment = (end - begin)/(FUZZY_TEST_CASES);
								for(int k = 1; k <= FUZZY_TEST_CASES; k ++)
								{
									testVals[k-1][i] = begin + (k * increment);
//									System.out.println(testVals[k-1][i]);
								}
							}
							else
							{
								System.err.println("ERROR: INVALID RANGE");
								System.exit(1);
							}
							
							getOut = false;
						}
						else if(paramRange[i].substring(j, j + 2).equals("!="))
						{
							double num = 0;
							if(isParam(paramRange[i].substring(j+2, j + 3)))
							{
								
								String number = "";
								char currentChar = ' ';
										
								while((j > 0) 
										&& ((paramRange[i].charAt(j-1)>='0' && 
										paramRange[i].charAt(j-1)<='9') || paramRange[i].charAt(j-1)=='-'))
								{
									j--;
									currentChar = paramRange[i].charAt(j);
									number += currentChar;
								}
										
								number = reverse(number);
								num = parseString(number);
//								System.out.println(num);
							}
							else
							{
								String number = "";
								char currentChar = ' ';
								
								while((j+2 < paramRange[i].length()) 
										&& ((paramRange[i].charAt(j+2)>='0' && 
										paramRange[i].charAt(j+2)<='9') || paramRange[i].charAt(j+2)=='-'))
								{
									j++;
									currentChar = paramRange[i].charAt(j+1);
									
									number += currentChar;
								}
										
//								System.out.println(number);
								num = parseString(number);
								
							}
							for(int k = 0; k < FUZZY_TEST_CASES; k++)
							{
								if((double)(k) != num)
								{
									testVals[k][i] = k;
								}
								else
								{
									testVals[k][i] = k + .1;
								}
							}
						}
						else if(paramRange[i].substring(j, j + 1).equals("n"))
						{
							for(int k = 0; k < FUZZY_TEST_CASES; k++)
							{
								testVals[k][i] = Math.pow(k, k);
							}
						}
							
						j++;
						
					}
			}
			for(double a = 0; a <= 1; a +=FUZZY_INCREMENTER)
			{
				for(double b = a+.001; b <= 1; b +=FUZZY_INCREMENTER)
				{
					for(int i = 0; i < FUZZY_TEST_CASES; i++)
					{
						if(!(evaluate(a,testVals[i]) >= evaluate(b,testVals[i])))
						{
							return false;
						}
					}
				}
			}
		}
		
		return true;
	}
	
	private boolean isParam(String token) 		//Takes a string checks if it is in the paramName array
	{
		for(int i = 0; i < paramName.length; i++)
		{
			if(token.equals(paramName[i]))
			{
				return true;
			}
		}
		
		return false;
	}

	private String reverse(String str) 		//Reverses the string str and returns it
	{
		int i = str.length()-1;
		String string = "";
		while(i >= 0)
		{
			string += str.substring(i,i+1);
			i--;
		}
		
		return string;
	}

	private double parseString(String token) // made to determine the value of a string
	{
		double value = 0,temp = 0;
		int count = 0;
		char currentChar = ' ';
		
		
		for(int i = token.length() - 1; i >=0; i--)
		{
			currentChar = token.charAt(i);
			if(currentChar >='0' && currentChar <= '9')
			{
				temp = Double.parseDouble(token.substring(i, i+1));
				for(int j = 0; j < count; j++)
				{
					temp *=10;
				}
				value += temp;
				count++;
			}
		}
		
		if(token.charAt(0)=='-')
		{
			value *=-1;
		}
		
		
		return value;
	}

	public void printEquation()		//prints the equation
	{
		System.out.println(equation);
	}
	
	public void printParamNames()		//prints out the names of parameters if there are any
	{
		if(paramName==null)
		{
			System.out.println("There are no parameters in the equation: ");
		}
		else
		{
			for(int i=0; i < paramName.length; i++)
			{
				System.out.print(paramName[i] + " ");
			}
			System.out.println();
		}
	}
}

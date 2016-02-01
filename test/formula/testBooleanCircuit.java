package test.formula;

import uran.formula.cnf.*;


public class testBooleanCircuit{
	
	public static void main (String args[]){
		ColorPrint.println("*****Testing for Boolean Circuit Translation*****\n\n",Color.WHITE);
		Case1();
		Case2();
		Case3();
	}

	public static void Case1(){
		ColorPrint.println("testing case 1",Color.BLUE);
		BooleanVariable v1 = new BooleanVariable("v1");
		BooleanVariable v2 = new BooleanVariable("v2");
		BooleanVariable v3 = new BooleanVariable("v3");
		BooleanVariable v4 = new BooleanVariable("v4");

		BinaryGate gate1 = new BinaryGate(Operator.AND, v1,v2);
		BinaryGate gate2 = new BinaryGate(Operator.AND, v3,v4);
		BinaryGate gate3 = new BinaryGate(Operator.AND, gate1, gate2);
		
		ColorPrint.println(
			gate3.toString(), Color.WHITE
		);
		ColorPrint.println("leaving case 1\n\n",Color.BLUE);
	}

	public static void Case2(){
		ColorPrint.println("testing case 2 (CNF translation debug 1)",Color.BLUE);
		BooleanVariable v1 = new BooleanVariable("v1");
		BooleanVariable v2 = new BooleanVariable("v2");
		BooleanVariable v3 = new BooleanVariable("v3");
		BooleanVariable v4 = new BooleanVariable("v4");

		BinaryGate gate1 = new BinaryGate(Operator.AND, v1,v2);
		BinaryGate gate2 = new BinaryGate(Operator.AND, new NotGate(v3),v4);
		BinaryGate gate3 = new BinaryGate(Operator.AND, gate1, gate2);
		BinaryGate gate4 = new BinaryGate(Operator.OR, gate3, gate2);

		BooleanCircuit[] circuits = new BooleanCircuit[4];
		circuits[0]=gate1;
		circuits[1]=gate2;
		circuits[2]=gate3;
		circuits[3]=gate4;

		CNFTranslator cnf = new CNFTranslator(circuits, "./test/test1.cnf");
		long start = System.currentTimeMillis();
		ColorPrint.println("generating cnf...",Color.WHITE);
		cnf.translate();
		cnf.generate();
		ColorPrint.println("successfully generate cnf.",Color.WHITE);
		
		ColorPrint.println("#variables:"+cnf.noOfVariables(),Color.WHITE);
		ColorPrint.println("#clauses:"+cnf.noOfClauses(),Color.WHITE);
		ColorPrint.println("Time took:"+(System.currentTimeMillis()-start)+" milliseconds.",Color.WHITE);
		ColorPrint.println("leaving case 2\n\n",Color.BLUE);
	}

	public static void Case3(){
		ColorPrint.println("testing case 3 (CNF translation debug 2)",Color.BLUE);
		BooleanVariable v1 = new BooleanVariable("v1");
		BooleanVariable v2 = new BooleanVariable("v2");
		BooleanVariable v3 = new BooleanVariable("v3");
		BooleanVariable v4 = new BooleanVariable("v4");		

		BinaryGate gate1 = new BinaryGate(Operator.XOR, v1, v2);
		BinaryGate gate2 = new BinaryGate(Operator.OR, v3, v4);

		BooleanCircuit[] circuits = new BooleanCircuit[2];

		circuits[0]=gate1;
		circuits[1]=gate2;
		CNFTranslator cnf = new CNFTranslator(circuits,"./test/test2.cnf");

		long start = System.currentTimeMillis();
		ColorPrint.println("generating cnf...",Color.WHITE);
		cnf.translate();
		cnf.generate();
		ColorPrint.println("successfully generate cnf.",Color.WHITE);
		
		ColorPrint.println("#variables:"+cnf.noOfVariables(),Color.WHITE);
		ColorPrint.println("#clauses:"+cnf.noOfClauses(),Color.WHITE);
		ColorPrint.println("Time took:"+(System.currentTimeMillis()-start)+" milliseconds.",Color.WHITE);
		ColorPrint.println("leaving case 3\n\n",Color.BLUE);		

	}


}

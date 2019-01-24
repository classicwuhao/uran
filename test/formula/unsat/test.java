package uran.test.formula.unsat;

import uran.test.util.*;
import uran.formula.*;
import uran.formula.value.*;
import uran.formula.type.*;
import uran.formula.smt2.*;
import uran.formula.bv.*;
import uran.solver.*;
import java.util.*;
import com.microsoft.z3.*;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


public final class test{

	private final String Z3="/home/haowu/z3/build/z3 -in";
	
	public static void main (String args[]){
		ColorPrint.println("*****Unsat core Test Suite 1*****\n",Color.WHITE);
	}
	
	@Test
	public void test1(){
		test test = new test();
		assertEquals(Result.UNSAT,test.Case1());
	}
	

	@Test
	public void test2(){
		test test = new test();
		assertEquals(Result.UNSAT,test.Case2());
	}

	public Result Case1(){
		long timer = System.currentTimeMillis();
		FunctionFactory factory = new FunctionFactory(512, 0.75f);		
		Constant x1 = factory.createConstant("x1", new Bool());
		Constant x2 = factory.createConstant("x2", new Bool());
				
		AndFormula formula1 = new AndFormula (x1, x2);
		NegFormula formula2 = new NegFormula (x1);
		
		List<AbstractFormula> formulas = new ArrayList<AbstractFormula>();
		formulas.add (new LabeledFormula (formula1,"t1"));
		formulas.add (new LabeledFormula (formula2,"t2"));
		
		SMT2Writer writer = new SMT2Writer(factory, formulas);
		SolverLauncher z3 = new SolverLauncher (Z3,writer,SolverLauncher.PRODUCE_UNSAT_CORES);
		z3.launch();
		ColorPrint.println("Time cost:"+(System.currentTimeMillis()-timer)+" ms",Color.WHITE);
		List<AbstractFormula> cores = z3.cores();
		for (AbstractFormula f : cores) System.out.println(f.toString());
	
		return Result.UNSAT;
	}

	public Result Case2(){
		long timer = System.currentTimeMillis();
		FunctionFactory factory = new FunctionFactory(512, 0.75f);
		Constant x = factory.createConstant("x", new Int());
		Constant y = factory.createConstant("y", new Int());
		Constant z = factory.createConstant("z", new Int());

		ComparisonFormula formula1 = new ComparisonFormula(Connective.LEQ,x,new NumLiteral(new IntValue(0)));
		ComparisonFormula formula2 = new ComparisonFormula(Connective.LESS,z,new NumLiteral(new IntValue(0)));
		ComparisonFormula formula3 = new ComparisonFormula(Connective.GREATER, 
			new ArithmeticFormula(Connective.PLUS,x,z), new NumLiteral(new IntValue(0)));
		
		ComparisonFormula formula4 = new ComparisonFormula(Connective.LESS,y,new NumLiteral(new IntValue(0)));
		ComparisonFormula formula5 = new ComparisonFormula(Connective.GREATER, 
				new ArithmeticFormula(Connective.PLUS,x,y), new NumLiteral(new IntValue(0)));
		
		List<AbstractFormula> formulas = new ArrayList<AbstractFormula>();	
		formulas.add(new LabeledFormula(formula1,"t1"));
		formulas.add(new LabeledFormula(formula2,"t2"));
		formulas.add(new LabeledFormula(formula3,"t3"));
		formulas.add(new LabeledFormula(formula4,"t4"));
		formulas.add(new LabeledFormula(formula5,"t5"));

		SMT2Writer writer = new SMT2Writer(factory, formulas);
		SolverLauncher z3 = new SolverLauncher (Z3,writer,SolverLauncher.PRODUCE_UNSAT_CORES);
		z3.launch();
		ColorPrint.println("Time cost:"+(System.currentTimeMillis()-timer)+" ms",Color.WHITE);
		List<AbstractFormula> cores = z3.cores();
		for (AbstractFormula f : cores) System.out.println(f.toString());

		return Result.UNSAT;
	}

	private void generate(){


	}



	
}

package uran.test.formula.unsat;

import uran.test.util.*;
import uran.formula.*;
import uran.formula.value.*;
import uran.formula.type.*;
import uran.formula.smt2.*;
import uran.formula.bv.*;
import uran.solver.*;
import java.util.*;
import java.io.*;
import com.microsoft.z3.*;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


public final class test{

	private String Z3="/home/haowu/z3/build/z3";
	private final String Z3_STD_IN = " -in ";

	public static void main (String args[]){
		ColorPrint.println("*****Unsat core Test Suite 1*****\n",Color.WHITE);
		//setZ3Path();
	}
	
	@Test
	public void test0(){
		test test = new test();
		assertEquals(Result.UNSAT,test.testZ3());
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

	@Test
	public void tes3(){
		test test = new test();
		assertEquals(Result.UNSAT,test.Case3());
	}
	
	private void setZ3Path(){
		String str = System.getProperty("os.name");
		if (str.indexOf("Mac OS X")!=-1)
			Z3 = "/Users/haowu/z3/build/z3";
		else
			Z3= "/home/haowu/z3/build/z3";
	}

	private Result testZ3(){
		setZ3Path();
		/* check if Z3 exists or not */
		Result result = Result.UNSAT;
		/* do a simple SMT solving */

		File f = new File (Z3);
		if (f.exists() && !f.isDirectory()){
			List<AbstractFormula> formulas = new ArrayList<AbstractFormula>();
			FunctionFactory factory = new FunctionFactory(512, 0.75f);
			Constant a = factory.createConstant("a", new Bool());
			Constant b = factory.createConstant("b", new Bool());
			AbstractFormula formula1 = new AndFormula (a,b);
			AbstractFormula formula2 = new NegFormula(new OrFormula (new NegFormula (a),new NegFormula(b)));
			formulas.add (new LabeledFormula(new NegFormula(new EqFormula(formula1, formula2)),"t1"));
			SMT2Writer writer = new SMT2Writer(factory, formulas);
			SolverLauncher z3 = new SolverLauncher (Z3+Z3_STD_IN,writer,SolverLauncher.PRODUCE_UNSAT_CORES);
			result = z3.launch();
			for (AbstractFormula formula :  z3.cores()) ColorPrint.print(formula.toSMT2(),Color.BLUE);
		}
		else{
			result = Result.SAT;
			//do nothing;
			ColorPrint.print("z3 not found.",Color.RED);
		}

		return result;
	}

	public Result Case1(){
		setZ3Path();
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
		SolverLauncher z3 = new SolverLauncher (Z3+Z3_STD_IN,writer,SolverLauncher.PRODUCE_UNSAT_CORES);
		z3.launch();
		ColorPrint.println("Time cost:"+(System.currentTimeMillis()-timer)+" ms",Color.WHITE);
		List<AbstractFormula> cores = z3.cores();
		for (AbstractFormula f : cores) System.out.println(f.toString());
	
		return Result.UNSAT;
	}

	public Result Case2(){
		setZ3Path();
		long timer = System.currentTimeMillis();
		FunctionFactory factory = new FunctionFactory(512, 0.75f);
		Constant x = factory.createConstant("x", new Int());
		Constant y = factory.createConstant("y", new Int());
		Constant z = factory.createConstant("z", new Int());
		List<AbstractFormula> subset = new ArrayList<AbstractFormula>();

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
		SolverLauncher z3 = new SolverLauncher (Z3+Z3_STD_IN,writer,SolverLauncher.PRODUCE_UNSAT_CORES);
		Result result = z3.launch();
		ColorPrint.println("Time cost:"+(System.currentTimeMillis()-timer)+" ms",Color.WHITE);
		List<AbstractFormula> cores = z3.cores();
		
		for (AbstractFormula f : cores){
			formulas.remove(f);
			if (solve(formulas)==Result.UNSAT) continue;
			subset.add(f);
		}

		ColorPrint.print("core:(",Color.BLUE);
		for (AbstractFormula f: cores) ColorPrint.print(((LabeledFormula)f).label()+" ",Color.RED);
		ColorPrint.println(")",Color.BLUE);

		return result;
	}

	private Result Case3(){
		setZ3Path();
		long timer = System.currentTimeMillis();
		FunctionFactory factory = new FunctionFactory(512, 0.75f);
		Constant a = factory.createConstant("a", new Bool());
		Constant b = factory.createConstant("b", new Bool());
		List<AbstractFormula> formulas = new ArrayList<AbstractFormula>();	
		
		List<AbstractFormula> subset = new ArrayList<AbstractFormula>();
		
		AbstractFormula formula0 = new EqFormula(a,new BoolLiteral(true));
		AbstractFormula formula1 = new OrFormula(new NegFormula(a),b);
		AbstractFormula formula2 = new NegFormula(b);
		AbstractFormula formula3 = new NegFormula(a);

		/*subset.add (formula0);
		subset.add (formula1);
		subset.add (formula2);
		subset.add (formula3);*/

		formulas.add (new LabeledFormula(formula0,"c1"));
		formulas.add (new LabeledFormula(formula1,"c2"));
		formulas.add (new LabeledFormula(formula2,"c3"));
		formulas.add (new LabeledFormula(formula3,"c4"));

		SMT2Writer writer = new SMT2Writer(factory, formulas);
		SolverLauncher z3 = new SolverLauncher (Z3+Z3_STD_IN,writer,SolverLauncher.PRODUCE_UNSAT_CORES);
		z3.launch();
		ColorPrint.println("Time cost:"+(System.currentTimeMillis()-timer)+" ms",Color.WHITE);

		List<AbstractFormula> cores = z3.cores();

		for (AbstractFormula f : cores){
			formulas.remove(f);
			if (solve(formulas)==Result.UNSAT) continue;
			subset.add(f);
		}

		ColorPrint.print("core:(",Color.BLUE);
		for (AbstractFormula f: cores) ColorPrint.print(((LabeledFormula)f).label()+" ",Color.RED);
		ColorPrint.println(")",Color.BLUE);

		return Result.UNSAT;
	}

	private Result solve(List<AbstractFormula> formulas){
		long timer = System.currentTimeMillis();
		FunctionFactory factory = new FunctionFactory(512, 0.75f);
		SMT2Writer writer = new SMT2Writer(factory, formulas);
		SolverLauncher z3 = new SolverLauncher (Z3+Z3_STD_IN,writer,SolverLauncher.PRODUCE_UNSAT_CORES);
		Result r = z3.launch();
		ColorPrint.println("Time cost:"+(System.currentTimeMillis()-timer)+" ms",Color.WHITE);
		return r;
	}


	
}

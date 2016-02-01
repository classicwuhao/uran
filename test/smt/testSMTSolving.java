package test.smt;

import uran.formula.*;
import uran.formula.value.*;
import uran.formula.type.*;
import uran.formula.smt2.*;
import uran.solver.*;
import test.formula.*;
import java.util.*;

public class testSMTSolving{

	public static void main (String args[]){
		ColorPrint.println("*****Testing for Z3 SMT Solving*****\n\n",Color.WHITE);
		long current = System.currentTimeMillis();
		Case1();Case2();
		ColorPrint.println("Total time cost:"+(System.currentTimeMillis()-current)+" ms",Color.WHITE);
	}

	public static void Case1(){
		ColorPrint.println("testing case 1",Color.BLUE);
		long timer = System.currentTimeMillis();
		List<AbstractFormula> formulas = new ArrayList<AbstractFormula>();
		FunctionFactory factory = new FunctionFactory(512, 0.75f);
		
		Constant b1 = factory.createConstant("b1", new Bool());	
		Constant b2 = factory.createConstant("b2", new Bool());	
		Constant b3 = factory.createConstant("b3", new Bool());	
		Constant b4 = factory.createConstant("b4", new Bool());
		Constant x1 = factory.createConstant("x1", new Int());
		Constant x2 = factory.createConstant("x2", new Int());
		formulas.add (FormulaBuilder.one(b1,b2,b3,b4));
		formulas.add (FormulaBuilder.sum(5, x1,x2));
		
		SMT2Writer writer = new SMT2Writer("./test/test_cases/case1.smt2",factory, formulas);
		Z3SMT2Solver solver = new Z3SMT2Solver(writer);
		Result result = solver.solve();
		ColorPrint.println("Time cost:"+(System.currentTimeMillis()-timer)+" ms",Color.WHITE);
		ColorPrint.println(result.toString(),Color.WHITE);
		ColorPrint.println("leaving case 1",Color.BLUE);
	}

	public static void Case2(){
		ColorPrint.println("testing case 2",Color.BLUE);
		long timer = System.currentTimeMillis();
		int k=0;
		Result result;
		List<AbstractFormula> formulas = new ArrayList<AbstractFormula>();
		List<AbstractFormula> negs = new ArrayList<AbstractFormula>();
		FunctionFactory factory = new FunctionFactory(512, 0.75f);
		
		Constant x1 = factory.createConstant("x1", new Int());	
		Constant x2 = factory.createConstant("x2", new Int());	
		Constant x3 = factory.createConstant("x3", new Int());	
		Constant x4 = factory.createConstant("x4", new Int());
		Constant b1 = factory.createConstant("b1", new Bool());
		Constant b2 = factory.createConstant("b2", new Bool());
		Function f1 = factory.createFunction("f1", new Int(), new Int(), new Int(), new Int());
		
		formulas.add(FormulaBuilder.sum(2,x1,x2,x3,x4));
		formulas.add(FormulaBuilder.range(0,1,x1,true));
		formulas.add(FormulaBuilder.range(0,1,x2,true));
		formulas.add(FormulaBuilder.range(0,1,x3,true));
		formulas.add(FormulaBuilder.range(0,1,x4,true));
		formulas.add(new EqFormula(f1.apply(x1,x2,x3), new NumLiteral(4)));
		formulas.add(FormulaBuilder.some(b1,b2));
		SMT2Writer writer = new SMT2Writer ("./test/test_cases/case2.smt2",factory, formulas);
		Z3SMT2Solver solver = new Z3SMT2Solver(writer);
		while (solver.solve()!=Result.UNSAT){
			ColorPrint.println(writer.getFactory().toString(),Color.WHITE);
			k++;
			writer.append(writer.getFactory().negConstants());
		}
		ColorPrint.println("Total solutions:"+k, Color.WHITE);
		ColorPrint.println("Time cost:"+(System.currentTimeMillis()-timer)+" ms",Color.WHITE);
	}
	
}

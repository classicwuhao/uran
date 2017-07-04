package uran.test.formula.array;
import uran.test.util.*;
import uran.formula.*;
import uran.formula.value.*;
import uran.formula.type.*;
import uran.formula.smt2.*;
import uran.formula.bv.*;
import uran.formula.array.*;
import uran.solver.*;
import java.util.*;
import com.microsoft.z3.*;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public final class test_array{

	public static void main (String args[]){
		ColorPrint.println("*****Array Test Set 1*****\n",Color.WHITE);
	}

	@Test
	public void test1(){
		test_array test = new test_array();
		assertEquals(Result.SAT ,test.Case1());
	}
	
	@Test
	public void test2(){
		test_array test = new test_array();
		assertEquals(Result.SAT, test.Case2());
	}
	
	public Result Case1(){
	
		ColorPrint.println("*****Array Test Case 1*****\n",Color.WHITE);
		long timer = System.currentTimeMillis();
		FunctionFactory factory = new FunctionFactory(512, 0.75f);
		//ArrayEx<Int, Int> a1 = new ArrayEx<Int, Int>("a1");
		//ArrayEx<Int, Bool> a2 = new ArrayEx<Int, Bool>("a2");

		//factory.registerArray(a1);factory.registerArray(a2);
		ArrayEx<Int, Int> a1 = factory.createArray("a1",Int.class,Int.class);
		ArrayEx<Int, Bool> a2 = factory.createArray("a2",Int.class,Bool.class);
		
		List<AbstractFormula> formulas = new ArrayList<AbstractFormula>();
		formulas.add(new EqFormula (new SelectFormula(a1,3), new NumLiteral(5)));
		
		SMT2Writer writer = new SMT2Writer("./test/array_test1.smt2", factory, formulas);
		Z3SMT2Solver solver = new Z3SMT2Solver(writer);
		
		Result result = solver.solve();
		
		ColorPrint.println(factory.toString(),Color.WHITE);
		ColorPrint.println("Time cost:"+(System.currentTimeMillis()-timer)+" ms",Color.WHITE);
		ColorPrint.println(result.toString(),Color.WHITE);
		ColorPrint.println("*****Leave Array Test Case 1*****\n",Color.WHITE);
		return result;
		
	}
	
	public Result Case2(){
		ColorPrint.println("*****Array Test Case 2*****\n",Color.WHITE);
		long timer = System.currentTimeMillis();
		FunctionFactory factory = new FunctionFactory(512, 0.75f);
		//ArrayEx<Int, Int> a1 = new ArrayEx<Int, Int>("a1");
		//ArrayEx<Int, Bool> a2 = new ArrayEx<Int, Bool>("a2");

		//factory.registerArray(a1);factory.registerArray(a2);
		ArrayEx<Int, Int> a1 = factory.createArray("a1",Int.class,Int.class);
		
		List<AbstractFormula> formulas = new ArrayList<AbstractFormula>();
		formulas.add(new EqFormula (new SelectFormula(a1,3), new NumLiteral(5)));
		formulas.add(new EqFormula(new StoreFormula(a1,3,new IntValue(5)),a1));
		
		SMT2Writer writer = new SMT2Writer("./test/array_test2.smt2", factory, formulas);
		Z3SMT2Solver solver = new Z3SMT2Solver(writer);
		
		Result result = solver.solve();
		ColorPrint.println(factory.toString(),Color.WHITE);
		ColorPrint.println("Time cost:"+(System.currentTimeMillis()-timer)+" ms",Color.WHITE);
		ColorPrint.println(result.toString(),Color.WHITE);
		ColorPrint.println("*****Leave Array Test Case 2*****\n",Color.WHITE);
		return result;
	}
	
		
	
}

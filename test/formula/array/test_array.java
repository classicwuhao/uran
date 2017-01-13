package test.formula.array;

import uran.formula.*;
import uran.formula.value.*;
import uran.formula.type.*;
import uran.formula.smt2.*;
import uran.formula.bv.*;
import uran.formula.array.*;
import uran.solver.*;
import java.util.*;
import com.microsoft.z3.*;
import test.formula.*;

public final class test_array{

	public static void main (String args[]){
		ColorPrint.println("*****Array Test Set 1*****\n",Color.WHITE);
		case1();
	}

	public static void case1(){
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
		ColorPrint.println("Time cost:"+(System.currentTimeMillis()-timer)+" ms",Color.WHITE);
		ColorPrint.println(result.toString(),Color.WHITE);
		
	}
	
		
	
}

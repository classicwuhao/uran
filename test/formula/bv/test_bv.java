package uran.test.formula.bv;

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

public final class test_bv{
	public static void main (String args[]){
		ColorPrint.println("*****Bit Vector Test Suite 1*****\n",Color.WHITE);		
	}
	
	
	@Test
	public void test1(){
		test_bv test = new test_bv();
		assertEquals(Result.UNSAT,test.Case1());
	}
	
	@Test
	public void test2(){
		test_bv test = new test_bv();
		assertEquals(Result.UNSAT, test.Case2());
	}
	
	@Test
	public void test3(){
		test_bv test = new test_bv();
		assertEquals(Result.UNSAT, test.Case3());
	}
	
	@Test
	public void test4(){
		test_bv test = new test_bv();
		assertEquals(Result.UNSAT, test.Case4());
	}	
	
	@Test
	public void test5(){
		test_bv test = new test_bv();
		assertEquals(Result.SAT, test.Case5());
	}	
	
	
	public Result Case1(){
		long timer = System.currentTimeMillis();
		BinaryLiteral l = new BinaryLiteral(240);
		ColorPrint.println("entering case 1",Color.BLUE);
		ColorPrint.println("display binary literal:"+l,Color.BLUE);
		BV_HexLiteral l1 = new BV_HexLiteral(20);
		ColorPrint.println("display hexadecimal literal:"+l1,Color.BLUE);
		BV_ArithmeticFormula bv_f = new BV_ArithmeticFormula(BV_Connective.ADD,l,l1);
		ColorPrint.println("formula:"+bv_f.toSMT2(),Color.BLUE);
		BV_AndFormula bv_f1 = new BV_AndFormula (l,l1);
		ColorPrint.println("formula:"+bv_f1.toSMT2(),Color.BLUE);
		BV_OrFormula bv_f2 = new BV_OrFormula (l,l1);
		ColorPrint.println("formula:"+bv_f2.toSMT2(),Color.BLUE);
		BV_XorFormula bv_f3 = new BV_XorFormula (l,l1);
		ColorPrint.println("formula:"+ new BV_NotFormula(l).toSMT2(),Color.BLUE);
		
		FunctionFactory factory = new FunctionFactory(512, 0.75f);
		BitVector bv1 = factory.createBitVector("bv1",4);
		BitVector bv2 = factory.createBitVector("bv2",4);
		BitVector bv3 = factory.createBitVector("bv3",4);
		
		List<AbstractFormula> formulas = new ArrayList<AbstractFormula>();
		formulas.add(new EqFormula(bv1,new BV_AndFormula(bv1,bv2)));
		formulas.add(new EqFormula(bv1,new BV_OrFormula(bv2, bv3)));
		formulas.add(new EqFormula(bv2,new BV_XorFormula(bv1, bv3)));
		formulas.add(new EqFormula(bv3,new BV_ArithmeticFormula(BV_Connective.ADD,bv1, new BV_HexLiteral(15))));
		formulas.add(new EqFormula(bv1, new BV_HexLiteral(15)));

		SMT2Writer writer = new SMT2Writer("./test/bv_test1.smt2", factory, formulas);
		Z3SMT2Solver solver = new Z3SMT2Solver(writer);
		Result result = solver.solve();
		ColorPrint.println("Time cost:"+(System.currentTimeMillis()-timer)+" ms",Color.WHITE);
		ColorPrint.println(result.toString(),Color.WHITE);
		if (result==Result.SAT) ColorPrint.println(factory.toString(),Color.WHITE);
		ColorPrint.println("leaving case 1",Color.BLUE);
		
		return result;
		
	}
	

	public Result Case2(){
		long timer = System.currentTimeMillis();
		ColorPrint.println("entering case 2",Color.BLUE);
		FunctionFactory factory = new FunctionFactory(512, 0.75f);
		BitVector bv1 = factory.createBitVector("bv1",8);
		BitVector bv2 = factory.createBitVector("bv2",8);
		BitVector bv3 = factory.createBitVector("bv3",8);
		
		List<AbstractFormula> formulas = new ArrayList<AbstractFormula>();
		formulas.add(new EqFormula(bv1,new BV_AndFormula(bv1,bv2)));
		formulas.add(new EqFormula(bv1,new BV_OrFormula(bv2, bv3)));
		formulas.add(new EqFormula(bv2,new BV_XorFormula(bv1, bv3)));
		formulas.add(new EqFormula(bv2,new BV_NandFormula(bv1, bv3)));
		formulas.add(new EqFormula(bv2,new BV_NorFormula(bv2, bv1)));
		formulas.add(new EqFormula(bv3,new BV_ArithmeticFormula(BV_Connective.ADD,bv1, new BV_HexLiteral(255))));
		formulas.add(new EqFormula(bv1, new BV_HexLiteral(255)));

		SMT2Writer writer = new SMT2Writer("./test/bv_test2.smt2", factory, formulas);
		Z3SMT2Solver solver = new Z3SMT2Solver(writer);
		Result result = solver.solve();
		ColorPrint.println("Time cost:"+(System.currentTimeMillis()-timer)+" ms",Color.WHITE);
		ColorPrint.println(result.toString(),Color.WHITE);
		if (result==Result.SAT) ColorPrint.println(factory.toString(),Color.WHITE);
		ColorPrint.println("leaving case 2",Color.BLUE);
		
		return result;

	}

	public Result Case3(){
		long timer = System.currentTimeMillis();
		ColorPrint.println("entering case 3",Color.BLUE);
		FunctionFactory factory = new FunctionFactory(512, 0.75f);
		BitVector bv1 = factory.createBitVector("bv1",8);
		BitVector bv2 = factory.createBitVector("bv2",8);
		BitVector bv3 = factory.createBitVector("bv3",8);
		
		List<AbstractFormula> formulas = new ArrayList<AbstractFormula>();
		formulas.add(new EqFormula(bv2,new BV_NandFormula(bv1, bv3)));
		formulas.add(new EqFormula(bv2,new BV_NorFormula(bv2, bv1)));
		formulas.add(new EqFormula(bv3,new BV_ArithmeticFormula(BV_Connective.SUB,bv1, new BV_HexLiteral(255))));
		formulas.add(new EqFormula(bv1, new BV_HexLiteral(255)));

		SMT2Writer writer = new SMT2Writer("./test/bv_test3.smt2", factory, formulas);
		Z3SMT2Solver solver = new Z3SMT2Solver(writer);
		Result result = solver.solve();
 
		ColorPrint.println("Time cost:"+(System.currentTimeMillis()-timer)+" ms",Color.WHITE);
		ColorPrint.println(result.toString(),Color.WHITE);
		if (result==Result.SAT)	ColorPrint.println(factory.toString(),Color.WHITE);
		ColorPrint.println("leaving case 3",Color.BLUE);
		
		return result;
	}

	public Result Case4(){
		long timer = System.currentTimeMillis();
		ColorPrint.println("entering case 4",Color.BLUE);
		FunctionFactory factory = new FunctionFactory(512, 0.75f);

		BitVector x = factory.createBitVector("x",64);
		BitVector y = factory.createBitVector("y",64);
		
		List<AbstractFormula> formulas = new ArrayList<AbstractFormula>();
		BV_NotFormula f1 = new BV_NotFormula(x);
		BV_NotFormula f2 = new BV_NotFormula(y);
		BV_AndFormula f3 = new BV_AndFormula (f1, f2);
		BV_NotFormula f4 = new BV_NotFormula(new BV_OrFormula (x,y));
		
		formulas.add(new NegFormula(new EqFormula(f3, f4)));
		SMT2Writer writer = new SMT2Writer("./test/bv_test4.smt2", factory, formulas);
		Z3SMT2Solver solver = new Z3SMT2Solver(writer);
		Result result = solver.solve();
		ColorPrint.println("Time cost:"+(System.currentTimeMillis()-timer)+" ms",Color.WHITE);
		ColorPrint.println(result.toString(),Color.WHITE);
		if (result==Result.SAT)	ColorPrint.println(factory.toString(),Color.WHITE);
		ColorPrint.println("leaving case 4",Color.BLUE);
		
		return result;
	}

	public Result Case5(){
		long timer = System.currentTimeMillis();
		ColorPrint.println("entering case 5",Color.BLUE);
		FunctionFactory factory = new FunctionFactory(512, 0.75f);
		BitVector x = factory.createBitVector("x",4);
		BitVector y = factory.createBitVector("y",4);
		
		List<AbstractFormula> formulas = new ArrayList<AbstractFormula>();
		BV_NotFormula f1 = new BV_NotFormula(x);
		BV_NotFormula f2 = new BV_NotFormula(y);
		BV_AndFormula f3 = new BV_AndFormula (x, y);
		formulas.add( new EqFormula (f3, new BV_HexLiteral(1<<3)));
		
		SMT2Writer writer = new SMT2Writer("./test/bv_test5.smt2", factory, formulas);
		Z3SMT2Solver solver = new Z3SMT2Solver(writer);
		
		Result result = solver.solve();
		ColorPrint.println("Time cost:"+(System.currentTimeMillis()-timer)+" ms",Color.WHITE);
		ColorPrint.println(result.toString(),Color.WHITE);
		ColorPrint.println("getting results...",Color.WHITE);
		if (result==Result.SAT)	ColorPrint.println(factory.toString(),Color.WHITE);
		ColorPrint.println("leaving case 5",Color.BLUE);
		
		return result;
	}

}
	
	

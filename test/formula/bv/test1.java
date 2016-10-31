package test.formula.bv;

import uran.formula.*;
import uran.formula.value.*;
import uran.formula.type.*;
import uran.formula.smt2.*;
import uran.formula.bv.*;
import uran.solver.*;
import java.util.*;
import com.microsoft.z3.*;
import test.formula.*;

public final class test1{
	public static void main (String args[]){
		ColorPrint.println("*****Bit Vector Test Case 1*****\n",Color.WHITE);
		case1();
	}
	
	public static void case1(){
		long timer = System.currentTimeMillis();
		BinaryLiteral l = new BinaryLiteral(240);
		ColorPrint.println("display binary literal:"+l,Color.BLUE);
		HexLiteral l1 = new HexLiteral(20);
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
		ColorPrint.println(factory.toString(),Color.WHITE);
		ColorPrint.println("leaving case 1",Color.BLUE);		
		
	}
	
}
	
	

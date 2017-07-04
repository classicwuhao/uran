package uran.test.formula;

import uran.test.util.*;
import uran.formula.*;
import uran.formula.value.*;
import uran.formula.type.*;
import uran.formula.smt2.*;
import uran.solver.*;
import java.util.*;
import com.microsoft.z3.*;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class testFormula{

	//public static void main (String args[]){
		//ColorPrint.println("*****Testing for Formula Composition*****\n\n",Color.WHITE);
		//junit.textui.TestRunner.run(new TestSuite(testFormula.class));
		/*Case1();Case2();Case3();Case4();Case5();
		Case6();Case7();Case8();Case9();Case10();
		Case11();Case12();Case13();Case14();*/
		//System.out.println("Testing...");
		//testFormula test = new testFormula();	
	//}
  	
  	/*static {
    	try {
    		System.load("../../../../../../lib/libz3.so");
    		System.load("../../../../../../lib/libz3java.so");
    	} 
    	catch (UnsatisfiedLinkError e) {
      		System.err.println("Native code library failed to load.\n" + e);
      		System.exit(1);
    	}
  	}*/
  	
	@Test
	public void test1(){		
		testFormula test = new testFormula();
		assertEquals(Status.SATISFIABLE,test.Case14());
	}
	
	@Test
	public void test2(){
		testFormula test = new testFormula();
		assertEquals("(or (and false false) (=> (=> (=> (=> (=> (=> (=> (=> (=> true true) true) true) true) true) true) true) true) true))" ,test.Case1());
	}
	
	@Test
	public void test3(){
		testFormula test = new testFormula();
		assertEquals("(or (or (or false false) (and true false)) (or (or (or (or (or (or (or (or (or false false) false) false) false) false) false) false) false) false))",test.Case2());
	}	
	
	@Test
	public void test4(){
		testFormula test = new testFormula();
		assertEquals("( len class ) ",test.Case3());
	}
	
	@Test
	public void test5(){
		testFormula test = new testFormula();
		assertEquals("(forall (( (x1 Bool) )( (x2 Int) )( (x3 Real) )) ( fun x1  x2  x3 ) )",test.Case4());
	}
	
	@Test
	public void test6(){
		testFormula test = new testFormula();
		assertEquals("(and (and (and (and (= x1 x2) (= x2 x5)) (= x2 x3)) (= x3 x4)) (= x4 x5))",test.Case5());
	}

	@Test
	public void test7(){
		testFormula test = new testFormula();
		assertEquals("(or (or (and (and x3 (not x4 )) (not x5 )) (and (and (not x3 ) x4) (not x5 ))) (and (and (not x3 ) (not x4 )) x5))",test.Case6());
	}
	
	@Test
	public void test8(){
		testFormula test = new testFormula();
		assertEquals("(= (+ (+ x1 x2) x3) 5)",test.Case7());
	}

	@Test
	public void test9(){
		testFormula test = new testFormula();
		assertEquals("(and (and (> x1 x2) (> x2 x3)) (> x3 x4))",test.Case8());
	}	

	@Test
	public void test10(){
		testFormula test = new testFormula();
		assertEquals("(= (+ (+ (+ (+ c1 c2) c3) c4) c5) 20)",test.Case9());
	}

	/*@Test
	public void test11(){
		testFormula test = new testFormula();
		assertEquals("(",test.Case10());
	}*/
	
	public String Case1(){
		ColorPrint.println("testing case 1",Color.BLUE);
		BoolLiteral[] vars = new BoolLiteral[10];
		for (int i=0;i<10;i++)
			vars[i]=new BoolLiteral(new BoolValue(true));

		BoolLiteral l1= new BoolLiteral(new BoolValue(false));
		BoolLiteral l2= new BoolLiteral(new BoolValue(false));
		
		AndFormula and = new AndFormula(l1,l2);
		OrFormula or = new OrFormula();
		ImpliesFormula implies = new ImpliesFormula();	
		String result = or.merge(and, implies.merge(vars)).toSMT2();
		ColorPrint.println(
			or.merge(and, implies.merge(vars)).toSMT2(), Color.WHITE
		);
		
		ColorPrint.println("leaving case 1\n\n",Color.BLUE);
		return result;
	}

	public String Case2(){
		ColorPrint.println("testing case 2",Color.BLUE);
		BoolLiteral[] vars = new BoolLiteral[10];
		for (int i=0;i<10;i++)
			vars[i]=new BoolLiteral(new BoolValue(false));

		BoolLiteral l1= new BoolLiteral(new BoolValue(true));
		BoolLiteral l2= new BoolLiteral(new BoolValue(false));
			
		OrFormula or = new OrFormula();
		AndFormula and = new AndFormula();
		String result = or.merge( and.merge(l1, l2), or.merge(vars)).toSMT2();
		ColorPrint.println(or.merge( and.merge(l1, l2), or.merge(vars)).toSMT2() , Color.WHITE);
		ColorPrint.println("leaving case 2\n\n",Color.BLUE);
		return result;
	}

	public String Case3(){
		ColorPrint.println("testing case 3 (Function part 1)",Color.BLUE);
		Function fun = new Function("len",new Int(), new Int());
		Constant c = new Constant("class", new Int());
		ColorPrint.println("name:"+fun.name(),Color.WHITE);	
		ColorPrint.println("arity:"+fun.arity(),Color.WHITE);
		ColorPrint.println("return type:"+fun.getReturnType(),Color.WHITE);
		String result = fun.apply(c).toSMT2();
		ColorPrint.println(fun.apply(c).toSMT2(),Color.WHITE);
		ColorPrint.println("leaving case 3\n\n ",Color.BLUE);
		return result;
	}


	public String Case4(){
		ColorPrint.println("testing case 4 (Function part 2)",Color.BLUE);
		Variable x1=new Variable("x1", new Bool());
		Variable x2=new Variable("x2", new Int());
		Variable x3=new Variable("x3", new Real());

		Function fun = new Function("fun",new Bool(), new Int(), new Real(), new Int());
		ColorPrint.println("name:"+fun.name(),Color.WHITE);	
		ColorPrint.println("arity:"+fun.arity(),Color.WHITE);
		ColorPrint.println("return type:"+fun.getReturnType(),Color.WHITE);

		//fun.apply(x1,x2,x3);
		//ColorPrint.println(fun.toSMT2(),Color.WHITE);
				
		QuantifiedFormula qf = new QuantifiedFormula(uran.formula.Quantifier.FORALL,new Decls(x1,x2,x3),fun.apply(x1,x2,x3));
		String result = qf.toSMT2();
		ColorPrint.println(qf.toSMT2(),Color.WHITE);
		ColorPrint.println("leaving case 4\n\n ",Color.BLUE);
		return result;
	}

	public String Case5(){
		ColorPrint.println("testing case 5 (Unary Formula)",Color.BLUE);
		Variable x1=new Variable("x1", new Bool());
		Variable x2=new Variable("x2", new Bool());
		Variable x3=new Variable("x3", new Bool());
		Variable x4=new Variable("x4", new Bool());		
		Variable x5=new Variable("x5", new Bool());

		EqFormula f = new EqFormula (x1, x2);
		ColorPrint.println(f.toSMT2(),Color.WHITE);
		String result = f.merge(x3,x4,x5).toSMT2();
		ColorPrint.println(f.merge(x3,x4,x5).toSMT2(),Color.WHITE);
		ColorPrint.println("leaving case 5\n\n ",Color.BLUE);
		return result;
	}
	
	public String Case6(){
		ColorPrint.println("testing case 6 (OneFormula)",Color.BLUE);
		Variable x1=new Variable("x1", new Bool());
		Variable x2=new Variable("x2", new Bool());
		Variable x3=new Variable("x3", new Bool());
		Variable x4=new Variable("x4", new Bool());
		Variable x5=new Variable("x5", new Bool());
		
		OneFormula f = new OneFormula(x1,x2);
		ColorPrint.println(f.toSMT2(),Color.WHITE);
		String result = f.merge(x3,x4,x5).toSMT2();
		ColorPrint.println(f.merge(x3,x4,x5).toSMT2(),Color.WHITE);
		ColorPrint.println("leaving case 6\n\n ",Color.BLUE);		
		return result;
	}

	public String Case7(){
		ColorPrint.println("testing case 7 (ArithmeticFormula)",Color.BLUE);
		Variable x1=new Variable("x1", new Int());
		Variable x2=new Variable("x2", new Int());
		Variable x3=new Variable("x3", new Int());
		
		ArithmeticFormula f = new ArithmeticFormula(Connective.PLUS);
		String result = new EqFormula(f.merge(x1,x2,x3),new NumLiteral(new IntValue(5))).toSMT2();
		ColorPrint.println(new EqFormula(f.merge(x1,x2,x3),new NumLiteral(new IntValue(5))).toSMT2(),Color.WHITE);
		ColorPrint.println("leaving case 7\n\n ",Color.BLUE);
		return result;
	}

	public String Case8(){
		ColorPrint.println("testing case 8 (ComparisonFormula)",Color.BLUE);
		Variable x1=new Variable("x1", new Int());
		Variable x2=new Variable("x2", new Int());
		Variable x3=new Variable("x3", new Int());
		Variable x4=new Variable("x4", new Int());

		ComparisonFormula f = new ComparisonFormula(Connective.GREATER,x1,x2);
		String result = f.merge(x3,x4).toSMT2();
		ColorPrint.println(f.merge(x3,x4).toSMT2(),Color.WHITE);
		ColorPrint.println("leaving case 8\n\n ",Color.BLUE);
		return result;
	}

	public String Case9(){
		ColorPrint.println("testing case 9 (BuildFormula: sum)",Color.BLUE);
		Constant c1 = new Constant("c1", new Int());
		Constant c2 = new Constant("c2", new Int());
		Constant c3 = new Constant("c3", new Int());
		Constant c4 = new Constant("c4", new Int());
		Constant c5 = new Constant("c5", new Int());
		
		String result = FormulaBuilder.sum(20,c1,c2,c3,c4,c5).toSMT2();
		ColorPrint.println(FormulaBuilder.sum(20,c1,c2,c3,c4,c5).toSMT2(),Color.WHITE);
		ColorPrint.println("leaving case 9\n\n ",Color.BLUE);
		return result;
	}
	
	public String Case10(){
		ColorPrint.println("testing case 10 (Scope I)",Color.BLUE);
		Constant x1 = new Constant("x1", new Int());
		Constant x2 = new Constant("x2", new Int());
		
		AndFormula and = new AndFormula(x1,x2);
		OrFormula or = new OrFormula(x1,x2);
		Scope scope = new Scope();
		scope.add(and);
		scope.add(or);
		
		String result = scope.toSMT2();
		ColorPrint.println(scope.toSMT2(),Color.WHITE);
		ColorPrint.println("leaving case 10 \n\n", Color.BLUE);
		return result;
	}

	/*public static void Case11(){
		ColorPrint.println("testing case 11 (Scope II)",Color.BLUE);
		Constant x1 = new Constant("x1", new Int());
		Constant x2 = new Constant("x2", new Int());
		Constant x3 = new Constant("x3", new Int());
		Constant x4 = new Constant("x4", new Int());
		
		AndFormula and = new AndFormula(x1,x2);
		OrFormula or = new OrFormula(x1,x2);
		OneFormula one = new OneFormula(x3,x4);
				
		Scope scope1 = new Scope();
		Scope scope2 = new Scope();
		Scope scope3 = new Scope();
				
		scope1.add(and);
		scope2.add(or);
		scope1.join(scope2);
		scope3.add(one);
		scope2.join(scope3);
		
		scope1.flatten();
		ColorPrint.println(scope1.toSMT2(),Color.WHITE);
		ColorPrint.println("leaving case 11 \n\n", Color.BLUE);
	}*/

	/*public static void Case12(){
		ColorPrint.println("testing case 12 (FunctionFactory)",Color.BLUE);
		FunctionFactory factory = new FunctionFactory(512, 0.75f);
		Constant c1 = factory.createConstant("c1",new Bool());
		Function f1 = factory.createFunction("fun1",new Int(), new Bool(), new Real());
		ColorPrint.println(factory.toString(),Color.WHITE);
		ColorPrint.println("leaving case 12 \n\n", Color.BLUE);
	}*/

	/*public static void Case13(){
		ColorPrint.println("testing case 13 (SMT2 output)",Color.BLUE);
		OneFormula one = new OneFormula();
		List<AbstractFormula> formulas = new ArrayList<AbstractFormula>();
		List<AbstractFormula> old_formulas = new ArrayList<AbstractFormula>();
		List<AbstractFormula> new_formulas = new ArrayList<AbstractFormula>();
		
		FunctionFactory factory = new FunctionFactory(512, 0.75f);
		Constant c1 = factory.createConstant("c1", new Bool());
		Constant x1 = factory.createConstant("x1", new Bool());
		Constant x2 = factory.createConstant("x2", new Bool());
		Constant x3 = factory.createConstant("x3", new Bool());
		Constant x4 = factory.createConstant("x4", new Bool());
		Constant x5 = factory.createConstant("x5", new Bool());
		Constant c2 = factory.createConstant("c2", new Int());
		Constant c3 = factory.createConstant("c3", new Real());
		Constant s1 = factory.createConstant("s1", new Int());
		Constant s2 = factory.createConstant("s2", new Int());
		Constant s3 = factory.createConstant("s3", new Int());
		Constant s4 = factory.createConstant("s4", new Int());
		Constant s5 = factory.createConstant("s5", new Int());

		Function f1 = factory.createFunction("fun1",new Int(), new Bool(), new Real());
		Function f2 = factory.createFunction("fun2",new Bool(), new Bool(), new Bool(), new Int());
		Function f3 = factory.createFunction("fun3",new Bool(), new Bool(), new Int());
		
		QuantifiedFormula qf = new QuantifiedFormula(uran.formula.Quantifier.FORALL,new Decls(x1,x2,x3),
				new EqFormula (new NumLiteral (10), f2.apply(x2,x3,x1)));
		
		formulas.add(new EqFormula(new NumLiteral(5),f1.apply(c2,c1)));
		formulas.add(one.merge(x1,x2,x3));
		formulas.add(qf);
		formulas.add(FormulaBuilder.sum(30,s1,s2,s2,s3,s4,s5));
		formulas.add(FormulaBuilder.some(x1,x2,x3,x4,x5));
		formulas.add(FormulaBuilder.range(0,1,s1,true));
		formulas.add(FormulaBuilder.range(0,2,s2,false));
		
		//Scope s = new Scope();
		//s.add();
		formulas.add(new ComparisonFormula(Connective.GREATER,f2.apply(x1,x2,x3),f3.apply(x3,x4)));
		SMT2Writer writer = new SMT2Writer("./test/test.smt2", factory, formulas);
		ColorPrint.println("SMT file is written.", Color.WHITE);
		ColorPrint.println("overwrite old formulas.", Color.WHITE);

		old_formulas.add(FormulaBuilder.sum(1,s1,s2,s3));
		old_formulas.add(FormulaBuilder.sum(2,s2,s4,s5));
		old_formulas.add(FormulaBuilder.sum(3,s3,s1,s5));
		writer.append(old_formulas);
		try{
			Thread.sleep(10000);
		}
		catch (InterruptedException e){}
		new_formulas.add(FormulaBuilder.all(x3,x4,x5));
		writer.overwrite(new_formulas,old_formulas.size());
		ColorPrint.println("leaving case 13 \n\n", Color.BLUE);
	}*/
	
	public Status Case14(){
		ColorPrint.println("testing case 14 (SMT2 Solving), testing native Z3 SMT Solver.",Color.BLUE);
		/* parse SMT2 file */
		HashMap<String, String> cfg = new HashMap<String, String>();
		cfg.put("model","true");
		try{
			Context ctx = new Context();
			String filename = "./test/test.smt2";
			Solver solver = ctx.mkSolver();
			long current = System.currentTimeMillis();
			solver.add(ctx.parseSMTLIB2File(filename,null, null, null, null));
			Status result0 = solver.check();
			Model model0 = solver.getModel();
			ColorPrint.println("Time cost:"+ (System.currentTimeMillis()-current)+" ms.",Color.WHITE);
			ColorPrint.println(result0.toString(),Color.WHITE);
			ColorPrint.println("Model: \n",Color.WHITE);
			ColorPrint.println(model0.toString(),Color.WHITE);
			ColorPrint.println("Scopes:"+solver.getNumScopes(),Color.WHITE);
			return result0;
		}
		catch (Exception e){
			ColorPrint.println("case failed: "+e.getMessage(), Color.RED);
			return Status.UNSATISFIABLE;
		}
		/*finally{
			ColorPrint.println("leaving case 14 \n\n", Color.BLUE);
			return Status.UNSATISFIABLE;
		}*/

	}

	

}

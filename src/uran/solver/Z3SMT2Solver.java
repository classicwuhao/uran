/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++Written by: Hao Wu++++++++++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 *
 *	This is a part of my research work.
 *  haowu@cs.nuim.ie
 *  JULY-2015 
 *  
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * ++++++++++++++++++++++++++++++Do or do not, there is no try.+++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package uran.solver;

import java.util.HashMap;
import uran.formula.FunctionFactory;
import uran.formula.smt2.SMT2Writer;
import uran.formula.*;
import com.microsoft.z3.*;
import com.microsoft.z3.enumerations.*;
import uran.formula.value.Value;
import uran.formula.value.BoolValue;
import uran.formula.value.IntValue;
import uran.formula.value.RealValue;
import java.util.List;
import java.util.ArrayList;

/**
 * We only need to use Z3 APIs to read in an smt2 file and solve it for us, we also update our constants and uninterpreted functions 
 * We decouple the following piece of code from Z3 APIs as much as possible so that we can easily upgrade our own formula part.
 * 
 * A very simple solve method that uses Z3 API to solve the formula we have generated.
 */

public final class Z3SMT2Solver{
	
	private FunctionFactory factory;
	private SMT2Writer writer;	
	private Solver solver;
	/**
	 *	Construct solver with a non-null SMT2Writer.
	 */	
	public Z3SMT2Solver(SMT2Writer writer){
		this.writer = writer;
		this.factory = writer.getFactory();
	}

	/**
	 *	Returns the current formula writer.
	 */
	public SMT2Writer getWriter(){return this.writer;}
	
	/**
	 * Use Z3 Java APIs to perform solving.
	 */
	public Result solve(){
		/* parse SMT2 file */
		HashMap<String, String> cfg = new HashMap<String, String>();
		cfg.put("model","true");
		cfg.put("unsat_core","true");
		
		try{
			Context ctx = new Context(cfg);
			Params p = ctx.mkParams();
			this.solver = ctx.mkSolver();
			
			p.add("timeout",SolverOption.TIMEOUT);
			if (SolverOption.TIMEOUT!=Integer.MAX_VALUE) solver.setParameters(p);
			if (SolverOption.PPDECIMAL) { 
				Global.setParameter ("pp.decimal","true"); 
				Global.setParameter ("pp.decimal_precision","5");
			};
			
			Status result;
			if (writer.sizeOfAssumption()>0){
				/* make assumptions*/
				int i=0;
				StringBuffer sb = new StringBuffer();
				StringBuffer decls = new StringBuffer();
				for (Function decl : writer.getDecls()) {
					sb.append("(declare-fun "+decl.toSMT2_decl()+") ");
					decls.append("(declare-fun "+decl.toSMT2_decl()+")");
				}
				BoolExpr[] assumptions = new BoolExpr[writer.sizeOfAssumption()];
				
				for (AbstractFormula formula : writer.getFormulas()){
					sb.append("( assert "+formula.toSMT2()+" ) ");
					//solver.add(ctx.parseSMTLIB2String("( assert "+formula.toSMT2()+" ) ",null, null, null, null)[0]);
				}
				
				solver.add(ctx.parseSMTLIB2String(sb.toString(),null, null, null, null)[0]);
				//solver.add(ctx.mkAnd(ctx.parseSMTLIB2File(writer.getFile(),null, null, null, null)));
				//solver.fromFile(writer.getFile());
				
				for (LabeledFormula f : writer.getAssumptions()){
					assumptions[i] = ctx.parseSMTLIB2String(decls.toString()+ " (assert "+f.toSMT2()+" ) ",null,null,null,null)[0];
					solver.assertAndTrack(assumptions[i], ctx.mkBoolConst(f.label()));
					writer.core_map.put(f.label().hashCode(),f.label().hashCode());
					i++;
				}
				
				//result = solver.check(assumptions);
				result = solver.check();
			}
			else{
				solver.add(ctx.parseSMTLIB2File(writer.getFile(),null, null, null, null));
				result = solver.check();
			}

			if (result == Status.SATISFIABLE){
				updateFuns(solver.getModel());
				return Result.SAT;
			}
			else if (result == Status.UNSATISFIABLE){
				//System.out.println(solver.getProof());
				//System.out.println("core: ");
				//System.out.println(solver.getUnsatCore().length);
				for (Expr c : solver.getUnsatCore()) writer.addCore(c.toString().hashCode());

				return Result.UNSAT;
			}
			else
				return Result.UNKNOWN;
		}
		catch (Z3Exception e){
			System.err.println("Z3 solver error: "+e.getMessage());
		}
		return Result.UNKNOWN;
	}
		
	private void updateFuns(Model model){
		FuncDecl cons[] = model.getConstDecls();
		Sort funs[] = model.getSorts(); 

		Expr expr;		
		/* constants */
		for (int i=0;i<cons.length;i++){
			Z3_sort_kind sort = cons[i].getRange().getSortKind();
			//System.out.println("Arity:"+cons[i].getArity()+cons[i].toString());
			if (sort==Z3_sort_kind.Z3_INT_SORT || sort==Z3_sort_kind.Z3_BV_SORT || sort==Z3_sort_kind.Z3_BOOL_SORT || sort==Z3_sort_kind.Z3_REAL_SORT){
				expr = model.getConstInterp(cons[i]);
				Symbol sym = cons[i].getName();
				if (expr.isBool()){
					factory.updateValue(cons[i].getName().toString(),new BoolValue(expr.isTrue()));
				}
				else if (expr.isInt()){
					factory.updateValue(cons[i].getName().toString(),
					new IntValue(Integer.parseInt(((IntExpr)expr).toString())));
				}
				else if (expr.isReal()){
					float r=Float.NEGATIVE_INFINITY; 
					String str_value;
					/*if (expr.isAlgebraicNumber()){
						str_value = ((AlgebraicNum) expr).toDecimal(5);
						r = Float.parseFloat(str_value.substring(0,str_value.indexOf("?")));
						factory.updateValue(cons[i].getName().toString(),new RealValue(r));
						continue;
					}*/
					int k=0;
					
					if (expr.isRatNum()){
						str_value = ((RatNum) expr).toDecimalString(SolverOption.PRECISION);
						k = str_value.indexOf("?"); //check values returns from z3.
						r = (k==-1) ? Float.parseFloat(str_value) : Float.parseFloat(str_value.substring(0,k));
						factory.updateValue(cons[i].getName().toString(),new RealValue(SolverOption.PRECISION,r));

					} else if (expr.isAlgebraicNumber()){
						str_value = ((AlgebraicNum) expr).toDecimal(SolverOption.PRECISION);
						k = str_value.indexOf("?"); //check values returns from z3.
						r = (k==-1) ? Float.parseFloat(str_value) : Float.parseFloat(str_value.substring(0,k));
						factory.updateValue(cons[i].getName().toString(),new RealValue(SolverOption.PRECISION,r));
					}
					else{
						factory.updateValue(cons[i].getName().toString(),new RealValue(expr.toString()));
					}
					
					/*String fraction = e.toString().trim();
					if (fraction.indexOf("/")!=-1){
						String num[] = fraction.split("/");
						int numerator = Integer.parseInt(num[0]);
						int denominator = Integer.parseInt(num[1]);
						float v = (float)numerator/denominator;
						factory.updateValue(cons[i].getName().toString(),new RealValue(v));
					}
					else{
						factory.updateValue(cons[i].getName().toString(),
						new RealValue(Float.parseFloat(((RealExpr)expr).toString())));
					}*/
				}
				else if (expr.isBV()){
					factory.updateBV(cons[i].getName().toString(), new IntValue(((BitVecNum)expr).getInt()));
				}
				else{
					System.out.println(expr);
				}				
			}
			else if (sort == Z3_sort_kind.Z3_ARRAY_SORT){
				FuncInterp array_interp = model.getFuncInterp(cons[i]);
				factory.updateArray(cons[i].getName().toString(),array_interp.toString());
				//System.out.println("Array Interpretation:"+array_interp.toString());
			}
			else {
				System.out.println("Unsupported interpretation.");
			}
		}
		/* interpreted functions */
		FuncDecl func[] = model.getFuncDecls();
		FuncInterp p;
		for (int i=0;i<func.length;i++){
			p = model.getFuncInterp(func[i]);
			FuncInterp.Entry entry[] = p.getEntries();
			for (int j=0;j<entry.length;j++){
				Expr e = entry[j].getValue();
				if (e.isBool()){
					factory.updateValue(func[i].getName().toString(), new BoolValue(e.isTrue()));
				}
				else if (e.isInt()){
					factory.updateValue(func[i].getName().toString(),
					new IntValue(Integer.parseInt(((IntExpr)e).toString())));
				}
			}
		}
	}
		
	public String name(){
		return "Z3";
	}
	
	public String version(){
		return Version.getString();
	}

	public String full_version(){
		return Version.getFullVersion();
	}

}

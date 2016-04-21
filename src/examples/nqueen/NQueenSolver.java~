/* An NQueen Solver
 * 
 * APR-2016	
 * Written by: Hao Wu
 * bugs report to: haowu@cs.nuim.ie
 */
package examples.nqueen;
import uran.formula.smt2.SMT2Writer;
import uran.solver.Result;
import uran.solver.Z3SMT2Solver;

public final class NQueenSolver{
	/* a standard 8x8 board */
	private static Board nqueen_0 = new Board(8,8);
	
	public static void main (String args[]){
		System.out.println("******NQueen Solver******");
		Rules rules = new Rules(nqueen_0);
		if (nqueen_0.row()<=3 && nqueen_0.col()<=3){
			System.out.println("No models exist.");
			return;
		}	
		FindSolution(rules);
	}
	
	/* Note that for large size board, you need to detect time out issue. */
	public static void FindSolution(Rules rules){
		long timer = System.currentTimeMillis();
		
		SMT2Writer writer = new SMT2Writer ("./nqueen.smt2",nqueen_0.getFactory(),rules.EncodeRules());
		Z3SMT2Solver solver = new Z3SMT2Solver(writer);

		if (solver.solve()==Result.SAT){
			System.out.println("We have a model :-)");
			System.out.println(nqueen_0);
		}
		else{
			System.out.println("No models exist.");
		}
		System.out.println("Time spent:"+(System.currentTimeMillis()-timer)+" ms");
	}

}

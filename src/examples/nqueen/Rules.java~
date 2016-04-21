/* An NQueen Solver
 * 
 * APR-2016	
 * Written by: Hao Wu
 * bugs report to: haowu@cs.nuim.ie
 */

package examples.nqueen;
import java.util.List;
import java.util.ArrayList;
import uran.formula.*;

public final class Rules{
	private Board board;
	private List<AbstractFormula> formulas = new ArrayList<AbstractFormula>();

	public Rules(Board b){board=b ;}

	public List<AbstractFormula> EncodeRules(){
		/* row, column and dia */
		for (int i=0;i<board.row();i++)
			formulas.add (FormulaBuilder.one(board.getRow(i).toArray(new Constant[board.getRow(i).size()])));

		for (int i=0;i<board.col();i++)
			formulas.add (FormulaBuilder.one(board.getCol(i).toArray(new Constant[board.getCol(i).size()])));

		for (int i=0;i<board.row();i++)
			for (int j=0;j<board.col();j++)
				formulas.add (new ImpliesFormula(board.getCell(i,j), 
					FormulaBuilder.none(board.getDiagonal(i,j).toArray(new Constant[board.getDiagonal(i,j).size()]))));
		
		return formulas;
	}

}

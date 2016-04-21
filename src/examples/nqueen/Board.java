/* An NQueen Solver
 * 
 * APR-2016	
 * Written by: Hao Wu
 * bugs report to: haowu@cs.nuim.ie
 */
package examples.nqueen;
import java.util.ArrayList;
import java.util.List;
import uran.formula.*;
import uran.formula.value.*;
import uran.formula.type.*;

public final class Board{
	private int row;
	private int col;
	private Constant board[][];	
	private FunctionFactory factory = new FunctionFactory(512,0.75f);

	public Board(int r, int c){
		row = r;
		col = c;
		if (row<=0 || col<=0) throw new QueenException("Error: the size of the board cannot be zero.");
		if(row!=col) throw new QueenException("Error: the board has to be square.");
		board = new Constant[row][col];		
		initialise();
	}

	private void initialise(){
		int count=0;
		for (int i=0;i<row;i++)
			for(int j=0;j<col;j++)
				board[i][j]=factory.createConstant("C_"+i+"_"+j,new Bool());
	}
	
	public List<Constant> getDiagonal(int c, int r){
		List<Constant> elements = new ArrayList<Constant>();
		int i = c;
		int j = r;

		if (c>=row || c<0) throw new QueenException("Error: row index is out of bound.");
		if (r>=col || r<0) throw new QueenException("Error: col index is out of bound.");

		/* do it in 2 directions */
		for(i=c+1,j=r+1;i<row && j<col;i++,j++)	elements.add(board[i][j]);
		for(i=c-1,j=r-1;i>=0 && j>=0;i--,j--) elements.add(board[i][j]);
		for(i=c-1,j=r+1;i>=0 && j<col;i--,j++) elements.add(board[i][j]);
		for(i=c+1,j=r-1;i<row && j>=0;i++,j--) elements.add(board[i][j]);

		return elements;
	}

	public List<Constant> getRow(int r){
		List<Constant> elements = new ArrayList<Constant>();					
		if (r>=row || r<0) throw new QueenException("Error: row index is out of bound.");
		for (int j=0;j<col;j++) elements.add(board[r][j]);
		return elements;
	}
	
	public List<Constant> getCol(int c){
		List<Constant> elements = new ArrayList<Constant>();
		if (c<0 || c>=col) throw new QueenException("Error: column index is out of bound.");
		for (int j=0;j<row;j++) elements.add(board[j][c]);
		return elements;
	}

	public int row(){return row;}
	public int col(){return col;}
	public Constant getCell(int i, int j){
		if (i>=row || i<0) throw new QueenException("Error: row index is out of bound.");
		if (j>=col || j<0) throw new QueenException("Error: col index is out of bound.");
		
		return board[i][j];
	}
	public String toString(){
		StringBuffer sb = new StringBuffer();
		int id=0;
		for (int k=0;k<col;k++) sb.append("---"); sb.append("\n");
		for (int i=0;i<row;i++){
			for (int j=0;j<col;j++){
				Value value = factory.getValue("C_"+i+"_"+j);
				if (value==null) throw new QueenException("Error: I am expecting a value.");
				if (value.IsBool()){
					BoolValue bv = (BoolValue) value;
					sb.append(bv.getValue() ? "|Q|" : "|X|");
				}
			}
			sb.append("\n");
			for (int k=0;k<col;k++) sb.append("---"); sb.append("\n");
		}
		return sb.toString();
	}
	
	public FunctionFactory getFactory(){return factory;}

}

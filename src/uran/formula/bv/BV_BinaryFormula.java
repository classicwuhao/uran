/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++Written by: Hao Wu++++++++++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 *
 *	This is a part of my research work.
 *  haowu@cs.nuim.ie
 *  OCT-2016 
 *  
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * ++++++++++++++++++++++++++++++Do or do not, there is no try.+++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package uran.formula.bv;
import uran.err.NullableFormulaException;
import uran.formula.visitor.AbstractVisitor;

/** 
 * 	Base class for all bitvector binary formulas 
 *	@author Hao Wu
 */

public abstract class BV_BinaryFormula extends BV_Formula{
	private BV_Formula _left;
	private BV_Formula _right;
	private BV_Connective _conn;
	
	/**
	 *	setup connective
	 */	
	public BV_BinaryFormula(BV_Connective conn){_conn=conn;}
	
	public BV_BinaryFormula(BV_Connective conn, BV_Formula left, BV_Formula right){
		_left=left;
		_right=right;
		_conn=conn;
	}

	/**
 	 *	if both left and right nodes are null, then this formula is considered to be null.
	 */
	@Override
	public boolean isNull(){
		return _left==null && _right==null;
	}	
	/**
 	 * @return	the left node
	 */
	public BV_Formula left(){
		return _left;
	}

	/**
 	 * @return	the right node
	 */
	public BV_Formula right(){
		return _right;
	}
	/**
 	 * @return	the connective
	 */
	public BV_Connective connective(){
		return _conn;
	}

	/**
	 *	set up a new left node.
 	 * @param	formula the new left node
	 */
	public void setLeft(BV_Formula formula){
		if (formula.isNull()) throw new NullableFormulaException("BinaryFormula: Cannot set a fomrula to be null");
		_left=formula;
	}

	/**
	 *	set up a new right node.
 	 * @param	formula the new right node
	 */
	public void setRight(BV_Formula formula){
		if (formula.isNull()) throw new NullableFormulaException("BinaryFormula: Cannot set a fomrula to be null");
		_right=formula;
	}

	public void setConnective(BV_Connective con){_conn=con;}
	
	public String toString(){
		return "("+_left.toString()+" "+_conn.toString()+" "+_right.toString()+")";
	}

	/**
	 *	merge a series of binary formulas.
 	 * @param	formulas formulas to be merged
	 */
	public abstract BV_BinaryFormula merge(BV_Formula...formulas);
	
	
	@Override
	public String toSMT2(){
		return "("+_conn+" "+_left.toSMT2()+" "+_right.toSMT2()+")";
	}

	@Override	
	public void accept (AbstractVisitor visitor){
		visitor.visit(this);
	}

	@Override
	public boolean isBV_BinaryFormula(){return true;}
	
}


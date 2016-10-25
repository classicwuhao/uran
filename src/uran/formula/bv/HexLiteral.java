/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++Written by: Hao Wu++++++++++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 *
 *	This is a part of my research work.
 *  haowu@cs.nuim.ie
 *  OCTOBER-2016 
 *  
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * ++++++++++++++++++++++++++++++Do or do not, there is no try.+++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */
package uran.formula.bv;
import uran.formula.AbstractFormula;
import uran.formula.visitor.AbstractVisitor;
import uran.err.IllFormedFormulaException;


/**
 * Abstract syntax tree for binary literal
 */
public final class HexLiteral extends BV_Literal{

	private String hex;

	/* disable the default constructor */
	private HexLiteral(){}

	/**
	 *	Create a hex literal node.	
	 * @param hex	a string of 0s and 1s.	
	 */
	public HexLiteral(String hex){
		if (hex==null) throw new IllFormedFormulaException("Error: name cannot be null.");
		if (hex.length()<=0) throw new IllFormedFormulaException("Error: the name is not specified.");
		
		this.hex = hex;
	}

	/**
	 *
	 *	@return a string representation;
	 */
	public String hex(){ return this.hex;}

	private boolean check(String hex){
		for (int i=0;i<hex.length();i++){
			int c = (int) hex.charAt(i);
			if (!(c>=48 && c<=57) && !(c>=97 && c<=102) && !(c>=65 && c<=70)) return false;
		}
		return true;
	}
	
	/**
	 *	@return	true.
	 */		
	@Override
	public boolean isLiteral(){return true;}
	/**
	 *	@return	true.
	 */
	@Override
	public boolean isHexLiteral(){return true;}

	/**
	 *	@return	hex literal in SMT2 standard.
	 */
	@Override
	public String toSMT2(){
		return this.toString();
	}
	
	/**
	 * Accept a concrete visitor that inherits from AbstractVisitor
	 * @Param	visitor	the visitor used for generating formulas in SMT2 standard
	 */
	@Override
	public void accept (AbstractVisitor visitor){
		visitor.visit(this);
	}	

	/**
	 *	@return	the string representation of a hexliteral.
	 */
	
	@Override
	public String toString(){
		return "#x"+this.hex;
	}
		
}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++Written by: Hao Wu++++++++++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 *
 *	This is a part of my research work.
 *  haowu@cs.nuim.ie
 *  APR-2015 
 *  
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * ++++++++++++++++++++++++++++++Do or do not, there is no try.+++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package uran.formula;
import uran.formula.visitor.AbstractVisitor;
public abstract class TernaryFormula extends AbstractFormula{
	private AbstractFormula _formula_a;
	private Connective _operator;
	private AbstractFormula _formula_b;
	private AbstractFormula _formula_c;
		
	public TernaryFormula (Connective operator, AbstractFormula a, AbstractFormula b, AbstractFormula c){
		_operator=operator;
		_formula_a=a;
		_formula_b=b;
		_formula_c=c;
	}
	
	public Connective operator(){return this._operator;}

	/**
	 * 1st expression
	 * @return
	 */
	public AbstractFormula expr_a(){
		return this._formula_a;
	} 

	/**
	 * 2nd expression
	 * @return
	 */
	public AbstractFormula expr_b(){
		return this._formula_b;
	} 

	/**
	 * 3rd expression
	 * @return
	 */
	public AbstractFormula expr_c(){
		return this._formula_c;
	} 

	/**
	 * Set appropriate connective
	 * @param operator
	 */
	public void setConnective(Connective operator){this._operator=operator;}

	/**
	 * Change 1st expression 
	 * @param expr_a
	 */
	public void setExprA(AbstractFormula expr_a){this._formula_a=expr_a;}

	/**
	 * Change 1st expression 
	 * @param expr_b
	 */
	public void setExprB(AbstractFormula expr_b){this._formula_b=expr_b;}

	/**
	 * Change 1st expression 
	 * @param expr_c
	 */
	public void setExprC(AbstractFormula expr_c){this._formula_c=expr_c;}

	public String toString(){
		return "("+this._operator.toString()+ this._formula_a.toString() + 
			","+ this._formula_b.toString()+"," + this._formula_c.toString() + ")";
	}

	@Override
	public String toSMT2(){
		return "(" + this._operator.toString()+ " "+ this._formula_a.toSMT2()
		+" "+ this._formula_b.toSMT2() +" "+ this._formula_c.toSMT2() +")";
	}

	@Override
	public boolean isTernaryFormula(){return true;}
	public boolean isITE(){return false;}

	@Override
	public void accept(AbstractVisitor visitor){
		visitor.visit(this);
	}
}

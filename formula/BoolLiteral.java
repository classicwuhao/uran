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

import uran.formula.value.BoolValue;
import uran.formula.visitor.AbstractVisitor;

public class BoolLiteral extends Literal{
	private BoolValue value;
	
	public BoolLiteral(){
		//default constructor
		value=new BoolValue();
	}

	public BoolLiteral(BoolValue v){
		value=v;
	}
		
	public BoolLiteral (boolean b){
		value=new BoolValue(b);
	}

	public boolean getLiteral(){
		return value.getValue();
	}
	
	public BoolValue getValue(){
		return value;
	}

	public String toString(){
		return value.toString();	
	}

	@Override
	public String toSMT2(){return toString();}

	@Override
	public void accept (AbstractVisitor visitor){visitor.visit(this);}
		
}

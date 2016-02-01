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


package uran.formula.cnf;
import uran.formula.cnf.visitor.AbstractVisitor;

public final class NotGate extends BooleanCircuit{

	private BooleanCircuit input;

	public NotGate(BooleanCircuit i){
		input=i;
	}
	
	public BooleanCircuit input(){
		return input;
	}

	public boolean isNotGate(){return true;}
	public void accept (AbstractVisitor v){
		v.visit(this);
	}

}

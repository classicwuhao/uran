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
import uran.formula.type.Type;

public final class Variable extends Constant{
	
	public Variable(String n, Type t){
		super(n,t);
	}

//	public String name(){return name;}
//	public Type type(){return type;}


	@Override
	public boolean isVariable(){return true;}
	
	@Override
	public String toString(){
		return "("+name()+" "+getReturnType()+")";
	}

	@Override
	public String toSMT2(){return name();}
	
}

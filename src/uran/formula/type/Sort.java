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

package uran.formula.type;
import uran.err.NullableFormulaException;

public final class Sort extends Type{

	private String name="";

	private Sort(){this.type=Type.SORT;}
	public Sort(String n){
		super(n);
		name=n;
	}

	public void setName(String n){
		if (n==null) throw new NullableFormulaException("Sort: a null type is not allowed.");
		if (n.trim().equals("")) throw new NullableFormulaException("Sort: a type must have a name.");
		name = n;
	}

	@Override
	public String name(){
		return name;
	}
	
	@Override
	public boolean isSort(){return true;}	
	@Override
	public String toString(){return name();}
	
}

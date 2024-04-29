
/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++Written by: Hao Wu++++++++++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 *
 *	This is a part of my research work.
 *  haowu@cs.nuim.ie
 *  MAR-2022
 *  
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * ++++++++++++++++++++++++++++++Do or do not, there is no try.+++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */
package uran.formula;
import java.util.Locale;
import uran.formula.value.RealValue;
import uran.formula.visitor.AbstractVisitor;
import uran.solver.SolverOption;
public class RealLiteral extends Literal{
    private RealValue value;

    
	public RealLiteral(){
		//default constructor
		value=new RealValue();
		
	}

	public RealLiteral(RealValue v){
		value=v;
	}
		
	public RealLiteral (float v){
		value=new RealValue(v);
	}

	public RealValue getValue(){
		return value;
	}

	public float getLiteral(){
		return value.getValue();
	}

	@Override
	public String toString(){
        /*
         * up-to 15 decimal places.
         **/ 
		return String.format(Locale.ENGLISH,"%."+SolverOption.PRECISION+"f",value.getValue());
	}

	@Override
	public String toSMT2(){return toString();}
	
	@Override
	public void accept (AbstractVisitor visitor){visitor.visit(this);}
}

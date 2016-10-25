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

public abstract class BV_Literal extends AbstractFormula{

	@Override
	public boolean isLiteral(){return true;}
	public boolean isBinaryLiteral(){return false;}
	public boolean isHexLiteral(){return false;}
	
}

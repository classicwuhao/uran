/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++Written by: Hao Wu++++++++++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 *
 *	This is a part of my research work.
 *  haowu@cs.nuim.ie
 *  APR-2022
 * 
 *  Added to support SMT-Lib v2.0+ 
 *  
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * ++++++++++++++++++++++++++++++Do or do not, there is no try.+++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package uran.formula;

import uran.err.NullableFormulaException;
import uran.err.MissingFormulaException;

public class XorFormula extends BinaryFormula{
    
    public XorFormula(){super(Connective.XOR);}
	
	public XorFormula(AbstractFormula f1, AbstractFormula f2){
		super(Connective.XOR,f1,f2);
	}

    @Override	
	public XorFormula merge (AbstractFormula...formulas){
		XorFormula formula=this;
		int pos=0;

		if (formulas==null) throw new NullableFormulaException("Build Xor Formula: No formula(s) found.");
		
		if (formula.isNull()){
			if (formulas.length<2) throw new MissingFormulaException("Build Xor Formula: Formula size >=  2 is expected.");
			formula.setLeft(formulas[0]);
			formula.setRight(formulas[1]);
			formula.setConnective(Connective.XOR);
			pos=2;
		}
		
		for (int i=pos;i<formulas.length;i++){
			if (formulas[i].isNull()) throw new NullableFormulaException("Build Xor Formula: A formula cannot be null");
			formula= new XorFormula(formula,formulas[i]);
		}
		return formula;	
		
	}


}

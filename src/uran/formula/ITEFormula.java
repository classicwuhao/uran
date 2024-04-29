package uran.formula;

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++Written by: Hao Wu++++++++++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 *
 *	This is a part of my research work.
 *  haowu@cs.nuim.ie
 *  APR-2022 
 *  
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * ++++++++++++++++++++++++++++++Do or do not, there is no try.+++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

public class ITEFormula extends TernaryFormula{
    
    public ITEFormula(AbstractFormula expr_g, AbstractFormula expr_t, AbstractFormula expr_e){
        super(Connective.ITE,expr_g,expr_t,expr_e);
    }

    /**
     * Swap expr_t and expr_e and keep the condition.
     */
    public void swap(){
        AbstractFormula expr_t = this.expr_b();
        AbstractFormula expr_e = this.expr_c();
        this.setExprB(expr_e);
        this.setExprC(expr_t);
    }
    
    @Override
    public boolean isITE(){return true;}

}

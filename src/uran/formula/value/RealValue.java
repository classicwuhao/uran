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

package uran.formula.value;

public class RealValue extends Value{
    private float value;
	private int n = 0;
	private String raw="";
	public RealValue(){}
	public RealValue(int s, float v){n=s;value=v;}
	public RealValue(float v){value=v;}
	public RealValue(String value){raw=value;}
	public float getValue(){return value;}
	public void setValue(float n){value=n;}
	public boolean IsReal(){return true;}
	public void setDecimalPlace (int s){n=s;}
	public boolean isInterpreted(){
		return this.raw.equals("");
	}
	public String toString(){
		if (!isInterpreted()) return this.raw;
		return (n>0) ? String.format("%."+n+"f",value) : String.format("%f",value);
	}

}

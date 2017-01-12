package uran.formula.array;

import uran.formula.AbstractFormula;
import uran.formula.visitor.AbstractVisitor;
import uran.formula.type.Type;

public final class ArrayEx <I extends Type, V extends Type> extends AbstractFormula{
	
	private I index;
	private V value;
	private String name;
	
	public ArrayEx(String name){this.name = name;}
	
	@Override
	public String toString(){
		return this.name()+" (Array "+this.index+" "+this.value+")";
	}
	
	@Override
	public String toSMT2(){return this.name();}
	
	@Override
	public void accept (AbstractVisitor visitor){
		
	}
		
	public I indexType(){return this.index;}
	public V valueType(){return this.value;}
	public String name(){return this.name();}
		
}

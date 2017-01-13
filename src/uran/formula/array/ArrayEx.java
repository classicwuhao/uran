package uran.formula.array;

import uran.formula.AbstractFormula;
import uran.formula.visitor.AbstractVisitor;
import uran.formula.type.Type;

public final class ArrayEx <I extends Type, V extends Type> extends AbstractFormula{
	
	private Class<I> index;
	private Class<V> value;
	private String name;
	
	public ArrayEx(String name, Class<I> index, Class<V> value){
		this.name = name;
		this.index = index;
		this.value = value;
	}
	
	@Override
	public String toString(){
		return this.name()+" (Array "+index.getSimpleName()+" "+value.getSimpleName()+")";
	}
	
	@Override
	public String toSMT2(){return this.name();}
	
	@Override
	public void accept (AbstractVisitor visitor){
		visitor.visit(this);
	}
	
	public Class<I> indexType(){return this.index;}
	public Class<V> valueType(){return this.value;}
	public String name(){return this.name;}
	
}

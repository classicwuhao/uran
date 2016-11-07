/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++Written by: Hao Wu++++++++++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 *
 *	This is a part of my research work.
 *  haowu@cs.nuim.ie
 *  JULY-2015 
 *  
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * ++++++++++++++++++++++++++++++Do or do not, there is no try.+++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package uran.formula;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import uran.err.NullableFormulaException;
import uran.err.DuplicatedDeclaration;
import uran.formula.Function;
import uran.formula.Constant;
import uran.formula.AndFormula;
import uran.formula.EqFormula;
import uran.formula.NegFormula;
import uran.formula.type.Type;
import uran.formula.type.Bool;
import uran.formula.type.Int;
import uran.formula.type.Real;
import uran.formula.type.Sort;
import uran.formula.value.Value;
import uran.formula.value.BoolValue;
import uran.formula.value.IntValue;
import uran.formula.bv.BitVector;
/** 
 *	Factory for creating functions and constants, only these functions and constants created by factory are translated to SMT2.	
 * 	@author Hao Wu
 */
public final class FunctionFactory{

	/* use a hashmap here, much faster than a hashtable. */
	private HashMap<String, Function> fun_table; 
	
	private HashMap<Function, Value> sym_table;

	/* tables for storing bit vectors. */
	private HashMap<String, BitVector> bv_table;
	
	private HashMap<BitVector, Value> bv_sym_table;

	/** Create a factory with default ini capacity and load factor */
	public FunctionFactory(){
		fun_table = new HashMap<String, Function>();
		sym_table = new HashMap<Function, Value>();
		bv_table = new HashMap<String, BitVector>();
		bv_sym_table = new HashMap<BitVector, Value>();
		
	}

	/** Specify an initial capacity for symbol tables */
	public FunctionFactory(int cap){
		fun_table = new HashMap<String, Function>(cap);
		sym_table = new HashMap<Function, Value>(cap);
		bv_table = new HashMap<String, BitVector>(cap);
		bv_sym_table = new HashMap<BitVector, Value>(cap);
	}

	/** 
	 *	Specify an ini capacity and load factor 
	 *	@param cap 		the initial capacity
	 *	@param load 	the load factor
	 */
	public FunctionFactory(int cap, float load){
		fun_table = new HashMap<String, Function>(cap, load);
		sym_table = new HashMap<Function, Value>(cap, load);
		bv_table = new HashMap<String, BitVector>(cap,load);
		bv_sym_table = new HashMap<BitVector, Value>(cap,load);
	}

	/** 
	 *	Create a new constant, and save it into the hash map. No duplicated constant is allowed in the hashmap.
	 *	@param name 	the name of a <tt>constant<tt>
	 *	@param type 	the type of a <tt>constant<tt>
	 *	@return			a new constant	
	 *	@throws			NullableFormulaException if the name is null
	 *	@throws			DuplicatedDeclaration if the duplicated name is identified.
	 */
	public Constant createConstant(String name, Type type){
		if (name==null || type==null) throw new NullableFormulaException("Error: cannot create constant either name or type is null.");
		check(name);
		Constant constant = new Constant(name, type);
		fun_table.put(name, constant);
		return constant;
	}

	/** 
	 *	Create a new function with a list of arguments, and save it into the hash map. No duplicated constant is allowed in the hashmap.
	 *	@param name 	the name of a <tt>function<tt>
	 *	@param args 	the list of <tt>arguments<tt>
	 *  @return			a new function	
	 *	@throws			NullableFormulaException if the name is null
	 *	@throws			DuplicatedDeclaration if the duplicated name is identified.
	 */
	public Function createFunction(String name, Type...args){
		if (name==null || args==null) throw new NullableFormulaException("Error: cannot create constant either name or type is null.");
		for (int i=0;i<args.length;i++)
			if (args[i]==null) throw new NullableFormulaException("Error: arguments cannot be null.");
		check(name);
		Function function = new Function(name,args);
		fun_table.put(name, function);
		return function;
	}

	/**
	 *	Create a new bit vector with specified name and length.
	 *	@param name		the name of a <tt>bit vector<tt>
	 *	@param length	the specified <tt>length<tt>
	 *  @return			a new bit vector
	 *	@throws			NullableFormulaException if the name is empty or length is <=0.
	 *	@throws			DuplicatedDeclaration if the bit vector already exists.
	 */
	public BitVector createBitVector(String name, int length){
		if (name==null || length<=0) throw new NullableFormulaException("Error: cannot create bit vector with null name or zero length.");
		if (bv_table.containsKey(name)) throw new DuplicatedDeclaration("Error: "+name+" for bit vector already exists.");
		
		BitVector bv = new BitVector(name, length);
		bv_table.put(name, bv);
		return bv;
	}

	/** 
	 *  Retrieve an existing bit vector.
	 *	@param name 	the name of a bit vector
	 *  @return			an exisitng bit vector saved in the factory otherwise returns a null value.
	 */
	public BitVector bvLookup (String name){	
		return (!bv_table.containsKey(name)) ? null : bv_table.get(name);
	}

	/** 
	 *  Retrieve an existing constant from the factory
	 *	@param name 	the name of a <tt>constant<tt>
	 *  @return			an exisitng constant saved in the factory otherwise returns a null value.
	 */
	public Constant conLookup (String name){
		if (!fun_table.containsKey(name)) return null;
		Function f = fun_table.get(name);
		
		/** otherwise it must be a constant */		
		return f.isConstant() ? (Constant)f : null;
	}

	/** 
	 *  Retrieve an existing function from the factory
	 *	@param name 	the name of a <tt>function<tt>
	 *  @return			an exisitng function saved in the factory otherwise returns a null value.
	 */
	public Function funLookup(String name){
		if (!fun_table.containsKey(name)) return null;
		Function f = fun_table.get(name);
		return f.isConstant() ? null : f;
	}
	
	private void check(String name){
		if (fun_table.containsKey(name)) throw new DuplicatedDeclaration("Error: "+ name + " already exists.");
	}

	/** 
	 *  Return the number of functions perserved by the factory
	 */
	public int size(){
		return fun_table.size();
	}
	/**
	 * Remove a function.
	 */
	public void remove(String name){
		if (!fun_table.containsKey(name))
			fun_table.remove(name);
	}

	/**
	 *	Return a list of functions that are perserved in the factory.
	 */	
	public List<Function> getAllFunctions(){
		List<Function> decls = new ArrayList<Function>();
		
		/* no need for checking for null exception */
		for (String name : fun_table.keySet()) decls.add(fun_table.get(name));
		return decls;
	}

	/**
	 *	
	 *	@return 	all the bit vectors that are perserved in the factory.
	 */	
	
	public List<BitVector> getAllBitVectors(){
		List<BitVector> bvs = new ArrayList<BitVector>();
		
		for (String name : bv_table.keySet()) bvs.add(bv_table.get(name));

		return bvs;
	}
	
	/**
	 * Update a specific function with an assignment returned by the SMT solver.
	 */
	public void updateValue (String name, Value value){
		if (value == null) throw new NullableFormulaException("Error: value cannot be null.");
		Function fun = this.conLookup(name);

		/* try function */
		if (fun == null){
			fun = this.funLookup(name);
			//if (fun==null) System.err.println("Warning: the function:"+name+" does not exist, update is ignored");				
		}
		sym_table.put(fun, value);
	}
	
	public void updateBV (String name, IntValue value){
		if (value==null) throw new NullableFormulaException("Error: value cannot be null.");
		BitVector bv = this.bvLookup(name);
		
		if (bv==null) throw new NullableFormulaException("Error: bit vector "+name+" does not exist.");
		
		System.out.println(name);
		bv.setValue(value);
		bv_sym_table.put(bv,value);
	}

	/**
	 * Get the specific value for a bit vector from our symbol table
	 * If no such bit vector is found, a null value is returned.
	 */	
	public int getBV_Value(String name){
		BitVector bv = bv_table.get(name);
		if (bv == null) throw new NullableFormulaException("Error: bit vector "+name+" does not exist.");
		
		return bv.value();
	}

	/**
	 * Get the specific value for a function from our symbol table
	 * If no such function is found, a null value is returned.
	 */
	public Value getValue(String name){
		return 
				(!sym_table.containsKey(fun_table.get(name))) ?
					null
				:
					sym_table.get(fun_table.get(name))
				;
	}

	/**
	 * Negate all the constants defined in the constant table.	
	 */		
	public NegFormula negConstants(){
		List <AbstractFormula> formulas = new ArrayList<AbstractFormula>();
		
		for (String name : fun_table.keySet()){
			Function fun = fun_table.get(name);
			if (fun.getReturnType().isBool()){
				BoolValue bv= (BoolValue) sym_table.get(fun);
				if (bv==null) continue; /* skip uniterpreted symbols */
				formulas.add(new EqFormula(fun, new BoolLiteral(bv.getValue())));
			}
			else if (fun.getReturnType().isInt()){
				IntValue iv = (IntValue) sym_table.get(fun);
				if (iv==null) continue;
				formulas.add(new EqFormula(fun, new NumLiteral(iv.getValue())));
			}

		}
		
		AbstractFormula[] tmp = new AbstractFormula[formulas.size()];
		for (int i=0;i<formulas.size();i++) tmp[i]=formulas.get(i);
		return new NegFormula(new AndFormula().merge(tmp));
	}

	/**
	 * Return a string representation of all functions.	
	 */		
	public String toString(){
		StringBuilder sb = new StringBuilder();
		/* dont have to check null exception because create methods make sure every instance of the function is not null. */
		sb.append("{\n");
		for (String name : fun_table.keySet())
			sb.append("<"+name+":"+sym_table.get(fun_table.get(name))+"> ");
		for (String name : bv_table.keySet())
			sb.append("<"+name+":"+bv_table.get(name).value()+"> ");
		
		sb.append("\n }");
		return sb.toString();
	}	
}

package com.sri.ai.util.function.core.variables;

import com.sri.ai.util.function.api.variables.SetOfValues;
import com.sri.ai.util.function.api.variables.Unit;
import com.sri.ai.util.function.core.values.SetOfIntegerValues;

public class IntegerVariable extends AbstractVariable {
	
	public IntegerVariable(String name, Unit unit) {
		super(name, unit, null);
	}

	public IntegerVariable(String name, Unit unit, SetOfValues setOfValues) {
		super(name, unit, setOfValues);
	}

	public IntegerVariable(String name, Unit unit, int first, int last) {
		super(name, unit, new SetOfIntegerValues(first, last));
	}
}
package com.sri.ai.test.util.planning;

import static com.sri.ai.util.Util.pair;

import com.sri.ai.util.base.Pair;
import com.sri.ai.util.planning.api.ContingentGoal;
import com.sri.ai.util.planning.api.State;

public class MyContingentGoal extends MyGoal implements ContingentGoal {

	public MyContingentGoal(String name) {
		super(name);
	}

	/**
	 * Always returns the given state since {@link MyContingentGoal} is not meant to be used at plan execution time.
	 */
	@Override
	public Pair<Boolean, State> isSatisfied(State state) {
		return pair(false, state);
	}
	
	@Override
	public String toString() {
		return "contingent " + name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyContingentGoal other = (MyContingentGoal) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
package com.sri.ai.util.experiment;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * A map where keys are mapped to non-empty Set objects.  Has the same
 * behavior as the standard Map interface, except that the value passed to 
 * <code>put</code> must be a Set; <code>get</code> returns an empty set 
 * instead of <code>null</code> when the given key is not in the map; and 
 * there are new <code>add</code> and <code>remove</code> methods.
 */
public interface MultiMap extends Map {
	/**
	 * Returns the set associated with the given key.  If the key is not 
	 * in the map, returns an empty set.  The set returned is modifiable and 
	 * backed by this multi-map: if values are added for the given key, 
	 * they will show up in the returned set.  However, the returned set may 
	 * lose its connection to this multi-map if the multi-map's 
	 * <code>put</code> method is called or if all the values for the 
	 * given key are removed.  
	 */
	@Override
	Object get(Object key);

	/**
	 * Associates the given key with the given value, which must be a Set.  
	 * If the given set is empty, the key is removed from the map.  
	 *
	 * @return the set previously associated with this key, or an empty 
	 *         set if the key was not in the map
	 *
	 * @throws IllegalArgumentException if <code>value</code> is not a Set
	 */
	@Override
	Object put(Object key, Object value);

	/**
	 * Adds the given value to the set associated with the given key.  
	 * If the key is not yet in the map, it is added.
	 *
	 * @return true if the MultiMap changed as a result of this call
	 */
	boolean add(Object key, Object value);

	/**
	 * Adds all elements of the given set to the set associated with the 
	 * given key.  If the key is not yet in the map, it is added.
	 *
	 * @return true if the MultiMap changed as a result of this call
	 */
	boolean addAll(Object key, Set values);

	/**
	 * Removes the given value from the set associated with the given key.  
	 * Does nothing if the value is not in that set.  If the set ends up 
	 * being empty, then the key is removed from the map.
	 *
	 * @return true if the MultiMap changed as a result of this call
	 */
	boolean remove(Object key, Object value);

	/**
	 * Removes all elements of the given set from the set associated with 
	 * the given key.  If the associated set ends up being empty, then the 
	 * key is removed from the map.  
	 *
	 * @return true if the MultiMap changed as a result of this call
	 */
	boolean removeAll(Object key, Set values);

	static class EmptyMultiMap extends AbstractMap 
	implements MultiMap {
		@Override
		public Object get(Object key) {
			return Collections.EMPTY_SET;
		}

		@Override
		public boolean add(Object key, Object value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(Object key, Set values) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(Object key, Object value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(Object key, Set values) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Set entrySet() {
			return Collections.EMPTY_SET;
		}
	}

	/**
	 * An unmodifiable multi-map that maps all keys to the empty set.
	 */
	public static final MultiMap EMPTY_MULTI_MAP = new EmptyMultiMap();
}

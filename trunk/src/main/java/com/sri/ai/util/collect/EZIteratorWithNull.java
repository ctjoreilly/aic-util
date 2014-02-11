/*
 * Copyright (c) 2013, SRI International
 * All rights reserved.
 * Licensed under the The BSD 3-Clause License;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 * 
 * http://opensource.org/licenses/BSD-3-Clause
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * Neither the name of the aic-util nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.sri.ai.util.collect;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.google.common.annotations.Beta;


/**
 * An abstract class meant to make the implementation of iterators easier. It is
 * similar to {@link EZIterator}, but allows for <code>null</code> elements in
 * the iterator's range. This is accomplished by changing the meaning of
 * {@link #calculateNext()} returning <code>null</code>. Whereas in
 * {@link EZIterator} this was interpreted as the end of the range, here this
 * simply means the next element is <code>null</code>. For
 * {@link #calculateNext()} to indicate end of range, it calls the new method
 * {@link #endOfRange()}.
 * 
 * @author braz
 */
@Beta
public abstract class EZIteratorWithNull<E> implements Iterator<E> {
	
	/** Field indicating if next element has already being computed. */
	protected boolean onNext = false;

	/** The next element if already computed. */
	protected E next;
	
	//
	private boolean endOfRange = false;
	
	/**
	 * Method responsible for calculating next element in sequence,
	 * returning <code>null</code> if there are no more elements.
	 */
	protected abstract E calculateNext();

	protected void endOfRange() {
		endOfRange = true;
	}
	
	private void ensureBeingOnNext() {
		if (!onNext) {
			next = calculateNext();
			onNext = true;
		}
	}

	public boolean hasNext() {
		ensureBeingOnNext();
		return ! endOfRange;
	}

	public E next() {
		ensureBeingOnNext();
		if (endOfRange) throw new NoSuchElementException();
		onNext = false;
		return next;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}

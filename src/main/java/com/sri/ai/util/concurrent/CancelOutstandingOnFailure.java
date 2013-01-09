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
package com.sri.ai.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.base.Predicate;

/**
 * A configurable {@link Predicate} to be used to determine whether or not outstanding
 * branched tasks should be cancelled when a failure occurs.
 * 
 * @author oreilly
 * 
 */
@Beta
public class CancelOutstandingOnFailure implements Predicate<Throwable> {
	private boolean   cancel    = true;
	private Throwable throwable = null;

	/**
	 * Constructor.
	 * 
	 * @param cancel
	 *            set to true if outstanding tasks should be cancelled on
	 *            failure, false otherwise.
	 */
	public CancelOutstandingOnFailure(boolean cancel) {
		this.cancel = cancel;
	}

	/**
	 * 
	 * @return is configured to cancel outstanding tasks on a failure, this will
	 *         contain the last non-null throwable received by this class.
	 */
	public Throwable getThrowable() {
		return throwable;
	}

	@Override
	public boolean apply(Throwable t) {
		// Ensure I've a throwable and want to
		// cancel outstanding tasks.
		if (t != null && cancel) {
			this.throwable = t;
		}
		return cancel;
	}
}

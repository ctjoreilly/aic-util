/*
 * Copyright (c) 2015, SRI International
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
 * Neither the name of the aic-praise nor the names of its
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
package com.sri.ai.util.base;

import com.sri.ai.util.Util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static com.sri.ai.util.Util.join;
import static com.sri.ai.util.Util.mapIntoArray;

/**
 * Uses reflection to access a constructor in a given class only if and when it is required,
 * also wrapping all possible exceptions into {@link Error}.

 * @author braz
 */
public class ConstructorByLazyReflection<T> {

	///////////////// DATA MEMBERS
	
	private Class<? extends T> clazz;
	private Class<?>[] parameterClasses;
	private Constructor<? extends T> constructor;

	///////////////// CONSTRUCTOR
	
	private ConstructorByLazyReflection(Class<? extends T> clazz, Class<?>... parameterClasses) {
		this.clazz = clazz;
		this.parameterClasses = parameterClasses;
	}
	
	public static <T> ConstructorByLazyReflection<T> constructorByLazyReflectionOfClassAndParameters(Class<? extends T> clazz, Class... parameterClasses) {
		return new ConstructorByLazyReflection<>(clazz, parameterClasses);
	}

	///////////////// IMPLEMENTATIONS
	
	public T newInstance(Object... arguments) {
		try {
			return getConstructor().newInstance(arguments);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new Error("Could not create a new instance of " + clazz, e);
		}
	}
	
	private Constructor<? extends T> getConstructor() {
		if (constructor == null) {
			try {
				constructor = clazz.getConstructor(parameterClasses);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new Error(
						"Implementations of " + clazz + " must implement a constructor taking " +
						"arguments of classes " + join(parameterClasses), e);
			}
		}
		return constructor;
	}
}
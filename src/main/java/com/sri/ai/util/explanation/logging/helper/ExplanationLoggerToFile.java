package com.sri.ai.util.explanation.logging.helper;

import java.io.IOException;

import com.sri.ai.util.explanation.logging.core.DefaultExplanationLogger;
import com.sri.ai.util.explanation.logging.core.handler.FileExplanationHandler;

/**
 * A convenience class that extends {@link DefaultExplanationLogger} by automatically
 * creating a {@link FileExplanationHandler} to a file with a given name,
 * as well as being {@link AutoCloseable}.
 * <p>
 * Note that other handlers can be added to the logger, but they will not be automatically closed.
 * 
 * @author braz
 *
 */
public class ExplanationLoggerToFile extends DefaultExplanationLogger implements AutoCloseable {
	
	private FileExplanationHandler handler;
	
	public ExplanationLoggerToFile(String fileName) {
		super();
		try {
			this.handler = new FileExplanationHandler(fileName);
		} catch (IOException e) {
			throw new Error("Cannot open " + fileName, e);
		}
		addHandler(handler);
	}

	@Override
	public void close() {
		try {
			handler.close();
		} catch (Exception e) {
			throw new Error("Unable to close explanation logger handler", e);
		}
	}

}

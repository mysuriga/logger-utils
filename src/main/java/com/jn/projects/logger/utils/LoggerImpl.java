package com.jn.projects.logger.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
 /**
  * Implementation of slf4j logger to log the messages
  * @author mysuriga
  *
  */
 class LoggerImpl {
	
	/*private instance of SLF4J Logger to be used in this API to log*/
	private final Logger slf4jLOGGER;
	private Marker customLEVEL;
	
	
	public LoggerImpl(Class<?> clazz) {
		slf4jLOGGER = LoggerFactory.getLogger(clazz);
	}
	
	public LoggerImpl(String logIdentifier) {
		slf4jLOGGER = LoggerFactory.getLogger(logIdentifier);
	}
	
	public Logger getLOGGER() {
		return slf4jLOGGER;
	}
	
	
	public Marker getCustomLevel() {
		return customLEVEL;
	}

	/**
	 * Logging happens here with the slf4j Logger instance
	 * @param <T>
	 * @param level
	 * @param msg
	 * @param args
	 */
	public <T> void printLog(String level, String msg, T args) {
		
		if(args instanceof Throwable) {
			Throwable t = (Throwable) args;
			switch (level) {
			case "TRACE":
				getLOGGER().trace(msg, t);
				break;
			case "DEBUG":
				getLOGGER().debug(msg, t);
				break;
			case "INFO":
				getLOGGER().info(msg, t);
				break;
			case "WARN":
				getLOGGER().warn(msg, t);
				break;
			case "ERROR":
				getLOGGER().error(msg, t);
				break;
						
			default:
				customLEVEL = MarkerFactory.getMarker(level);
				//Setting the custom level to error level.
				getLOGGER().error(getCustomLevel(), msg, t);
				break;
			}
		} else {
			Object[] varArgs = (Object[]) args;
			switch (level) {
			case "TRACE":
				getLOGGER().trace(msg, varArgs);
				break;
			case "DEBUG":
				getLOGGER().debug(msg, varArgs);
				break;
			case "INFO":
				getLOGGER().info(msg, varArgs);
				break;
			case "WARN":
				getLOGGER().warn(msg, varArgs);
				break;
			case "ERROR":
				getLOGGER().error(msg, varArgs);
				break;
				
			default:
				customLEVEL = MarkerFactory.getMarker(level);
				//Setting the custom level to error level.
				getLOGGER().error(getCustomLevel(), msg, varArgs);
				break;
			}
		}
		
	}
}

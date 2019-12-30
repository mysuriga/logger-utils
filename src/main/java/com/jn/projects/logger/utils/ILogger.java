package com.jn.projects.logger.utils;

/**
 * Entry point of Logger API
 * 
 * @author mysuriga
 *
 */
public interface ILogger {
	
	/**
	 * Log a message at TRACE level with specified format and varargs
	 * @param msg
	 * @param args
	 */
	public void trace(String msg, Object... args);
	/**
	 * Log a message along with an exception at TRACE level
	 * @param msg
	 * @param exception
	 */
	public void trace(String msg, Throwable exception);
	/**
	 * Log a message at INFO level with specified format and varargs
	 * @param msg
	 * @param args
	 */
	public void info(String msg, Object... args);
	/**
	 * Log a message along with an exception at INFO level
	 * @param msg
	 * @param exception
	 */
	public void info(String msg, Throwable exception);
	/**
	 * Log a message at DEBUG level with specified format and varargs
	 * @param msg
	 * @param args
	 */
	public void debug(String msg, Object... args);
	/**
	 * Log a message along with an exception at DEBUG level
	 * @param msg
	 * @param exception
	 */
	public void debug(String msg, Throwable exception);
	/**
	 * Log a message at WARN level with specified format and varargs
	 * @param msg
	 * @param args
	 */
	public void warn(String msg, Object... args);
	/**
	 * Log a message along with an exception at WARN level
	 * @param msg
	 * @param exception
	 */
	public void warn(String msg, Throwable exception);
	/**
	 * Log a message at ERROR level with specified format and varargs
	 * @param msg
	 * @param args
	 */
	public void error(String msg, Object... args);
	/**
	 * Log a message along with an exception at ERROR level
	 * @param msg
	 * @param exception
	 */
	public void error(String msg, Throwable exception);
	/**
	 * Log a message at CUSTOM level with specified format and varargs
	 * @param customLevel
	 * @param msg
	 * @param args
	 */
	public void customLevel(String customLevel, String msg, Object... args);
	/**
	 * Log a message along with an exception at CUSTOM level
	 * @param customLevel
	 * @param msg
	 * @param exception
	 */
	public void customLevel(String customLevel, String msg, Throwable exception);
	
	// setCommonLog
	public void setCommonLog(String param, String value);
	//purge commonlog
	public void clearCommonLog();
	public String getCommonLog(String param);
}

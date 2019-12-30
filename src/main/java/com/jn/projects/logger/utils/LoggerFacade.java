package com.jn.projects.logger.utils;

import org.slf4j.MDC;

import com.jn.projects.logger.utils.traceid.LoggerTraceId;
import com.jn.projects.logger.utils.domain.LogLevel;

/**
 * Logger Facade class to get facade object 
 * 
 * @author mysuriga
 *
 */
public class LoggerFacade<T> implements ILogger {

	private LoggerImpl loggerImpl;
	private LoggerTraceId traceIdImpl;
	/**
	 * Making the constructor package private so that implementing modules cannot initialize
	 * Modules can use {@link LoggerFactory#getLogger()}
	 */
	LoggerFacade(T identifier) {
		loggerImpl = (identifier instanceof Class) ? new LoggerImpl((Class<?>) identifier) : new LoggerImpl((String) identifier);
		traceIdImpl = new LoggerTraceId();
	}

	@Override
	public void trace(String msg, Object... args) {
		getLoggerImpl().printLog(LogLevel.TRACE.name(), msg, args);
		
	}
	@Override
	public void trace(String msg, Throwable exception) {
		getLoggerImpl().printLog(LogLevel.TRACE.name(), msg, exception);
	}	
	@Override
	public void info(String msg, Object... args) {
		getLoggerImpl().printLog(LogLevel.INFO.name(), msg, args);
		
	}
	@Override
	public void info(String msg, Throwable exception) {
		getLoggerImpl().printLog(LogLevel.INFO.name(), msg, exception);
		
	}
	@Override
	public void debug(String msg, Object... args) {
		getLoggerImpl().printLog(LogLevel.DEBUG.name(), msg, args);
		
	}
	@Override
	public void debug(String msg, Throwable exception) {
		getLoggerImpl().printLog(LogLevel.DEBUG.name(), msg, exception);
		
	}
	@Override
	public void warn(String msg, Object... args) {
		getLoggerImpl().printLog(LogLevel.WARN.name(), msg, args);
		
	}
	@Override
	public void warn(String msg, Throwable exception) {
		getLoggerImpl().printLog(LogLevel.WARN.name(), msg, exception);
		
	}
	@Override
	public void error(String msg, Object... args) {
		getLoggerImpl().printLog(LogLevel.ERROR.name(), msg, args);
		
	}
	@Override
	public void error(String msg, Throwable exception) {
		getLoggerImpl().printLog(LogLevel.ERROR.name(), msg, exception);
		
	}
	@Override
	public void customLevel(String customLevel, String msg, Object... args) {
		getLoggerImpl().printLog(customLevel, msg, args);
	}
	@Override
	public void customLevel(String customLevel, String msg, Throwable exception) {
		getLoggerImpl().printLog(customLevel, msg, exception);
	}
	
	/**
	 * Method to add entries of common_log_parameter-value pairs in the MDC map.
	 * @param param
	 * @param value
	 */
	@Override
	public void setCommonLog(String param, String value) {
		MDC.put(param, value);
	}
	
	@Override
	public String getCommonLog(String param) {
		return MDC.get(param);
	}

	/**
	 * Clears all entries in the MDC map.
	 */
	@Override
	public void clearCommonLog() {
		MDC.clear();
		
	}

	/**
	 * Method to store the traceId in MDC
	 * @param traceId
	 */
	public void storeTraceId(String traceId) {
		traceIdImpl.storeTraceId(traceId);
		
	}

	/**
	 * Method to get LoggerTraceId instance
	 * @return LoggerTraceId
	 */
	public LoggerTraceId getTraceIdImpl() {
		return traceIdImpl;
	}

	public LoggerImpl getLoggerImpl() {
		return loggerImpl;
	}
	

}

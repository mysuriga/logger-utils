package com.jn.projects.logger.utils;
/**
 * Outside modules use this Factory class to get the Logger instance 
 * @author mysuriga
 *
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jn.projects.logger.utils.traceid.LoggerTraceId;

public final class LoggerFactory {

	private static LoggerFacade<?> loggerFacade;
	private static Map<String, LoggerFacade<?>> loggerFacadeInstanceMap = new ConcurrentHashMap<>();
	static {
        System.setProperty("log4j.configurationFile", "etc/log4j2.xml");
        System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
    }
	private LoggerFactory() {
	}
	/**
	 * static method to get this library's logger instance
	 * @param <T>
	 * @param logIdentifier
	 * @return Logger
	 */
	public static <T> ILogger getLogger(T logIdentifier) {
		String key;
		if(logIdentifier instanceof Class) {
			key = ((Class<?>) logIdentifier).getName();
		} else {
			key = (String) logIdentifier;
		}
		if (!loggerFacadeInstanceMap.containsKey(key)) {
			loggerFacade = new LoggerFacade<>(logIdentifier);
			loggerFacadeInstanceMap.put(key, loggerFacade);
		}
		
		return loggerFacadeInstanceMap.get(key);
	}

	/**
	 * Static method to get this library's LoggerTraceId instance
	 * @return LoggerTraceId
	 */
	public static LoggerTraceId getTraceIdInstance() {
		LoggerTraceId loggerTraceIdImpl = null;
		if (null != loggerFacade) {
			loggerTraceIdImpl = loggerFacade.getTraceIdImpl();
		}
		return loggerTraceIdImpl;
	}
}

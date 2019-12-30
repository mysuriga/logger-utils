package com.jn.projects.logger.utils.util;

public class LoggerConstants {
	private LoggerConstants() {
	}
	/*Name of the trace_id present in the http request's header*/
	public static final String TRACE_ID = "X-TRACEID";
	
	public static final String KEY_VAL_SEPARATOR = "=";
	
	public static final String RESPONSE_CODE = "code";
	
	public static final String RESPONSE_TIME = "req_ms";
	
	public static final String CPU_TIME = "cpu_ms";
	
	public static final String URL = "url";
	
	public static final String REQUEST_BEGIN = "REQ::BEGIN:";
	
	public static final String REQUEST_END = "REQ::END:";
	
	public static final String ACTIVE_THREADS = "active_threads";
	
	public static final String TOTAL_THREADS = "total_threads";
	
	public static final String REQUEST_METHOD = "method";
	
	public static final String REQUEST_BODY = "request_body";
	
	public static final String ERROR_READING_REQ_BODY = "--Error reading request body of the POST url: Exc_Message=";
	
	public static final String WORD_SPACE = " ";
	
}

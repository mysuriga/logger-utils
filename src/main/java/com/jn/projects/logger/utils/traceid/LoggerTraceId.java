package com.jn.projects.logger.utils.traceid;

import java.util.Optional;

import org.slf4j.MDC;

import com.jn.projects.logger.utils.util.LoggerConstants;
import com.jn.projects.logger.utils.util.LoggerUtil;
/**
 * TraceId Implementer class to generate a UUID and store in {@link MDC} map.
 * @author mysuriga
 *
 */
public class LoggerTraceId {
	
	/**
	 * This method generates a unique traceId if required and stores it in the MDC
	 * @param traceId
	 */
	public void storeTraceId(String traceId) {
		if(!Optional.ofNullable(traceId).filter(s -> !s.isEmpty()).isPresent()) {
			traceId = LoggerUtil.generateTraceId();
		}
		MDC.put(LoggerConstants.TRACE_ID, traceId);
	}
	
	/**
	 * This method gets the traceId from MDC map
	 * @return traceId String
	 */
	public String getTraceId() {
		if(null == MDC.get(LoggerConstants.TRACE_ID))
		{
			storeTraceId(null);
		}
		return MDC.get(LoggerConstants.TRACE_ID);
	}
}

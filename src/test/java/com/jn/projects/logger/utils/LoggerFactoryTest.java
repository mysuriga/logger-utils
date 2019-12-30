package com.jn.projects.logger.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.jn.projects.logger.utils.ILogger;
import com.jn.projects.logger.utils.LoggerFactory;
import com.jn.projects.logger.utils.traceid.LoggerTraceId;
import com.jn.projects.logger.utils.util.LoggerConstants;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoggerFactoryTest {
	@Test
	public void testGetLogger() {
		ILogger logger = LoggerFactory.getLogger(LoggerFactoryTest.class);
		assertNotNull(logger);
		ILogger anotherLogger = LoggerFactory.getLogger(LoggerFactoryTest.class);
		assertEquals(logger, anotherLogger);
		anotherLogger = LoggerFactory.getLogger("identifier");
		assertNotNull(anotherLogger);
		assertNotEquals(logger, anotherLogger);
		logger.setCommonLog(LoggerConstants.TRACE_ID, "2443t");
		assertEquals("2443t", logger.getCommonLog(LoggerConstants.TRACE_ID));
		logger.clearCommonLog();
		assertNull(logger.getCommonLog(LoggerConstants.TRACE_ID));
	}

	@Test
	public void testGetLoggerTraceId() {
		LoggerTraceId traceId = LoggerFactory.getTraceIdInstance();
		assertNotNull(traceId);
		traceId.storeTraceId(null);
		LoggerTraceId anotherTraceId = LoggerFactory.getTraceIdInstance();
		assertEquals(traceId, anotherTraceId);
	}

}

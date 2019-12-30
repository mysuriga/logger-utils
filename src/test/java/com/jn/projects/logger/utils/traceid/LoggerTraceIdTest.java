package com.jn.projects.logger.utils.traceid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.jn.projects.logger.utils.LoggerFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoggerTraceIdTest {

	LoggerTraceId loggerTraceId = new LoggerTraceId();
	private static final String UUID = "9c2946cf-00cc-49b5-9f5f-db06bc83e9f6";

	@Test
	public void testGetTraceId_2() {
		loggerTraceId.storeTraceId(UUID);
		assertEquals(UUID, loggerTraceId.getTraceId());
	}
	@Test
	public void testGetTraceId_1() {
		LoggerFactory.getLogger(this.getClass()).clearCommonLog();
		assertNotNull(loggerTraceId.getTraceId());
		loggerTraceId.storeTraceId(null);
		assertNotNull(loggerTraceId.getTraceId());
		loggerTraceId.storeTraceId("");
		assertNotNull(loggerTraceId.getTraceId());
	}

}

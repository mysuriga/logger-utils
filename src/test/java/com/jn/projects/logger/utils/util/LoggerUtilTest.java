package com.jn.projects.logger.utils.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jn.projects.logger.utils.util.LoggerUtil;

public class LoggerUtilTest {

	@Test
	public void testGenerateTraceId() {
		assertNotNull(LoggerUtil.generateTraceId());
	}

}

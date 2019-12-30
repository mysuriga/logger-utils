package com.jn.projects.logger.utils;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.Marker;

import com.jn.projects.logger.utils.LoggerFacade;
import com.jn.projects.logger.utils.LoggerImpl;

@RunWith(MockitoJUnitRunner.class)
public class LoggerFacadeTest {
	
	
	private LoggerFacade<?> loggerFacade;
	private LoggerImpl mockedLoggerImpl;
	@Mock
	private Exception mockedException;
	@Mock
	private Logger mockedSlf4jLogger;
	@Mock
	private Marker mockedCustomLevelMarker;
	Object[] varArgs = {};
	private static final String MSG = "Message: ";
	
	@Before
	public void setUp() {
		loggerFacade = spy(new LoggerFacade<>(this.getClass()));
		mockedLoggerImpl = spy(new LoggerImpl(getClass()));
		when(loggerFacade.getLoggerImpl()).thenReturn(mockedLoggerImpl);
		when(mockedLoggerImpl.getLOGGER()).thenReturn(mockedSlf4jLogger);
		when(mockedLoggerImpl.getCustomLevel()).thenReturn(mockedCustomLevelMarker);
	}

	@Test
	public void testPrintInfoStringObjectArray() {
		loggerFacade.info(MSG);
		verify(mockedSlf4jLogger).info(MSG, varArgs);
		Object [] varArgss = {""};
		loggerFacade.info(MSG, "");
		verify(mockedSlf4jLogger).info(MSG, varArgss);
	}

	@Test
	public void testPrintInfoStringThrowable() {
		loggerFacade.info(MSG, mockedException);
		verify(mockedSlf4jLogger).info(MSG, mockedException);
	}

	@Test
	public void testPrintTraceStringObjectArray() {
		loggerFacade.trace(MSG);
		verify(mockedSlf4jLogger).trace(MSG, varArgs);
	}

	@Test
	public void testPrintTraceStringThrowable() {
		loggerFacade.trace(MSG, mockedException);
		verify(mockedSlf4jLogger).trace(MSG, mockedException);
	}
	
	@Test
	public void testPrintDebugStringObjectArray() {
		loggerFacade.debug(MSG);
		verify(mockedSlf4jLogger).debug(MSG, varArgs);
	}

	@Test
	public void testPrintDebugStringThrowable() {
		loggerFacade.debug(MSG, mockedException);
		verify(mockedSlf4jLogger).debug(MSG, mockedException);
	}

	@Test
	public void testPrintWarnStringObjectArray() {
		loggerFacade.warn(MSG);
		verify(mockedSlf4jLogger).warn(MSG, varArgs);
	}

	@Test
	public void testPrintWarnStringThrowable() {
		loggerFacade.warn(MSG, mockedException);
		verify(mockedSlf4jLogger).warn(MSG, mockedException);
	}

	@Test
	public void testPrintErrorStringObjectArray() {
		loggerFacade.error(MSG);
		verify(mockedSlf4jLogger).error(MSG, varArgs);
	}

	@Test
	public void testPrintErrorStringThrowable() {
		loggerFacade.error(MSG, mockedException);
		verify(mockedSlf4jLogger).error(MSG, mockedException);
	}

	@Test
	public void testPrintCustomLevelStringStringObjectArray() {
		loggerFacade.customLevel("CUSTOM_LEVEL", MSG);
		verify(mockedSlf4jLogger).error(mockedCustomLevelMarker, MSG, varArgs);
	}

	@Test
	public void testPrintCustomLevelStringStringThrowable() {
		loggerFacade.customLevel("CUSTOM_LEVEL", MSG, mockedException);
		verify(mockedSlf4jLogger).error(mockedCustomLevelMarker, MSG, mockedException);
	}

}

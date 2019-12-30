package com.jn.projects.logger.utils.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.MDC;

import com.jn.projects.logger.utils.util.LoggerConstants;
import com.jn.projects.logger.utils.util.LoggerUtil;

@RunWith(MockitoJUnitRunner.class)
public class RequestLoggerFilterTest {

	@InjectMocks
	private RequestLoggerFilter requestLoggerFilter;

	@Mock
	private HttpServletRequest mockedHttpServletReq;
	@Mock
	private HttpServletResponse mockedHttpServletResponse;
	@Mock
	private FilterChain mockedFilterChain;
	@Mock
	private Map<Thread, StackTraceElement[]> mockedTotalThreadsMap;
	@Mock
	private Set<Thread> mockedTotalThreadsSet;
	private static final String UUID = "9c2946cf-00cc-49b5-9f5f-db06bc83e9f6";
	private static final String REQ_URL = "http://localhost:8182/rest/dummy";
	private static final Integer RESP_CODE = 200;
	private static final long RESP_TIME = 50;
	private static final String NUMBER_REGEX = "\\d+";
	static private final String REQ_BODY = "{\"apiversion\":\"1.0\", \"request\":\"dummy\", \"sapisession\":\"abcd-aa-bb-11\"}";
	static private final String REQ_BODY_REGEX = "\\{\"apiversion\":\"1.0\", \"request\":\"dummy\", \"sapisession\":\"abcd-aa-bb-11\"\\}";
	private static final String EXPECTED_REQ_END_MSG = LoggerConstants.REQUEST_END + LoggerConstants.WORD_SPACE 
			+ LoggerConstants.RESPONSE_CODE + LoggerConstants.KEY_VAL_SEPARATOR + RESP_CODE  + LoggerConstants.WORD_SPACE 
			+ LoggerConstants.RESPONSE_TIME + LoggerConstants.KEY_VAL_SEPARATOR + RESP_TIME + LoggerConstants.WORD_SPACE
			+ LoggerConstants.CPU_TIME + LoggerConstants.KEY_VAL_SEPARATOR + RESP_TIME + LoggerConstants.WORD_SPACE
			+ LoggerConstants.URL + LoggerConstants.KEY_VAL_SEPARATOR + REQ_URL;
	private static final String EXPECTED_GET_REQ_BEGIN_MSG_REGEX = LoggerConstants.REQUEST_BEGIN + LoggerConstants.WORD_SPACE 
			+ LoggerConstants.ACTIVE_THREADS + LoggerConstants.KEY_VAL_SEPARATOR + NUMBER_REGEX  + LoggerConstants.WORD_SPACE
			+ LoggerConstants.TOTAL_THREADS + LoggerConstants.KEY_VAL_SEPARATOR + NUMBER_REGEX + LoggerConstants.WORD_SPACE
			+ LoggerConstants.REQUEST_METHOD + LoggerConstants.KEY_VAL_SEPARATOR + "GET"  + LoggerConstants.WORD_SPACE
			+ LoggerConstants.URL + LoggerConstants.KEY_VAL_SEPARATOR + REQ_URL;
	private static final String EXPECTED_POST_REQ_BEGIN_MSG_REGEX = LoggerConstants.REQUEST_BEGIN + LoggerConstants.WORD_SPACE
			+ LoggerConstants.ACTIVE_THREADS + LoggerConstants.KEY_VAL_SEPARATOR + NUMBER_REGEX  + LoggerConstants.WORD_SPACE
			+ LoggerConstants.TOTAL_THREADS + LoggerConstants.KEY_VAL_SEPARATOR + NUMBER_REGEX + LoggerConstants.WORD_SPACE
			+ LoggerConstants.REQUEST_METHOD + LoggerConstants.KEY_VAL_SEPARATOR + "POST"  + LoggerConstants.WORD_SPACE
			+ LoggerConstants.URL + LoggerConstants.KEY_VAL_SEPARATOR + REQ_URL + LoggerConstants.WORD_SPACE
			+ LoggerConstants.REQUEST_BODY + LoggerConstants.KEY_VAL_SEPARATOR + REQ_BODY_REGEX;
	
	
	
	@Test
	public void testDoFilter() throws Exception {
		String queryString = "param1=1&param2=2";
		when(mockedHttpServletReq.getRequestURL()).thenReturn(new StringBuffer(REQ_URL));
		when(mockedHttpServletReq.getQueryString()).thenReturn(queryString);
		when(mockedHttpServletResponse.getStatus()).thenReturn(200);
		when(mockedHttpServletReq.getHeader(LoggerConstants.TRACE_ID)).thenReturn(LoggerUtil.generateTraceId());
		requestLoggerFilter.doFilter(mockedHttpServletReq, mockedHttpServletResponse, mockedFilterChain);
		verify(mockedHttpServletReq, times(1)).getQueryString();
	}
	
	@Test
	public void testDoFilter_POST_req() throws Exception {
		when(mockedHttpServletReq.getRequestURL()).thenReturn(new StringBuffer(REQ_URL));
		when(mockedHttpServletReq.getMethod()).thenReturn("POST");
		when(mockedHttpServletResponse.getStatus()).thenReturn(200);
		when(mockedHttpServletReq.getHeader(LoggerConstants.TRACE_ID)).thenReturn(UUID);
		requestLoggerFilter.doFilter(mockedHttpServletReq, mockedHttpServletResponse, mockedFilterChain);
		verify(mockedHttpServletReq, times(1)).getReader();
	}
	
	@Test
	public void testBuildRequestBeginLogMsg() throws IOException {
		when(mockedHttpServletReq.getMethod()).thenReturn("GET");
		String msg = requestLoggerFilter.buildRequestBeginLogMsg(mockedHttpServletReq, REQ_URL);
		assertTrue(msg.matches(EXPECTED_GET_REQ_BEGIN_MSG_REGEX));
	}
	
	@Test
	public void testBuildRequestBeginLogMsg_POST_req() throws IOException {
		BufferedReader mockedBuffReader = mock(BufferedReader.class);
		when(mockedHttpServletReq.getReader()).thenReturn(mockedBuffReader);
		Stream<String> reqBodyStream = Stream.of(REQ_BODY);
		when(mockedBuffReader.lines()).thenReturn(reqBodyStream);
		when(mockedHttpServletReq.getMethod()).thenReturn("POST");
		String msg = requestLoggerFilter.buildRequestBeginLogMsg(mockedHttpServletReq, REQ_URL);
		assertTrue(msg.matches(EXPECTED_POST_REQ_BEGIN_MSG_REGEX));
	}
	
	@Test
	public void testBuildRequestEndLogMsg() throws IOException {
		when(mockedHttpServletResponse.getStatus()).thenReturn(RESP_CODE);
		String msg = requestLoggerFilter.buildRequestEndLogMsg(mockedHttpServletResponse, REQ_URL, RESP_TIME, RESP_TIME);
		assertEquals(EXPECTED_REQ_END_MSG, msg);
		
	}
	
	@Test(expected = IOException.class)
	public void testDoFilter_for_exception() throws IOException, ServletException {
		when(mockedHttpServletReq.getRequestURL()).thenReturn(new StringBuffer(REQ_URL));
		try {
			requestLoggerFilter.doFilter(mockedHttpServletReq, mockedHttpServletResponse, mockedFilterChain);
			assertEquals(0, MDC.getCopyOfContextMap().size());
			doThrow(new IOException()).when(mockedFilterChain).doFilter(mockedHttpServletReq, mockedHttpServletResponse);
			requestLoggerFilter.doFilter(mockedHttpServletReq, mockedHttpServletResponse, mockedFilterChain);
		} catch (IOException e) {
			//Making sure MDC gets cleared after exception
			assertEquals(0, MDC.getCopyOfContextMap().size());
			throw new IOException();
		}
	}
}

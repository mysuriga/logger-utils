package com.jn.projects.logger.utils.filter;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jn.projects.logger.utils.LoggerFacade;
import com.jn.projects.logger.utils.LoggerFactory;
import com.jn.projects.logger.utils.util.LoggerConstants;
import com.jn.projects.logger.utils.util.LoggerUtil;

/**
 * This Filter will be added to client modules's listener.
 * Has two main methods called from doFilter()-
 *  1. {@link #startRequest()}
 *  2. {@link #endRequest()}
 *	
 * @author mysuriga
 */
public class RequestLoggerFilter implements Filter {

	private LoggerFacade<?> loggerFacade = (LoggerFacade<?>) LoggerFactory.getLogger(RequestLoggerFilter.class);

	static {
		System.setProperty("log4j.configurationFile", "etc/log4j2.xml");
		System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Filter initialization
	}

	/**
	 * 
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		final long reqStartTime = System.currentTimeMillis();
		final long cpuStartTime = LoggerUtil.getCPUTime(Thread.currentThread().getId());
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String url = httpRequest.getRequestURL().toString();
		String queryString = httpRequest.getQueryString();
		url = queryString == null ? url : url + "?" + queryString;
		startRequest(httpRequest, url);
		try {
			filterChain.doFilter(httpRequest, httpResponse);
		} finally {
			endRequest(httpResponse, url, reqStartTime, cpuStartTime);
			loggerFacade.clearCommonLog();
		}
	}

	/**
	 * This method is used to- - Extract traceId present in request header or
	 * generate a new one otherwise. - Save the traceId in MDC - Log the request
	 * beginning message details
	 * 
	 * @param httpRequest
	 */
	protected void startRequest(HttpServletRequest httpRequest, String reqUrl) {
		loggerFacade.storeTraceId(httpRequest.getHeader(LoggerConstants.TRACE_ID));
		// log the request begin details
		loggerFacade.info(buildRequestBeginLogMsg(httpRequest, reqUrl));
	}

	/**
	 * Method to build request start message - grammar
	 * 
	 * @param httpRequest
	 * @return String
	 */
	protected String buildRequestBeginLogMsg(HttpServletRequest httpRequest, String reqUrl) {
		String requestBody;
		StringBuilder msg = new StringBuilder(LoggerConstants.REQUEST_BEGIN);
		msg.append(
				LoggerConstants.WORD_SPACE + LoggerConstants.ACTIVE_THREADS + LoggerConstants.KEY_VAL_SEPARATOR
						+ Thread.activeCount() + LoggerConstants.WORD_SPACE + LoggerConstants.TOTAL_THREADS
						+ LoggerConstants.KEY_VAL_SEPARATOR + Thread.getAllStackTraces().keySet().size()
						+ LoggerConstants.WORD_SPACE + LoggerConstants.REQUEST_METHOD
						+ LoggerConstants.KEY_VAL_SEPARATOR + httpRequest.getMethod() + LoggerConstants.WORD_SPACE
						+ LoggerConstants.URL + LoggerConstants.KEY_VAL_SEPARATOR + reqUrl);

		if ("POST".equals(httpRequest.getMethod())) {
			try {
				requestBody = httpRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			} catch (Exception e) {
				return msg.append(
						LoggerConstants.WORD_SPACE + LoggerConstants.ERROR_READING_REQ_BODY + e.getMessage())
						.toString();

			}
			msg.append(LoggerConstants.WORD_SPACE + LoggerConstants.REQUEST_BODY
					+ LoggerConstants.KEY_VAL_SEPARATOR + requestBody);
		}
		return msg.toString();
	}

	/**
	 * This method is used to - - Log the request end message details
	 * 
	 * @param httpResponse
	 * @param reqURL
	 */
	protected void endRequest(HttpServletResponse httpResponse, String reqURL, long reqStartTime, long cpuStartTime) {
		// log the request end details
		loggerFacade.info(buildRequestEndLogMsg(httpResponse, reqURL, System.currentTimeMillis() - reqStartTime,
				(LoggerUtil.getCPUTime(Thread.currentThread().getId()) - cpuStartTime) / 1000000));

	}

	/**
	 * Method to build request end message - grammar
	 * 
	 * @param httpRequest
	 * @param requestURL
	 * @return String
	 */
	protected String buildRequestEndLogMsg(HttpServletResponse httpResponse, String reqURL, long requestTime,
			long cpuTime) {
		StringBuilder msg = new StringBuilder(LoggerConstants.REQUEST_END);
		msg.append(LoggerConstants.WORD_SPACE + LoggerConstants.RESPONSE_CODE + LoggerConstants.KEY_VAL_SEPARATOR + httpResponse.getStatus())
				.append(LoggerConstants.WORD_SPACE + LoggerConstants.RESPONSE_TIME + LoggerConstants.KEY_VAL_SEPARATOR + requestTime)
				.append(LoggerConstants.WORD_SPACE + LoggerConstants.CPU_TIME + LoggerConstants.KEY_VAL_SEPARATOR + cpuTime)
				.append(LoggerConstants.WORD_SPACE + LoggerConstants.URL + LoggerConstants.KEY_VAL_SEPARATOR + reqURL);

		return msg.toString();
	}

	@Override
	public void destroy() {
		// Filter's destroy method
	}

}

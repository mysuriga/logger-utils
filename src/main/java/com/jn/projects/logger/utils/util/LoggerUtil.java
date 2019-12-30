package com.jn.projects.logger.utils.util;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.UUID;

/**
 * Utility class with static helper methods
 *
 */
public class LoggerUtil {

	private LoggerUtil() {
	}
	/**
	 * This method generates a uuid({@link UUID}) which will be used as traceId
	 * @return UUID string
	 */
	public static String generateTraceId() {
		return UUID.randomUUID().toString();
	}
	
	/**
     * Method to get the CPU time of a given thread ID.
     *
     * @param threadID
     */
    public static long getCPUTime(long threadID) {
            ThreadMXBean bean = ManagementFactory.getThreadMXBean();
            if (!bean.isThreadCpuTimeSupported()) {
                    return 0L;
            }
            return bean.getThreadCpuTime(threadID);
    }
}

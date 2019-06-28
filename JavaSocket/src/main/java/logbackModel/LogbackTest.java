package logbackModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackTest {
	private static final Logger LOGGER = LoggerFactory.getLogger("game.runtimeabc");

	public static void main(String[] args) {
		LOGGER.trace("logback的--trace日志--输出了");
		LOGGER.debug("logback的--debug日志--输出了");
		LOGGER.info("logback的--info日志--输出了");
		LOGGER.warn("logback的--warn日志--输出了");
		LOGGER.error("logback的--error日志--输出了");
		
		// 如果log4j的配置中设置了debug级别，那么就可以输出其他debug的日志，在日志中标记为[DEBUG].
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("bug！");
		}
		
	}

}

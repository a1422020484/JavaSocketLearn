package nettyServer.util;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class LogFilter extends Filter<ILoggingEvent> {

	@Override
	public FilterReply decide(ILoggingEvent event) {
        if (GameConfig.IsDebug) {
            if (event.getMessage().contains("2003")
                    || event.getMessage().contains("2004")
                    || event.getMessage().contains("5001")
                    || event.getMessage().contains("20001") // 注册Center
                    ) {
                return FilterReply.DENY;
            }
        }
		return FilterReply.ACCEPT;
	}

}

package toys.application;

import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.TriggeringEventEvaluator;

/**
 * Esta classe é utilizada pelo Log4j para fazer com que cada evento de log seja enviado por e-mail
 * ao invés de ser colocado no buffer.
 * @author Iran
 */
public class SmtpAppenderTriggeringEventEvaluator implements TriggeringEventEvaluator {

	@Override
	public boolean isTriggeringEvent(LoggingEvent event) {
		return true;
	}

}

package logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggingConfigure {
	public static Logger getLogger(Class<?> clss) {
		Logger logger = Logger.getLogger(clss.getCanonicalName());
		logger.setLevel(Level.ALL);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new SimpleFormatter());
		logger.addHandler(handler);
		handler.setLevel(Level.ALL);
		return logger;
	}
}

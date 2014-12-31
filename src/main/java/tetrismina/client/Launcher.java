package tetrismina.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.richclient.application.ApplicationLauncher;

public class Launcher {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(Launcher.class);

	public void launch() {
		String startupContextPath = "ctx/richclient-startup-ctx.xml";
		String richclientContextPath = "ctx/richclient-ctx.xml";
		String tetrisClientContextPath = "ctx/tetrisclient-ctx.xml";
		String[] contextPaths = new String[] { tetrisClientContextPath,
				richclientContextPath };
		try {
			new ApplicationLauncher(startupContextPath, contextPaths);
		} catch (RuntimeException e) {
			LOGGER.error("RuntimeException during startup", e);
		}
	}
}

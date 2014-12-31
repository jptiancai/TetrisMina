package tetrismina.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerMain {

	private static final int PORT = 9190;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServerMain.class);

	public static void main(String[] args) {
		try {
			new TetrisServer(PORT).start();
			LOGGER.info("Server started at port {}.", PORT);
		} catch (IOException e) {
			LOGGER.error("Can't start server!", e);
		}
	}

}

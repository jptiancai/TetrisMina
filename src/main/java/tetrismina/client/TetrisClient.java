package tetrismina.client;

import java.net.InetSocketAddress;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tetrismina.codec.TetrisCodecFactory;
import tetrismina.command.AbstractTetrisCommand;

public class TetrisClient {
	private String host;
	private int port;
	private SocketConnector connector;
	private IoSession session;

	private static final long CONNECT_TIMEOUT = 30 * 1000L;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TetrisClient.class);

	private IoHandler handler;

	public TetrisClient(String host, int port) {
		this.host = host;
		this.port = port;
		connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("protocol",
				new ProtocolCodecFilter(new TetrisCodecFactory()));
	}

	public void setHandler(IoHandler handler) {
		this.handler = handler;
		connector.setHandler(this.handler);
	}

	public void connect() {
		ConnectFuture connectFuture = connector.connect(new InetSocketAddress(
				host, port));
		connectFuture.awaitUninterruptibly(CONNECT_TIMEOUT);
		try {
			session = connectFuture.getSession();
		} catch (RuntimeIoException e) {
			LOGGER.warn(e.getMessage(), e);
		}
	}

	public void disconnect() {
		if (session != null) {
			session.close(false).awaitUninterruptibly(CONNECT_TIMEOUT);
			session = null;
		}
	}

	public IoSession getSession() {
		return this.session;
	}

	public void sendCommand(AbstractTetrisCommand command) {
		if (session != null) {
			session.write(command);
		}
	}
}

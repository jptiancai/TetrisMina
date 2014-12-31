package tetrismina.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.statemachine.StateMachine;
import org.apache.mina.statemachine.StateMachineFactory;
import org.apache.mina.statemachine.StateMachineProxyBuilder;
import org.apache.mina.statemachine.annotation.IoHandlerTransition;
import org.apache.mina.statemachine.context.IoSessionStateContextLookup;
import org.apache.mina.statemachine.context.StateContext;
import org.apache.mina.statemachine.context.StateContextFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import tetrismina.codec.TetrisCodecFactory;

public class TetrisServer {
	private NioSocketAcceptor acceptor;

	private int port;

	public TetrisServer(int port) {
		this.port = port;
		acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("protocol",
				new ProtocolCodecFilter(new TetrisCodecFactory()));
		acceptor.setHandler(createIoHandler());
	}

	private static IoHandler createIoHandler() {
		StateMachine sm = StateMachineFactory.getInstance(
				IoHandlerTransition.class).create(ServerHandler.NOT_CONNECTED,
				new ServerHandler());
		return new StateMachineProxyBuilder().setStateContextLookup(
				new IoSessionStateContextLookup(new StateContextFactory() {
					public StateContext create() {
						return new ServerHandler.TetrisServerContext();
					}
				})).create(IoHandler.class, sm);
	}

	public void start() throws IOException {
		acceptor.bind(new InetSocketAddress(port));
	}
}

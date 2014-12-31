package tetrismina.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tetrismina.client.domain.PlayerDataSource;
import tetrismina.command.AbstractTetrisCommand;
import tetrismina.command.concrete.GameWinCommand;
import tetrismina.command.concrete.InvitationReceivedCommand;
import tetrismina.command.concrete.RefreshPlayersListCommand;
import tetrismina.command.concrete.StartGameCommand;
import tetrismina.command.concrete.SyncBoardCommand;

public class ClientHandler extends IoHandlerAdapter {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ClientHandler.class);

	private List<CommandCallback> callbacks = new ArrayList<CommandCallback>();

	public void addCommandCallback(CommandCallback callback) {
		if (callback != null) {
			callbacks.add(callback);
		}
	}

	public void setCallbacks(List<CommandCallback> callbacks) {
		this.callbacks = callbacks;
	}

	public void sessionOpened(IoSession session) throws Exception {

	}

	public void messageReceived(IoSession session, Object message)
			throws Exception {
		if (!(message instanceof AbstractTetrisCommand)) {
			LOGGER.warn("Unexpected message, drop it.");
		}
		AbstractTetrisCommand command = (AbstractTetrisCommand) message;
		LOGGER.info(command.toString());
		if (RefreshPlayersListCommand.NAME.equals(command.getName())) {
			RefreshPlayersListCommand rplc = (RefreshPlayersListCommand) command;
			for (CommandCallback callback : callbacks) {
				callback.refreshPlayersList(PlayerDataSource.valueOf(rplc
						.getPlayers()));
			}
		} else if (InvitationReceivedCommand.NAME.equals(command.getName())) {
			InvitationReceivedCommand irc = (InvitationReceivedCommand) command;
			for (CommandCallback callback : callbacks) {
				callback.receiveInvitation(irc.getInviterName());
			}
		} else if (StartGameCommand.NAME.equals(command.getName())) {
			StartGameCommand sgc = (StartGameCommand) command;
			for (CommandCallback callback : callbacks) {
				callback.startGame(sgc.getOpponent());
			}
		} else if (SyncBoardCommand.NAME.equals(command.getName())) {
			SyncBoardCommand sbc = (SyncBoardCommand) command;
			for (CommandCallback callback : callbacks) {
				callback.syncBoard(sbc.getSequence(), sbc.getBoardData());
			}
		} else if (GameWinCommand.NAME.equals(command.getName())) {
			for (CommandCallback callback : callbacks) {
				callback.winGame();
			}
		}

	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		LOGGER.warn(cause.getMessage(), cause);
	}

}

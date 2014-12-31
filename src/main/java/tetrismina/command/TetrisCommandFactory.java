package tetrismina.command;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import tetrismina.command.concrete.AcceptInvitationCommand;
import tetrismina.command.concrete.ChangeNickNameCommand;
import tetrismina.command.concrete.GameLostCommand;
import tetrismina.command.concrete.GameWinCommand;
import tetrismina.command.concrete.InvitationReceivedCommand;
import tetrismina.command.concrete.InviteCommand;
import tetrismina.command.concrete.LoginCommand;
import tetrismina.command.concrete.RefreshPlayersListCommand;
import tetrismina.command.concrete.StartGameCommand;
import tetrismina.command.concrete.StartGameConfirmCommand;
import tetrismina.command.concrete.SyncBoardCommand;

public final class TetrisCommandFactory {
	private static Map<String, Class<? extends Command>> registry = new HashMap<String, Class<? extends Command>>();

	private TetrisCommandFactory() {

	}

	static {
		register(SyncBoardCommand.NAME, SyncBoardCommand.class);
		register(LoginCommand.NAME, LoginCommand.class);
		register(ChangeNickNameCommand.NAME, ChangeNickNameCommand.class);
		register(RefreshPlayersListCommand.NAME,
				RefreshPlayersListCommand.class);
		register(InviteCommand.NAME, InviteCommand.class);
		register(InvitationReceivedCommand.NAME,
				InvitationReceivedCommand.class);
		register(AcceptInvitationCommand.NAME, AcceptInvitationCommand.class);
		register(StartGameCommand.NAME, StartGameCommand.class);
		register(StartGameConfirmCommand.NAME, StartGameConfirmCommand.class);
		register(GameWinCommand.NAME, GameWinCommand.class);
		register(GameLostCommand.NAME, GameLostCommand.class);
	}

	private static void register(String name, Class<? extends Command> clazz) {
		if (name != null && clazz != null) {
			registry.put(name, clazz);
		}
	}

	public static AbstractTetrisCommand newCommand(String name) {
		if (StringUtils.isNotEmpty(name)) {
			Class<? extends Command> clazz = registry.get(name);
			if (clazz != null) {
				if (AbstractTetrisCommand.class.isAssignableFrom(clazz)) {
					try {
						return (AbstractTetrisCommand) clazz.newInstance();
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}
			}
		}
		return null;
	}
}

package tetrismina.client.ui;

import java.awt.BorderLayout;
import java.util.Collections;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.richclient.application.PageComponentContext;
import org.springframework.richclient.application.support.AbstractView;
import org.springframework.richclient.command.ActionCommand;
import org.springframework.richclient.command.ActionCommandExecutor;
import org.springframework.richclient.command.CommandGroup;
import org.springframework.richclient.dialog.ConfirmationDialog;
import org.springframework.richclient.dialog.InputApplicationDialog;
import org.springframework.richclient.dialog.MessageDialog;
import org.springframework.richclient.util.PopupMenuMouseListener;
import org.springframework.richclient.widget.table.PropertyColumnTableDescription;
import org.springframework.richclient.widget.table.glazedlists.GlazedListTableWidget;
import org.springframework.rules.closure.Closure;

import tetrismina.client.ClientHandler;
import tetrismina.client.CommandCallback;
import tetrismina.client.TetrisClient;
import tetrismina.client.domain.Player;
import tetrismina.client.domain.PlayerDataSource;
import tetrismina.client.ui.game.GameCanvas;
import tetrismina.command.AbstractTetrisCommand;
import tetrismina.command.concrete.AcceptInvitationCommand;
import tetrismina.command.concrete.GameLostCommand;
import tetrismina.command.concrete.GameWinCommand;
import tetrismina.command.concrete.InviteCommand;
import tetrismina.command.concrete.LoginCommand;
import tetrismina.command.concrete.StartGameConfirmCommand;
import tetrismina.command.concrete.SyncBoardCommand;

public class GameLobbyView extends AbstractView implements ApplicationListener,
		CommandCallback, GameEventListener {

	private GlazedListTableWidget widget;

	private LoginExecutor loginExecutor = new LoginExecutor();

	private InviteExecutor inviteExecutor = new InviteExecutor();

	private TetrisClient tetrisClient = null;

	private ClientHandler clientHandler = null;

	private String currentUser = null;

	private GameCanvas gameCanvas = null;

	private int syncBoardSeq = 0;

	public void setTetrisClient(TetrisClient tetrisClient) {
		this.tetrisClient = tetrisClient;
	}

	public void setClientHandler(ClientHandler clientHandler) {
		if (clientHandler != null) {
			this.clientHandler = clientHandler;
			this.clientHandler.addCommandCallback(this);
		}
	}

	@Override
	protected JComponent createControl() {
		PropertyColumnTableDescription desc = new PropertyColumnTableDescription(
				"playersTable", Player.class);
		desc.addPropertyColumn("nickname");
		desc.addPropertyColumn("status");
		widget = new GlazedListTableWidget(Collections.emptyList(), desc);
		JPanel table = new JPanel(new BorderLayout());
		table.add(widget.getComponent(), BorderLayout.CENTER);

		CommandGroup popup = new CommandGroup();
		popup.add((ActionCommand) getWindowCommandManager().getCommand(
				"inviteCommand", ActionCommand.class));
		JPopupMenu popupMenu = popup.createPopupMenu();
		widget.getTable().addMouseListener(
				new PopupMenuMouseListener(popupMenu));

		JPanel view = getComponentFactory().createPanel(new BorderLayout());
		view.add(table, BorderLayout.CENTER);

		return view;
	}

	protected void registerLocalCommandExecutors(PageComponentContext context) {
		context.register("loginCommand", loginExecutor);
		context.register("inviteCommand", inviteExecutor);
	}

	public void onApplicationEvent(ApplicationEvent event) {

	}

	public void refreshPlayersList(PlayerDataSource players) {
		widget.setRows(players.getPlayersExclude(currentUser));
	}

	public void receiveInvitation(String inviterName) {
		new AcceptInvitationDialog(inviterName).showDialog();
	}

	private void sendCommand(AbstractTetrisCommand command) {
		tetrisClient.sendCommand(command);
	}

	public void startGame(String opponent) {
		gameCanvas = new GameCanvas(this);
		String gameWindowTitle = getMessage("gameWindow.title",
				new Object[] { opponent });
		gameCanvas.setWindowTitle(gameWindowTitle);
		sendCommand(new StartGameConfirmCommand());
	}

	public void syncBoard(int seq, int[][] data) {
		gameCanvas.synchronize(seq, data);
	}
	
	public void winGame() {
		showGameWinMessageDialog();
		sendCommand(new GameWinCommand());
		gameCanvas.stopGame();
	}
	
	private void showGameWinMessageDialog() {
		String title = getMessage("gameWinMessage.title");
		String message = getMessage("gameWinMessage.message");
		MessageDialog dialog = new MessageDialog(title, message);
		dialog.showDialog();
	}

	public void gameOver() {
		showGameOverMessageDialog();
		sendCommand(new GameLostCommand());
		gameCanvas.stopGame();
	}

	private void showGameOverMessageDialog() {
		String title = getMessage("gameOverMessage.title");
		String message = getMessage("gameOverMessage.message");
		MessageDialog dialog = new MessageDialog(title, message);
		dialog.showDialog();
	}

	public void rowsCleared(int rowNum) {

	}

	public void notifyChanged(int[][] board) {
		SyncBoardCommand sbc = new SyncBoardCommand(syncBoardSeq, board);
		syncBoardSeq++;
		tetrisClient.sendCommand(sbc);
	}

	private class LoginExecutor implements ActionCommandExecutor {

		public LoginExecutor() {

		}

		public void execute() {
			new NickNameInputDialog(tetrisClient).showDialog();

		}
	}

	private class NickNameInputDialog extends InputApplicationDialog {

		public NickNameInputDialog(final TetrisClient tetrisClient) {
			setTitle(getMessage("inputNickName.dialog.title"));
			setInputLabelMessage("inputNickName.dialog.label");
			setFinishAction(new Closure() {

				public Object call(Object inputValue) {
					if (inputValue != null) {
						String nickName = inputValue.toString();
						LoginCommand command = new LoginCommand(nickName);
						tetrisClient.sendCommand(command);
						currentUser = nickName;
						return true;
					}
					return false;
				}

			});
		}
	}

	private class AcceptInvitationDialog extends ConfirmationDialog {

		private String inviterName;

		public AcceptInvitationDialog(String inviterName) {
			this.inviterName = inviterName;
			setTitle(getMessage("acceptInvitation.title",
					new Object[] { inviterName }));
			setConfirmationMessage(getMessage("acceptInvitation.description",
					new Object[] { inviterName }));
		}

		@Override
		protected void onConfirm() {
			AcceptInvitationCommand command = new AcceptInvitationCommand();
			command.setInviterName(inviterName);
			tetrisClient.sendCommand(command);
		}
	}

	private class InviteExecutor implements ActionCommandExecutor {

		public void execute() {
			for (Object selected : widget.getSelectedRows()) {
				Player player = (Player) selected;
				InviteCommand command = new InviteCommand();
				command.setInviteeName(player.getNickname());
				tetrisClient.sendCommand(command);
			}
		}

	}

}

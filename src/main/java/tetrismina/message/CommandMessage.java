package tetrismina.message;

import tetrismina.command.AbstractTetrisCommand;

public class CommandMessage {

	private AbstractTetrisCommand command;

	public CommandMessage(AbstractTetrisCommand command) {
		this.command = command;
	}

	public AbstractTetrisCommand getCommand() {
		return this.command;
	}
}

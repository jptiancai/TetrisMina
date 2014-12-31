package tetrismina.command.concrete;

import tetrismina.command.AbstractTetrisCommand;

public class StartGameCommand extends AbstractTetrisCommand {

	public static final String NAME = "StartGame";

	private String opponent = "";

	public StartGameCommand() {

	}

	public StartGameCommand(String opponent) {
		this.opponent = opponent;
	}

	public String getOpponent() {
		return opponent;
	}

	@Override
	public void bodyFromBytes(byte[] bytes) throws Exception {
		opponent = new String(bytes);
	}

	@Override
	public byte[] bodyToBytes() throws Exception {
		return opponent.getBytes();
	}

	@Override
	public String getName() {
		return NAME;
	}

}

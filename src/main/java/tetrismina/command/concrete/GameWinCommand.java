package tetrismina.command.concrete;

import tetrismina.command.AbstractTetrisCommand;

public class GameWinCommand extends AbstractTetrisCommand {
	
	public static final String NAME = "GameWin";

	@Override
	public void bodyFromBytes(byte[] bytes) throws Exception {

	}

	@Override
	public byte[] bodyToBytes() throws Exception {
		return new byte[] {};
	}

	@Override
	public String getName() {
		return NAME;
	}

}

package tetrismina.command.concrete;

import tetrismina.command.AbstractTetrisCommand;

public class GameLostCommand extends AbstractTetrisCommand {

	public static final String NAME = "GameLost";
	
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

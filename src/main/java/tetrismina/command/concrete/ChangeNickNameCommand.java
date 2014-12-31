package tetrismina.command.concrete;

import tetrismina.command.AbstractTetrisCommand;

public class ChangeNickNameCommand extends AbstractTetrisCommand {
	public static final String NAME = "ChangeNick";

	@Override
	public void bodyFromBytes(byte[] bytes) {

	}

	@Override
	public byte[] bodyToBytes() {
		return new byte[0];
	}

	@Override
	public String getName() {
		return NAME;
	}

}

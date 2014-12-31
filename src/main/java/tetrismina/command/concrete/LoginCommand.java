package tetrismina.command.concrete;

import tetrismina.command.AbstractTetrisCommand;

public class LoginCommand extends AbstractTetrisCommand {
	private String nickName = "";

	public static final String NAME = "Login";

	public LoginCommand() {

	}

	public LoginCommand(String nickName) {
		setNickName(nickName);
	}

	public void setNickName(String nickName) {
		if (nickName == null) {
			throw new IllegalArgumentException("Nick name can't be empty.");
		}
		this.nickName = nickName;
	}

	public String getNickName() {
		return this.nickName;
	}

	@Override
	public void bodyFromBytes(byte[] bytes) {
		this.setNickName(new String(bytes));
	}

	@Override
	public byte[] bodyToBytes() {
		return this.nickName.getBytes();
	}

	@Override
	public String getName() {
		return NAME;
	}

}

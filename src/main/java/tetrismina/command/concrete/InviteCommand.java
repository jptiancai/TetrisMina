package tetrismina.command.concrete;

import tetrismina.command.AbstractTetrisCommand;

public class InviteCommand extends AbstractTetrisCommand {

	public static final String NAME = "Invite";

	private String inviteeName = "";

	public String getInviteeName() {
		return inviteeName;
	}

	public void setInviteeName(String inviteeName) {
		this.inviteeName = inviteeName;
	}

	@Override
	public void bodyFromBytes(byte[] bytes) throws Exception {
		this.setInviteeName(new String(bytes));
	}

	@Override
	public byte[] bodyToBytes() throws Exception {
		return this.inviteeName.getBytes();
	}

	@Override
	public String getName() {
		return NAME;
	}

}

package tetrismina.command.concrete;

import tetrismina.command.AbstractTetrisCommand;

public class AcceptInvitationCommand extends AbstractTetrisCommand {
	public static final String NAME = "AcceptInvitation";

	private String inviterName = "";

	public String getInviterName() {
		return inviterName;
	}

	public void setInviterName(String inviterName) {
		this.inviterName = inviterName;
	}

	@Override
	public void bodyFromBytes(byte[] bytes) throws Exception {
		setInviterName(new String(bytes));

	}

	@Override
	public byte[] bodyToBytes() throws Exception {
		return inviterName.getBytes();
	}

	@Override
	public String getName() {
		return NAME;
	}

}

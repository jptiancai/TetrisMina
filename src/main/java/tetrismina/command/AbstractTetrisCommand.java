package tetrismina.command;

import org.apache.commons.lang.StringUtils;

import tetrismina.Constants;

public abstract class AbstractTetrisCommand implements TetrisCommand {
	public abstract String getName();

	public abstract byte[] bodyToBytes() throws Exception;

	public abstract void bodyFromBytes(byte[] bytes) throws Exception;

	public byte[] toBytes() throws Exception {
		byte[] body = bodyToBytes();
		int commandNameLength = Constants.COMMAND_NAME_LENGTH;
		int len = commandNameLength + body.length;
		byte[] bytes = new byte[len];
		String name = StringUtils.rightPad(getName(), commandNameLength,
				Constants.COMMAND_NAME_PAD_CHAR);
		name = name.substring(0, commandNameLength);
		System.arraycopy(name.getBytes(), 0, bytes, 0, commandNameLength);
		System.arraycopy(body, 0, bytes, commandNameLength, body.length);
		return bytes;
	}

	public String toString() {
		return "Command : " + getName();
	}
}

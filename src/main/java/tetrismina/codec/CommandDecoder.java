package tetrismina.codec;

import org.apache.commons.lang.StringUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tetrismina.Constants;
import tetrismina.command.AbstractTetrisCommand;
import tetrismina.command.TetrisCommandFactory;

public class CommandDecoder extends CumulativeProtocolDecoder {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CommandDecoder.class);

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		if (in.prefixedDataAvailable(4, Constants.MAX_COMMAND_LENGTH)) {
			int length = in.getInt();
			byte[] bytes = new byte[length];
			in.get(bytes);
			int commandNameLength = Constants.COMMAND_NAME_LENGTH;
			byte[] cmdNameBytes = new byte[commandNameLength];
			System.arraycopy(bytes, 0, cmdNameBytes, 0, commandNameLength);
			String cmdName = StringUtils.trim(new String(cmdNameBytes));
			AbstractTetrisCommand command = TetrisCommandFactory
					.newCommand(cmdName);
			if (command != null) {
				byte[] cmdBodyBytes = new byte[length - commandNameLength];
				System.arraycopy(bytes, commandNameLength, cmdBodyBytes, 0,
						length - commandNameLength);
				command.bodyFromBytes(cmdBodyBytes);
				out.write(command);
				LOGGER.info("Command \"{}\" received.", cmdName);
			} else {
				LOGGER.info("Unknown \"{}\" command received, skip it.",
						cmdName);
			}
			return true;
		} else {
			return false;
		}
	}

}

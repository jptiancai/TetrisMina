package tetrismina.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import tetrismina.command.AbstractTetrisCommand;

public class CommandEncoder extends ProtocolEncoderAdapter {

	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		AbstractTetrisCommand command = (AbstractTetrisCommand) message;
		byte[] bytes = command.toBytes();
		IoBuffer buf = IoBuffer.allocate(bytes.length, false);

		buf.setAutoExpand(true);
		buf.putInt(bytes.length);
		buf.put(bytes);

		buf.flip();
		out.write(buf);
	}
}

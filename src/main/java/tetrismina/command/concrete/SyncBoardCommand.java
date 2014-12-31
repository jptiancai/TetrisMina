package tetrismina.command.concrete;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import tetrismina.command.AbstractTetrisCommand;

public class SyncBoardCommand extends AbstractTetrisCommand {

	public static final String NAME = "SyncBoard";

	private int seq = 0;

	private int[][] data;

	public String getName() {
		return NAME;
	}

	public SyncBoardCommand() {

	}

	public SyncBoardCommand(int seq, int[][] data) {
		this.seq = seq;
		this.data = data;
	}

	public int getSequence() {
		return seq;
	}

	public int[][] getBoardData() {
		return data;
	}

	public void bodyFromBytes(byte[] bytes) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bais);
		seq = ois.readInt();
		data = (int[][]) ois.readObject();
	}

	public byte[] bodyToBytes() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeInt(seq);
		oos.writeObject(data);
		return baos.toByteArray();
	}

}

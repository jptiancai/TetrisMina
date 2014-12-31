package tetrismina.command.concrete;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import tetrismina.command.AbstractTetrisCommand;
import tetrismina.common.UserStatus;

public class RefreshPlayersListCommand extends AbstractTetrisCommand {

	public static final String NAME = "RefreshPlayer";

	private List<String[]> players = new ArrayList<String[]>();

	public void addPlayer(String nickname, UserStatus status) {
		if (nickname != null && status != null) {
			players.add(new String[] { nickname, status.getStatus() });
		}
	}

	@Override
	public void bodyFromBytes(byte[] bytes) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bais);
		int num = ois.readInt();
		for (int i = 0; i < num; i++) {
			String nickname = (String) ois.readObject();
			String status = (String) ois.readObject();
			addPlayer(nickname, UserStatus.get(status));
		}
	}

	@Override
	public byte[] bodyToBytes() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeInt(players.size());
		for (int i = 0, n = players.size(); i < n; i++) {
			String[] player = players.get(i);
			oos.writeObject(player[0]);
			oos.writeObject(player[1]);
		}
		return baos.toByteArray();
	}

	@Override
	public String getName() {
		return NAME;
	}

	public List<String[]> getPlayers() {
		return players;
	}

}

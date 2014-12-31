package tetrismina.client.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerDataSource {
	private Set<Player> players = new HashSet<Player>();

	public PlayerDataSource() {

	}

	public void addPlayer(Player player) {
		if (player != null) {
			players.add(player);
		}
	}

	public Player[] getAllPlayers() {
		return players.toArray(new Player[0]);
	}

	public List<Player> getPlayersExclude(String nickName) {
		List<Player> list = new ArrayList<Player>();
		for (Player p : players) {
			if (!p.getNickname().equals(nickName)) {
				list.add(p);
			}
		}
		return list;
	}

	public static PlayerDataSource valueOf(List<String[]> list) {
		PlayerDataSource pds = new PlayerDataSource();
		for (String[] p : list) {
			pds.addPlayer(new Player(p[0], p[1]));
		}
		return pds;
	}
}

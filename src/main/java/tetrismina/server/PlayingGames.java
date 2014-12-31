package tetrismina.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class PlayingGames {
	private List<String[]> games = new ArrayList<String[]>();

	public void newGameStarted(String player1, String player2) {
		games.add(new String[] { player1, player2 });
	}

	public String findOpponent(String player) {
		if (StringUtils.isEmpty(player)) {
			return null;
		}
		for (String[] players : games) {
			if (player.equals(players[0])) {
				return players[1];
			} else if (player.equals(players[1])) {
				return players[0];
			}
		}
		return null;
	}
	
	public void endGame(String player) {
		if (StringUtils.isEmpty(player)) {
			return;
		}
		int index = -1;
		for (int i = 0, n = games.size(); i < n; i++) {
			String[] players = games.get(i);
			if (player.equals(players[0]) || player.equals(players[1])) {
				index = i;
				break;
			}
		}
		if (index != -1) {
			games.remove(index);
		}
	}
}

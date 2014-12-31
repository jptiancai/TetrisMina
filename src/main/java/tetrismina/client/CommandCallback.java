package tetrismina.client;

import tetrismina.client.domain.PlayerDataSource;

public interface CommandCallback {
	void refreshPlayersList(PlayerDataSource players);

	void receiveInvitation(String inviterName);

	void startGame(String opponent);

	void syncBoard(int seq, int[][] data);
	
	void winGame();
}

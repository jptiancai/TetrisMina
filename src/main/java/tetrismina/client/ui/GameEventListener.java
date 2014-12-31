package tetrismina.client.ui;

public interface GameEventListener {
	void rowsCleared(int rowNum);

	void gameOver();

	void notifyChanged(int[][] board);
}

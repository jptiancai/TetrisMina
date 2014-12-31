package tetrismina.client.ui.game;

public interface BoardSynchronizationListener {
	void synchronize(int seq, int[][] data);
}

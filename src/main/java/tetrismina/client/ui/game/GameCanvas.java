package tetrismina.client.ui.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import tetrismina.client.ui.GameEventListener;

public class GameCanvas extends Canvas implements BoardSynchronizationListener {

	private static final long serialVersionUID = 1L;

	private static final int GAME_LOOP_INTERVAL = 100;

	private BufferStrategy strategy;

	private Timer loopTimer;

	private GameController gameController;

	private JFrame container = null;

	private int windowWidth = 600;

	private int windowHeight = 800;

	private int currentSeq = -1;

	private boolean sync = false;

	private int[][] boardData = null;

	private boolean stopRequested = false;

	private GameEventListener gameEventListener;

	public GameCanvas(GameEventListener gameEventListener) {
		this.gameEventListener = gameEventListener;
		createWindow();
		init();
	}

	private void createWindow() {
		container = new JFrame("");
		container.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		container.addWindowListener(new WindowClose());

		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(windowWidth, windowHeight));
		panel.setLayout(null);

		setBounds(0, 0, windowWidth, windowHeight);
		panel.add(this);

		setIgnoreRepaint(true);

		container.pack();
		container.setResizable(false);
		container.setVisible(true);
	}

	private void init() {
		setIgnoreRepaint(true);

		requestFocus();

		createBufferStrategy(2);
		strategy = getBufferStrategy();

		gameController = new GameController(gameEventListener);

		addKeyListener(gameController);

		loopTimer = new Timer(GAME_LOOP_INTERVAL, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				gameLoop();
			}
		});

		loopTimer.start();
	}

	public void setWindowTitle(String title) {
		container.setTitle(title);
	}

	public void stopGame() {
		stopRequested = true;
		stopGameLoop();
		closeWindow();
	}

	private void stopGameLoop() {
		loopTimer.stop();
	}

	private void closeWindow() {
		container.setVisible(false);
		container.dispose();
	}

	private void gameLoop() {
		if (stopRequested) {
			return;
		}

		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, windowWidth, windowHeight);

		gameController.inLoop();
		gameController.draw(g);
		if (sync) {
			gameController.drawOtherBoard(g, boardData);
		}

		g.dispose();
		try {
			strategy.show();
		} catch (IllegalStateException e) {
			//ignore
		}
	}

	public void synchronize(int seq, int[][] data) {
		if (currentSeq >= seq) {
			sync = false;
			return;
		}
		sync = true;
		boardData = data;
		currentSeq = seq;
	}

	private class WindowClose extends WindowAdapter {

		public void windowClosing(WindowEvent e) {
			stopGame();
		}

	}
}

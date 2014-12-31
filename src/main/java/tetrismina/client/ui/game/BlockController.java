package tetrismina.client.ui.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.Timer;

public class BlockController {
	private Block block;

	private GameController gameController;

	private static final Color[] FILL_COLORS = new Color[] { Color.RED,
			Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.GREEN, Color.BLUE };

	private Color fillColor = Color.BLACK;

	private Random random = new Random();

	private int posX = 0;

	private int posY = 0;

	private int dx = 0;

	private int dy = 0;

	private int downSpeed = 1;

	private Timer timer;

	private boolean autoMoved = false;

	public BlockController(Block block, GameController gameController) {
		this(gameController);
		this.block = block;
	}

	public BlockController(GameController gameController) {
		this.gameController = gameController;
		fillColor = generateFillColor();
	}

	public void setManagedBlock(Block block) {
		this.block = block;
		resetBlock();
		startAutoMovingDown();
	}

	private void resetBlock() {
		posY = 0;
		posX = gameController.getBlockStartXPosition();
		fillColor = generateFillColor();
	}

	private void setupTimer() {
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dy = 1;
				autoMoved = true;
			}
		};
		timer = new Timer(700 * downSpeed, taskPerformer);
		timer.start();
	}

	private void killTimer() {
		if (timer != null) {
			timer.stop();
			timer = null;
		}
	}

	public Block getBlock() {
		return block;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public void startAutoMovingDown() {
		setupTimer();
	}

	public void move() {
		if (dx < 0 && !gameController.canMoveLeft(block, posX, posY)) {
			return;
		}

		if (dx > 0 && !gameController.canMoveRight(block, posX, posY)) {
			return;
		}

		if (dy > 0 && !gameController.canMoveDown(block, posX, posY)) {
			return;
		}

		posX += dx;
		posY += dy;

		if (autoMoved) {
			dy = 0;
			autoMoved = false;
		}

		if (!canMoveDown()) {
			killTimer();
			gameController.notifyBlockEnd();
		}

	}

	public void moveLeft() {
		if (gameController.canMoveLeft(block, posX, posY)) {
			dx = -1;
		} else {
			dx = 0;
		}
	}

	public void moveRight() {
		if (gameController.canMoveRight(block, posX, posY)) {
			dx = 1;
		} else {
			dx = 0;
		}
	}

	public boolean moveDown() {
		if (gameController.canMoveDown(block, posX, posY)) {
			dy = 1;
			return true;
		} else {
			dy = 0;
			gameController.notifyBlockEnd();
			return false;
		}
	}

	private boolean canMoveDown() {
		return gameController.canMoveDown(block, posX, posY);
	}

	private boolean canTransform() {
		return gameController.canTransform(block, posX, posY);
	}

	private void transform() {
		if (canTransform()) {
			block.transform();
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
			dx = -1;
			break;
		case KeyEvent.VK_RIGHT:
			dx = 1;
			break;
		case KeyEvent.VK_DOWN:
			dy = 1;
			break;
		case KeyEvent.VK_UP:
			transform();
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
			dx = 0;
			break;
		case KeyEvent.VK_RIGHT:
			dx = 0;
			break;
		case KeyEvent.VK_DOWN:
			dy = 0;
			break;
		}
	}

	private Color generateFillColor() {
		int index = random.nextInt(FILL_COLORS.length);
		return FILL_COLORS[index];
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void draw(Graphics2D g) {
		int blockSize = Constants.BLOCK_SIZE;
		g.translate(Constants.BOARD_X, Constants.BOARD_Y);
		byte[][] position = block.getPosition();
		for (int i = 0; i < block.getHeight(); i++) {
			for (int j = 0; j < block.getWidth(); j++) {
				if (position[i][j] == 1) {
					g.setColor(fillColor);
					g.fillRect((posX + j) * blockSize, (posY + i) * blockSize,
							blockSize, blockSize);
					g.draw3DRect((posX + j) * blockSize,
							(posY + i) * blockSize, blockSize - 1,
							blockSize - 1, true);
				}
			}
		}
		g.translate(-Constants.BOARD_X, -Constants.BOARD_Y);
	}
}

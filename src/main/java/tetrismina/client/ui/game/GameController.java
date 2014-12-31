package tetrismina.client.ui.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import tetrismina.client.ui.GameEventListener;

public class GameController implements KeyListener {
	private Board board;

	private Block currentBlock = null;

	private Block nextBlock = null;

	private boolean gameRunning = false;

	private BlockController blockController;

	private static final int BOARD_WIDTH = 10;

	private static final int BOARD_HEIGHT = 15;

	private GameEventListener gameEventListener;

	public GameController(GameEventListener gameEventListener) {
		this.gameEventListener = gameEventListener;
		init();
	}

	private void init() {
		board = new Board(BOARD_WIDTH, BOARD_HEIGHT);
		blockController = new BlockController(this);
		currentBlock = BlockFactory.getNextBlock();
		nextBlock = BlockFactory.getNextBlock();
		blockController.setManagedBlock(currentBlock);
		gameRunning = true;
	}

	public void inLoop() {
		if (gameRunning) {
			blockController.move();
		}
	}

	public void draw(Graphics2D g) {
		drawBoard(g);
		blockController.draw(g);
		drawNextBlock(g);
	}

	private void drawBoard(Graphics2D g) {
		int blockSize = Constants.BLOCK_SIZE;
		g.setColor(Color.BLACK);
		g.translate(Constants.BOARD_X, Constants.BOARD_Y);
		g.drawRect(-1, -1, board.getWidth() * blockSize + 1, board.getHeight()
				* blockSize + 1);

		int[][] blocks = board.getBlocks();
		for (int i = 0, n = board.getHeight(); i < n; i++) {
			for (int j = 0, m = board.getWidth(); j < m; j++) {
				Color fillColor = blocks[i][j] == 0 ? Color.GRAY : new Color(
						blocks[i][j]);
				g.setColor(fillColor);
				g.fillRect(j * blockSize, i * blockSize, blockSize, blockSize);
				if (blocks[i][j] != 0) {
					g.draw3DRect(j * blockSize, i * blockSize, blockSize - 1,
							blockSize - 1, true);
				}
			}
		}
		g.translate(-Constants.BOARD_X, -Constants.BOARD_Y);
	}

	public void drawOtherBoard(Graphics2D g, int[][] boardData) {
		int blockSize = Constants.OTHER_BLOCK_SIZE;
		g.setColor(Color.BLACK);
		g.translate(Constants.OTHER_BOARD_X, Constants.OTHER_BOARD_Y);
		g.drawRect(-1, -1, board.getWidth() * blockSize + 1, board.getHeight()
				* blockSize + 1);

		for (int i = 0, n = boardData.length; i < n; i++) {
			for (int j = 0, m = boardData[0].length; j < m; j++) {
				Color fillColor = boardData[i][j] == 0 ? Color.GRAY
						: new Color(boardData[i][j]);
				g.setColor(fillColor);
				g.fillRect(j * blockSize, i * blockSize, blockSize, blockSize);
				if (boardData[i][j] != 0) {
					g.draw3DRect(j * blockSize, i * blockSize, blockSize - 1,
							blockSize - 1, true);
				}
			}
		}

		g.translate(-Constants.OTHER_BOARD_X, -Constants.OTHER_BOARD_Y);
	}

	private void drawNextBlock(Graphics2D g) {
		int blockSize = Constants.BLOCK_SIZE;
		g.translate(Constants.NEXT_BLOCK_X, Constants.NEXT_BLOCK_Y);
		int posX = 0;
		int posY = 0;
		byte[][] position = nextBlock.getPosition();
		for (int i = 0; i < nextBlock.getHeight(); i++) {
			for (int j = 0; j < nextBlock.getWidth(); j++) {
				if (position[i][j] == 1) {
					g.setColor(Color.DARK_GRAY);
					g.fillRect((posX + j) * blockSize, (posY + i) * blockSize,
							blockSize, blockSize);
					g.draw3DRect((posX + j) * blockSize,
							(posY + i) * blockSize, blockSize - 1,
							blockSize - 1, true);
				}
			}
		}
		g.translate(-Constants.NEXT_BLOCK_X, -Constants.NEXT_BLOCK_Y);
	}

	public boolean canMoveLeft(Block block, int posX, int posY) {
		return this.board.canMoveLeft(block, posX, posY);
	}

	public boolean canMoveRight(Block block, int posX, int posY) {
		return this.board.canMoveRight(block, posX, posY);
	}

	public boolean canMoveDown(Block block, int posX, int posY) {
		return this.board.canMoveDown(block, posX, posY);
	}

	public boolean canTransform(Block block, int posX, int posY) {
		return this.board.canTransform(block, posX, posY);
	}

	public int getBlockStartXPosition() {
		return 5;
	}

	public void notifyBlockEnd() {
		board.addBlock(currentBlock, blockController.getPosX(), blockController
				.getPosY(), blockController.getFillColor());
		int rowNum = board.clearRows(currentBlock, blockController.getPosY());
		if (rowNum > 0) {
			gameEventListener.rowsCleared(rowNum);
		}
		if (board.isGameOver()) {
			gameRunning = false;
			gameEventListener.gameOver();
		} else {
			currentBlock = nextBlock;
			nextBlock = BlockFactory.getNextBlock();
			blockController.setManagedBlock(currentBlock);
			gameEventListener.notifyChanged(board.getBlocks());
		}
	}

	public void keyPressed(KeyEvent e) {
		blockController.keyPressed(e);

	}

	public void keyReleased(KeyEvent e) {
		blockController.keyReleased(e);

	}

	public void keyTyped(KeyEvent e) {

	}

}

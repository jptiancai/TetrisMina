package tetrismina.client.ui.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
	private int width = 0;
	private int height = 0;

	private int[][] blocks;

	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		blocks = new int[height][width];
		for (int i = 0; i < height; i++) {
			blocks[i] = new int[width];
			Arrays.fill(blocks[i], (byte) 0);
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isGameOver() {
		int[] firstRow = blocks[0];
		for (int i = 0; i < width; i++) {
			if (firstRow[i] != 0) {
				return true;
			}
		}
		return false;
	}

	public int clearRows(Block block, int endY) {
		List<Integer> rowsToClear = new ArrayList<Integer>();
		for (int i = 0; i < block.getHeight(); i++) {
			int rowNum = endY + i;
			int[] row = blocks[rowNum];
			int cols = 0;
			for (int j = 0; j < width; j++) {
				if (row[j] != 0) {
					cols++;
				}
			}
			if (cols == width) {
				rowsToClear.add(rowNum);
			}
		}
		if (rowsToClear.size() > 0) {
			int[][] newBlocks = new int[height][width];
			int count = height - 1;
			for (int i = height - 1; i >= 0; i--) {
				if (!rowsToClear.contains(i)) {
					newBlocks[count] = new int[width];
					System.arraycopy(blocks[i], 0, newBlocks[count], 0, width);
					count--;
				}
			}
			while (count >= 0) {
				newBlocks[count] = new int[width];
				Arrays.fill(newBlocks[count], 0);
				count--;
			}
			blocks = newBlocks;
		}
		return rowsToClear.size();
	}

	public boolean canMoveLeft(Block block, int posX, int posY) {
		if (posX <= 0) {
			return false;
		}
		byte[][] position = block.getPosition();
		for (int i = 0; i < block.getHeight(); i++) {
			for (int j = 0; j < block.getWidth(); j++) {
				if (position[i][j] == 1 && blocks[posY + i][posX - 1 + j] != 0) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean canMoveRight(Block block, int posX, int posY) {
		if (posX < 0 || posX >= width - block.getWidth()) {
			return false;
		}
		byte[][] position = block.getPosition();
		for (int i = 0; i < block.getHeight(); i++) {
			for (int j = 0; j < block.getWidth(); j++) {
				if (position[i][j] == 1 && blocks[posY + i][posX + 1 + j] != 0) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean canMoveDown(Block block, int posX, int posY) {
		if (posY >= height - block.getHeight()) {
			return false;
		}
		byte[][] position = block.getPosition();
		for (int i = 0; i < block.getHeight(); i++) {
			for (int j = 0; j < block.getWidth(); j++) {
				if (position[i][j] == 1 && blocks[posY + 1 + i][posX + j] != 0) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean canTransform(Block block, int posX, int posY) {
		int tHeight = block.getTransformedHeight();
		int tWidth = block.getTransformedWidth();
		if (posY + tHeight > height || posX + tWidth > width) {
			return false;
		}
		byte[][] position = block.getTransformedPosition();
		for (int i = 0; i < tHeight; i++) {
			for (int j = 0; j < tWidth; j++) {
				if (position[i][j] == 1 && blocks[posY + i][posX + j] != 0) {
					return false;
				}
			}
		}
		return true;
	}

	public void addBlock(Block block, int posX, int posY, Color color) {
		byte[][] position = block.getPosition();
		for (int i = 0; i < block.getHeight(); i++) {
			for (int j = 0; j < block.getWidth(); j++) {
				if (position[i][j] == 1) {
					blocks[posY + i][posX + j] = color.getRGB();
				}
			}
		}
	}

	public int[][] getBlocks() {
		return blocks;
	}
}

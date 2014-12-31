package tetrismina.client.ui.game;

import java.util.Random;

public class BlockFactory {
	private BlockFactory() {

	}

	public static final Block getNextBlock() {
		Block[] blocks = Block.values();
		int total = blocks.length;
		int index = new Random().nextInt(total);
		return blocks[index];
	}
}

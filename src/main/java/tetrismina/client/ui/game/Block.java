package tetrismina.client.ui.game;

public enum Block {
	T(3, 2, new byte[][] { { 1, 1, 1 }, { 0, 1, 0 } }), L(2, 3, new byte[][] {
			{ 1, 0 }, { 1, 0 }, { 1, 1 } }), I(1, 4, new byte[][] { { 1 },
			{ 1 }, { 1 }, { 1 } }), TIAN(2, 2, new byte[][] { { 1, 1 },
			{ 1, 1 } }), AL(2, 3, new byte[][] { { 0, 1 }, { 0, 1 }, { 1, 1 } }), YU(
			2, 3, new byte[][] { { 1, 0 }, { 1, 1 }, { 0, 1 } }), AYU(2, 3,
			new byte[][] { { 0, 1 }, { 1, 1 }, { 1, 0 } });

	private int width = 0;
	private int height = 0;
	private byte[][] position;

	Block(int width, int height, byte[][] position) {
		this.width = width;
		this.height = height;
		this.position = position;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public byte[][] getPosition() {
		return position;
	}

	public byte[][] getTransformedPosition() {
		byte[][] newPos = new byte[width][height];
		for (int i = height - 1; i >= 0; i--) {
			for (int j = 0; j < width; j++) {
				newPos[j][height - 1 - i] = position[i][j];
			}
		}
		return newPos;
	}

	public int getTransformedHeight() {
		return width;
	}

	public int getTransformedWidth() {
		return height;
	}

	public void transform() {
		byte[][] newPos = getTransformedPosition();
		int tmp = height;
		height = width;
		width = tmp;
		position = newPos;
	}

}

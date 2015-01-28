package com.jptiancai.TetrisMina;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tetrismina.client.ui.game.Block;
import tetrismina.client.ui.game.BlockFactory;
import tetrismina.client.ui.game.Board;

public class BoardTest {

	private static final int BOARD_WIDTH = 10;
	private static final int BOARD_HEIGHT = 15;
	private Board board;
	private Block block;
	
	@Before
	public void setUp() {
		board=new Board(BOARD_WIDTH, BOARD_HEIGHT);
	}
	
	
	@Test
	public void testIsGameOver() {
		block=Block.L;
		for (int i = 0; i < 3; i+=3) {
			board.addBlock(block,0,i,Color.BLUE);
		}
		Assert.assertTrue(board.isGameOver());
		
	}
	
	
	@Test
	public void testClearRows() {
		block=Block.TIAN;
		//满足5个TIAN
		for (int i = 0; i < BOARD_WIDTH; i+=2) {
			board.addBlock(block,i,0,Color.BLUE);
		}
		Assert.assertEquals(2, board.clearRows(block, 0));
	}
	
	
	
	@Test
	public void testCanMoveLeft() {
		block=Block.TIAN;
		//left boundary
		Assert.assertEquals(false,board.canMoveLeft(block, 0, 0));
		//no left boundary
		Assert.assertTrue(board.canMoveLeft(block, 4, 0));
	}
	
	
	@Test
	public void testCanMoveRight() {
		block=Block.TIAN;
		//right boundary
		Assert.assertEquals(false,board.canMoveRight(block, 8, 0));
		//no right boundary
		Assert.assertTrue(board.canMoveLeft(block, 4, 0));
	}
	
	
	@Test
	public void testCanMoveDown() {
		block=Block.TIAN;
		//方块的起始点只满足于自身的高
		Assert.assertEquals(false,board.canMoveDown(block, 0, BOARD_HEIGHT-2));
		//方块的起始点在在最底部
		Assert.assertTrue(board.canMoveDown(block, 0,0));
	}
	
	
	@Test
	public void testCanTransform() {
		block=Block.I;
		Assert.assertEquals(false,board.canTransform(block, 0, BOARD_HEIGHT));
		Assert.assertTrue(board.canTransform(block, 0, 0));
		
	}
}

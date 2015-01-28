package com.jptiancai.TetrisMina;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tetrismina.client.ui.game.Block;

/**
 * {@link tetrismina.client.ui.game.Block}
 */
public class BlockTest {

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}
	
	@Test
	public void testTransform() {
//		Block block=Block.T;
		Block block=Block.L;
//		Block block=Block.I;
//		Block block=Block.TIAN;
//		Block block=Block.AL;
//		Block block=Block.YU;
//		Block block=Block.AYU;
		//输出值
		for (int i = 0; i < block.getPosition().length; i++) {
			for (int j = 0; j < block.getPosition()[i].length; j++) {
				System.out.print(block.getPosition()[i][j]);
				System.out.print(' ');
			}
			System.out.println();
				}
		System.out.println("--------------");
		byte[][] actualPos=block.getTransformedPosition();
		//输出值
		for (int i = 0; i < actualPos.length; i++) {
			for (int j = 0; j < actualPos[i].length; j++) {
				System.out.print(actualPos[i][j]);
				System.out.print(' ');
			}
			System.out.println();
		}
		System.out.println("--------------");
		//一次顺时针旋转
		block.transform();
		byte[][] actualPos1=block.getTransformedPosition();
		//输出值
		for (int i = 0; i < actualPos1.length; i++) {
			for (int j = 0; j < actualPos1[i].length; j++) {
				System.out.print(actualPos1[i][j]);
				System.out.print(' ');
			}
			System.out.println();
		}
		System.out.println("--------------");
		//一次顺时针旋转
		block.transform();
		byte[][] actualPos2=block.getTransformedPosition();
		//输出值
		for (int i = 0; i < actualPos2.length; i++) {
			for (int j = 0; j < actualPos2[i].length; j++) {
				System.out.print(actualPos2[i][j]);
				System.out.print(' ');
			}
			System.out.println();
		}
		
	}
}

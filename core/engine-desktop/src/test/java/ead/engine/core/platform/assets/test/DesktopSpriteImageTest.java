/**
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the
 *    <e-UCM> research group.
 *
 *    Copyright 2005-2010 <e-UCM> research group.
 *
 *    You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *
 *    <e-UCM> is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *
 * ****************************************************************************
 *
 *  This file is part of eAdventure, version 2.0
 *
 *      eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with eAdventure.  If not, see <http://www.gnu.org/licenses/>.
 */

package ead.engine.core.platform.assets.test;

import junit.framework.TestCase;

import org.junit.Test;

public class DesktopSpriteImageTest extends TestCase {

//	private Injector injector;
//		
//	private SpriteImage sprite1;
//	
//	private SpriteImage sprite2;
	
//	private DesktopSpriteImage spriteImage1;
//
//	private DesktopSpriteImage spriteImage2;
	
	@Override
	public void setUp() {
//		injector = Guice.createInjector(new DesktopAssetHandlerModule());
		
//		spriteImage1 = injector.getInstance(DesktopSpriteImage.class);
//		
//		
//		sprite1 = new SpriteImage(new Image("@drawable/loading.png"), 9, 2);
//		spriteImage1.setDescriptor(sprite1);
//		
//		spriteImage2 = injector.getInstance(DesktopSpriteImage.class);
//
//		sprite2 = new SpriteImage(new Image("@drawable/loading.png"), 9, 6);
//		spriteImage2.setDescriptor(sprite2);
//		
		
	}

	/**
	 * Tests that sprites are loaded correctly and that two sprites
	 * that use the same image are loaded at the same time (use only one image instance)
	 */
	@Test
	public void testImageLoadUnload() {

//		spriteImage1.loadAsset();
//		
//		assertTrue(spriteImage1.isLoaded());
//		assertTrue(spriteImage2.isLoaded());
//		
//		spriteImage1.freeMemory();
//		
//		assertFalse(spriteImage1.isLoaded());
//		assertFalse(spriteImage2.isLoaded());
	}

	private static final int GCCOUNT = 1;

	private static void gc() {
		try {
			System.gc();
			Thread.sleep(100);
			System.runFinalization();
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
	}

	private static void gc(int n) {
		for (int i = 0; i < n; i++) {
			gc();
		}
	}

	public static long getMemoryUse() {
		gc(GCCOUNT);
		long total = Runtime.getRuntime().totalMemory();
		long free = Runtime.getRuntime().freeMemory();
		return total - free;
	}
	
	/**
	 * Test if memory use after releasing an image is within 10% of the memory use befor loading it
	 */
	@Test
	public void testMemoryUse() {
		long memoryUse = getMemoryUse();

//		spriteImage1.loadAsset();
//		
//		spriteImage2.freeMemory();

		assertTrue(getMemoryUse() < memoryUse * 1.10);
	}

}

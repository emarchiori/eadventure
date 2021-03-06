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

package ead.engine.core.game;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import ead.common.util.EAdURI;
import ead.engine.core.platform.GUI;
import ead.engine.core.platform.assets.AssetHandler;
import ead.engine.core.tracking.GameTracker;

public class GameControllerImpl implements GameController {

	/**
	 * The {@link Game}
	 */
	private Game game;

	/**
	 * The game's loop {@link GameLoop}
	 */
	private GameLoop gameLoop;

	/**
	 * The games {@link GUI}
	 */
	private GUI gui;

	/**
	 * Indicates that the game loop is executed in a different thread
	 */
	private boolean threaded;

	private GameTracker gameTracker;

	private AssetHandler assetHandler;

	@Inject
	public GameControllerImpl(Game game, GameLoop gameLoop, GUI gui,
			@Named("threaded") Boolean threaded, AssetHandler assetHandler,
			GameTracker gameTracker) {
		this.game = game;
		this.gameLoop = gameLoop;
		this.gui = gui;
		this.threaded = threaded;
		this.assetHandler = assetHandler;
		this.gameTracker = gameTracker;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.core.GameController#start()
	 */
	@Override
	public void start(EAdURI uri) {
		assetHandler.setResourcesLocation(uri);
		gui.initialize();
		gameLoop.setGame(game);
		game.loadGame();
		gameLoop.runLoop(threaded);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.core.GameController#stop()
	 */
	@Override
	public void stop() {
		gui.finish();
		gameTracker.stop();
		gameLoop.stop();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.core.GameController#pause()
	 */
	@Override
	public void pause() {
		gameLoop.pause();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.core.GameController#resume()
	 */
	@Override
	public void resume() {
		gameLoop.resume();
	}

}

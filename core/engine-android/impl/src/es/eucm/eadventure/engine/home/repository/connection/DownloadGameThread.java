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

package es.eucm.eadventure.engine.home.repository.connection;

import android.content.Context;
import android.os.Handler;
import es.eucm.eadventure.engine.home.repository.database.GameInfo;
import es.eucm.eadventure.engine.home.repository.handler.ProgressNotifier;
import es.eucm.eadventure.engine.home.repository.handler.RepoResourceHandler;
import es.eucm.eadventure.engine.home.utils.directory.Paths;

/**
 * A thread in charge of downloading games from their url
 * 
 * @author Roberto Tornero
 */
public class DownloadGameThread extends Thread {

	/**
	 * The information of the game where its url is located
	 */
	private GameInfo game;
	/**
	 * A notifier to show the progress of the download process 
	 */
	private ProgressNotifier pn;

	/**
	 * Constructor
	 */
	public DownloadGameThread(Context c, Handler handler, GameInfo game) {
		
		this.game = game;
		this.pn = new ProgressNotifier(handler);		
	}

	/**
	 * Starts the download process of the game from its url
	 */
	@Override
	public void run() {

		RepoResourceHandler.downloadFileAndUnzip(game.getEadUrl(), Paths.eaddirectory.GAMES_PATH, 
				game.getFileName(), pn);
		pn.notifityGameInstalled();		
	}

}

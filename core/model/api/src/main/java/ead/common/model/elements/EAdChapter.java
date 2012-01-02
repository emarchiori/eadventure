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

package ead.common.model.elements;

import ead.common.interfaces.features.Evented;
import ead.common.interfaces.features.Resourced;
import ead.common.interfaces.features.Variabled;
import ead.common.model.EAdElement;
import ead.common.model.elements.extra.EAdList;
import ead.common.model.elements.scene.EAdScene;
import ead.common.model.elements.scene.EAdSceneElementDef;
import ead.common.params.text.EAdString;
import ead.common.resources.annotation.Asset;
import ead.common.resources.annotation.Bundled;
import ead.common.resources.assets.multimedia.Sound;

public interface EAdChapter extends EAdElement, Resourced, Variabled, Evented {

	@Bundled
	@Asset({ Sound.class })
	final static String music = "music";

	/**
	 * Returns the scenes of the chapter.
	 * 
	 * @return the scenes of the chapter.
	 */
	EAdList<EAdScene> getScenes();

	/**
	 * Returns actors of the chapter.
	 * 
	 * @return the actors of the chapter.
	 */
	EAdList<EAdSceneElementDef> getActors();

	/**
	 * @return The title of the chapter
	 */
	EAdString getTitle();

	/**
	 * @return The description of the chapter
	 */
	EAdString getDescription();

	/**
	 * @return The first screen in the game
	 */
	EAdScene getInitialScene();

}
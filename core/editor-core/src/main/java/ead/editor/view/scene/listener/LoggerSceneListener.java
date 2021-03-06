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

package ead.editor.view.scene.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ead.common.model.elements.scene.EAdSceneElement;
import ead.common.model.elements.variables.VarDef;

public class LoggerSceneListener implements SceneListener {

	private final static Logger logger = LoggerFactory.getLogger("SceneLoader");

	@Override
	public <T> void updateInitialValue(VarDef<T> var, EAdSceneElement element,
			T value) {
		logger.debug(
				"Varirable {} change its value to {} in element {}",
				new String[] { var.toString(), value.toString(),
						element.getId() });

	}

	@Override
	public void updateSelection(List<EAdSceneElement> sceneElements) {
		logger.debug("Selection udpated. {} elements selected",
				sceneElements == null ? 0 : sceneElements.size());

	}
}

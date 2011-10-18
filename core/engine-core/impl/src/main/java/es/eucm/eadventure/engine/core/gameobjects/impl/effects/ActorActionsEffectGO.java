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

package es.eucm.eadventure.engine.core.gameobjects.impl.effects;

import com.google.inject.Inject;

import es.eucm.eadventure.common.model.effects.impl.EAdActorActionsEffect;
import es.eucm.eadventure.common.model.effects.impl.EAdActorActionsEffect.Change;
import es.eucm.eadventure.common.resources.StringHandler;
import es.eucm.eadventure.engine.core.GameState;
import es.eucm.eadventure.engine.core.gameobjects.GameObjectFactory;
import es.eucm.eadventure.engine.core.gameobjects.GameObjectManager;
import es.eucm.eadventure.engine.core.gameobjects.SceneElementGO;
import es.eucm.eadventure.engine.core.gameobjects.huds.ActionsHUD;
import es.eucm.eadventure.engine.core.platform.AssetHandler;
import es.eucm.eadventure.engine.core.platform.GUI;

public class ActorActionsEffectGO extends
		AbstractEffectGO<EAdActorActionsEffect> {

	/**
	 * The current {@link ActionsHUD}
	 */
	private ActionsHUD actionsHUD;

	private GameObjectManager gameObjectManager;

	@Inject
	public ActorActionsEffectGO(AssetHandler assetHandler,
			StringHandler stringHandler, GameObjectFactory gameObjectFactory,
			GUI gui, GameState gameState,
			GameObjectManager gameObjectManager, ActionsHUD actionsHUD) {
		super(assetHandler, stringHandler, gameObjectFactory, gui, gameState);
		this.actionsHUD = actionsHUD;
		this.gameObjectManager = gameObjectManager;
	}

	@Override
	public void initilize() {
		super.initilize();
		if (element.getChange() == Change.SHOW_ACTIONS) {
			SceneElementGO<?> sceneElement = (SceneElementGO<?>) gameObjectFactory
					.get(element.getActionElement());
			if (sceneElement.getValidActions() != null) {
				actionsHUD.setElement(sceneElement);
				gameObjectManager.addHUD(actionsHUD);
			}
		} else {
			gameObjectManager.removeHUD(actionsHUD);
		}
	}

	@Override
	public boolean isVisualEffect() {
		return false;
	}

	@Override
	public boolean isFinished() {
		return true;
	}

}

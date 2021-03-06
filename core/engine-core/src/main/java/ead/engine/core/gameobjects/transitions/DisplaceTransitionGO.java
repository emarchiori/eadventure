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

package ead.engine.core.gameobjects.transitions;

import com.google.inject.Inject;

import ead.common.model.elements.transitions.DisplaceTransition;
import ead.common.model.elements.transitions.enums.DisplaceTransitionType;
import ead.common.model.elements.variables.SystemFields;
import ead.engine.core.game.GameState;
import ead.engine.core.gameobjects.factories.EventGOFactory;
import ead.engine.core.gameobjects.factories.SceneElementGOFactory;
import ead.engine.core.gameobjects.go.transitions.SceneLoader;
import ead.engine.core.input.InputHandler;
import ead.engine.core.platform.GUI;
import ead.engine.core.platform.assets.AssetHandler;
import ead.engine.core.util.EAdInterpolator;
import ead.engine.core.util.EAdTransformation;
import ead.engine.core.util.EAdTransformationImpl;

public class DisplaceTransitionGO extends
		AbstractTransitionGO<DisplaceTransition> {

	private boolean finished;

	private int width;

	private int height;

	private int startTime = -1;

	private int x1, x2, y1, y2;

	private EAdTransformation transformation;

	private int currentTime;

	@Inject
	public DisplaceTransitionGO(AssetHandler assetHandler,
			SceneElementGOFactory gameObjectFactory, GUI gui,
			GameState gameState, EventGOFactory eventFactory,
			SceneLoader sceneLoader, InputHandler inputHandler) {
		super(assetHandler, gameObjectFactory, gui, gameState, eventFactory,
				sceneLoader, inputHandler);
		finished = false;
		width = gameState.getValueMap().getValue(SystemFields.GAME_WIDTH);
		height = gameState.getValueMap().getValue(SystemFields.GAME_HEIGHT);
		transformation = new EAdTransformationImpl();
		currentTime = 0;
	}

	public void doLayout(EAdTransformation t) {
		super.doLayout(t);

		if (this.isLoadedNextScene() && startTime != -1) {
			// transformation.setClip(0, 0, width, height);
			transformation.getMatrix().setIdentity();
			transformation.getMatrix().translate(x2, y2, false);
			gui.addElement(nextSceneGO,
					gui.addTransformation(transformation, t));
			if (!isFinished()) {
				// transformation.setClip(0, 0, width, height);
				transformation.getMatrix().setIdentity();
				transformation.getMatrix().translate(x1, y1, false);
				gui.addElement(previousScene,
						gui.addTransformation(transformation, t));
			}
		}
	}

	public void update() {
		super.update();
		if (isLoadedNextScene()) {
			currentTime += gui.getSkippedMilliseconds();
			if (startTime == -1) {
				startTime = currentTime;
			}

			if (currentTime - startTime >= transition.getTime()) {
				finished = true;
			} else {
				float dispX = getDisp(true, currentTime - startTime);
				float dispY = getDisp(false, currentTime - startTime);

				if (dispX != 0.0f) {
					x1 = ((int) (dispX * -width));
					x2 = ((int) ((1 - dispX) * width));
					if (transition.getForward()) {
						x1 = -x1;
						x2 = (int) (dispX * width) - width;
					}
				}

				if (dispY != 0.0f) {
					y1 = ((int) (dispY * -height));
					y2 = ((int) ((1 - dispY) * height));

					if (transition.getForward()) {
						y1 = -y1;
						y2 = (int) (dispY * height) - height;
					}
				}

			}
		}

	}

	public boolean isFinished() {
		return finished;
	}

	private float getDisp(boolean horizontal, int currentTime) {
		if ((horizontal && transition.getType() == DisplaceTransitionType.HORIZONTAL)
				|| (!horizontal && transition.getType() == DisplaceTransitionType.VERTICAL)) {
			float value = EAdInterpolator.LINEAR.interpolate(currentTime,
					transition.getTime(), 1.0f);
			return value;

		} else
			return 0;

	}
}

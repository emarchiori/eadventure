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

package es.eucm.eadventure.engine.core.gameobjects.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.inject.Inject;

import es.eucm.eadventure.common.model.elements.EAdTimer;
import es.eucm.eadventure.common.model.elements.impl.EAdTimerImpl;
import es.eucm.eadventure.common.params.geom.impl.EAdPositionImpl;
import es.eucm.eadventure.common.resources.StringHandler;
import es.eucm.eadventure.engine.core.GameLoop;
import es.eucm.eadventure.engine.core.GameState;
import es.eucm.eadventure.engine.core.MouseState;
import es.eucm.eadventure.engine.core.ValueMap;
import es.eucm.eadventure.engine.core.gameobjects.GameObject;
import es.eucm.eadventure.engine.core.gameobjects.GameObjectFactory;
import es.eucm.eadventure.engine.core.gameobjects.TimerGO;
import es.eucm.eadventure.engine.core.guiactions.GUIAction;
import es.eucm.eadventure.engine.core.platform.AssetHandler;
import es.eucm.eadventure.engine.core.platform.GUI;
import es.eucm.eadventure.engine.core.platform.RuntimeAsset;
import es.eucm.eadventure.engine.core.util.EAdTransformation;

public class TimerGOImpl extends GameObjectImpl<EAdTimer> implements TimerGO {

	private double passedTime;
	
	private static final Logger logger = Logger.getLogger("TimerGOImpl");

	@Inject
	public TimerGOImpl(AssetHandler assetHandler, StringHandler stringsReader,
			GameObjectFactory gameObjectFactory, GUI gui, GameState gameState,
			ValueMap valueMap) {
		super(assetHandler, stringsReader, gameObjectFactory, gui, gameState, valueMap);
	}

	@Override
	public boolean processAction(GUIAction action) {
		return false;
	}

	@Override
	public GameObject<?> getDraggableElement(MouseState mouseState) {
		return null;
	}

	@Override
	public void doLayout(EAdTransformation transformation) {
	}

	@Override
	public EAdPositionImpl getPosition() {
		return null;
	}

	@Override
	public List<RuntimeAsset<?>> getAssets(List<RuntimeAsset<?>> assetList,
			boolean allAssets) {
		return null;
	}

	@Override
	public void update() {
		super.update();
		if (valueMap.getValue(element, EAdTimerImpl.VAR_ENDED)) {
			logger.log(Level.INFO, "ENDED");
			valueMap.setValue(element, EAdTimerImpl.VAR_ENDED, Boolean.FALSE);
			//TODO trigger finished effects
		}
		if (valueMap.getValue(element, EAdTimerImpl.VAR_STARTED)) {
			if (passedTime == 0)
				logger.log(Level.INFO, "STARTED");
			
			passedTime += GameLoop.SKIP_MILLIS_TICK;
			if (passedTime > element.getTime()) {
				valueMap.setValue(element, EAdTimerImpl.VAR_ENDED, Boolean.TRUE);

				//TODO should not do this if restart
				valueMap.setValue(element, EAdTimerImpl.VAR_STARTED, Boolean.FALSE);
				passedTime = 0;
			}
		}
	}

}

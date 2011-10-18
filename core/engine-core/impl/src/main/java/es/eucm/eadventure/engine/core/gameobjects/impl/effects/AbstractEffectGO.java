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

import java.util.Map.Entry;

import com.google.inject.Inject;

import es.eucm.eadventure.common.model.effects.EAdEffect;
import es.eucm.eadventure.common.model.variables.EAdVarDef;
import es.eucm.eadventure.common.resources.StringHandler;
import es.eucm.eadventure.engine.core.GameState;
import es.eucm.eadventure.engine.core.gameobjects.EffectGO;
import es.eucm.eadventure.engine.core.gameobjects.GameObjectFactory;
import es.eucm.eadventure.engine.core.gameobjects.impl.GameObjectImpl;
import es.eucm.eadventure.engine.core.guiactions.GUIAction;
import es.eucm.eadventure.engine.core.platform.AssetHandler;
import es.eucm.eadventure.engine.core.platform.GUI;

public abstract class AbstractEffectGO<P extends EAdEffect> extends
		GameObjectImpl<P> implements EffectGO<P> {

	private boolean stopped = false;

	private boolean initialized = false;

	protected GUIAction action;
	
	@Inject
	public AbstractEffectGO(AssetHandler assetHandler,
			StringHandler stringsReader, GameObjectFactory gameObjectFactory,
			GUI gui, GameState gameState) {
		super(assetHandler, stringsReader, gameObjectFactory, gui, gameState);
	}

	@Override
	public void initilize() {
		initialized = true;
		for ( Entry<EAdVarDef<?>, Object> e: element.getVars().entrySet()){
			gameState.getValueMap().setValue(e.getKey(), e.getValue(), element);
		}
	}

	public P getEffect() {
		return element;
	}

	@Override
	public boolean isBlocking() {
		return element.isBlocking();
	}

	@Override
	public boolean isOpaque() {
		return element.isOpaque();
	}
	
	@Override
	public boolean isQueueable() {
		return element.isQueueable();
	}

	@Override
	public boolean isStopped() {
		return stopped;
	}

	public void stop() {
		stopped = true;
	}

	public void run() {
		stopped = false;
	}

	public boolean isInitilized() {
		return initialized;
	}

	public void finish() {
		initialized = false;
		stopped = true;
		gameState.getValueMap().remove(element);
		gameObjectFactory.remove(element);
	}

	public void setGUIAction(GUIAction action) {
		this.action = action;
	}
	
	public String toString(){
		return "GO " + element;
	}

}

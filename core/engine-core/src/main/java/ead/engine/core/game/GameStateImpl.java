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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import ead.common.model.EAdElement;
import ead.common.model.elements.EAdEffect;
import ead.common.model.elements.effects.ChangeSceneEf;
import ead.common.model.elements.scene.EAdScene;
import ead.common.model.elements.scene.EAdSceneElement;
import ead.common.model.elements.scenes.BasicScene;
import ead.common.model.elements.scenes.SceneElementDef;
import ead.common.model.elements.variables.EAdVarDef;
import ead.common.model.elements.variables.SystemFields;
import ead.engine.core.evaluators.EvaluatorFactory;
import ead.engine.core.gameobjects.factories.EffectGOFactory;
import ead.engine.core.gameobjects.factories.EventGOFactory;
import ead.engine.core.gameobjects.factories.SceneElementGOFactory;
import ead.engine.core.gameobjects.go.EffectGO;
import ead.engine.core.gameobjects.go.SceneElementGO;
import ead.engine.core.gameobjects.go.SceneGO;
import ead.engine.core.input.InputAction;
import ead.engine.core.plugins.PluginHandler;
import ead.engine.core.tracking.GameTracker;

@Singleton
public class GameStateImpl implements GameState {

	private SceneGO<?> scene;

	private List<EffectGO<?>> effects;

	private Stack<EAdScene> previousSceneStack;

	private ValueMap valueMap;

	private SceneElementGOFactory sceneElementFactory;

	private EffectGOFactory effectFactory;

	/**
	 * Queue for effects added
	 */
	private List<EffectGO<?>> effectsQueue;

	private static Logger logger = LoggerFactory.getLogger("GameState");

	private boolean paused;

	private EAdScene loadingScreen;

	private EvaluatorFactory evaluatorFactory;

	private EventGOFactory eventGOFactory;

	private PluginHandler pluginHandler;

	private GameTracker tracker;

	private GameStateData gameStateData;

	@Inject
	public GameStateImpl(@Named("LoadingScreen") EAdScene loadingScreen,
			SceneElementGOFactory gameObjectFactory,
			EffectGOFactory effectFactory, ValueMap valueMap,
			EvaluatorFactory evaluatorFactory, EventGOFactory eventGOFactory,
			PluginHandler pluginHandler, GameTracker tracker) {
		logger.info("New instance of GameState");
		effects = new ArrayList<EffectGO<?>>();
		effectsQueue = new ArrayList<EffectGO<?>>();
		this.loadingScreen = loadingScreen;
		this.valueMap = valueMap;
		this.sceneElementFactory = gameObjectFactory;
		this.previousSceneStack = new Stack<EAdScene>();
		this.evaluatorFactory = evaluatorFactory;
		this.effectFactory = effectFactory;
		this.pluginHandler = pluginHandler;
		this.eventGOFactory = eventGOFactory;
		this.tracker = tracker;
		// TODO improve
		installPlugins();
	}

	@Override
	public SceneGO<?> getScene() {
		if (scene == null) {
			logger.debug("null scene, Loading screen: "
					+ (loadingScreen != null));
			this.scene = (SceneGO<?>) sceneElementFactory.get(loadingScreen);
			previousSceneStack.push(loadingScreen);
		}
		return scene;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.eucm.eadventure.engine.core.GameState#setScene(es.eucm.eadventure.
	 * engine.core.gameobjects.SceneGO)
	 */
	@Override
	public void setScene(SceneGO<? extends EAdScene> newScene) {
		if (this.scene != null && this.scene.getElement() != null) {
			valueMap.setValue(scene.getElement(), BasicScene.VAR_SCENE_LOADED,
					Boolean.FALSE);
			if (scene.getElement().getReturnable()) {
				previousSceneStack.push(scene.getElement());
			}
		}
		this.scene = newScene;
		if (this.scene != null && this.scene.getElement() != null) {
			valueMap.setValue(scene.getElement(), BasicScene.VAR_SCENE_LOADED,
					Boolean.TRUE);
			for (Entry<EAdVarDef<?>, Object> e : scene.getElement().getVars()
					.entrySet()) {
				valueMap.setValue(scene.getElement(), e.getKey(), e.getValue());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.core.GameState#getEffects()
	 */
	@Override
	public List<EffectGO<?>> getEffects() {
		return effects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.core.GameState#getValueMap()
	 */
	@Override
	public ValueMap getValueMap() {
		return valueMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.eucm.eadventure.engine.core.GameState#addEffect(es.eucm.eadventure
	 * .common.model.effects.EAdEffect)
	 */
	@Override
	public EffectGO<?> addEffect(EAdEffect e, InputAction<?> action,
			EAdSceneElement parent) {
		return addEffect(-1, e, action, parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.core.GameState#addEffect(int,
	 * es.eucm.eadventure.common.model.effects.EAdEffect)
	 */
	@Override
	public EffectGO<?> addEffect(int pos, EAdEffect e, InputAction<?> action,
			EAdSceneElement parent) {
		if (e != null) {
			if (evaluatorFactory.evaluate(e.getCondition())) {
				EffectGO<?> effectGO = effectFactory.get(e);
				if (effectGO == null) {
					logger.warn("No game object for effect {}", e.getClass());
					return null;
				}
				effectGO.setGUIAction(action);
				effectGO.setParent(parent);
				effectGO.initialize();
				if (e.isQueueable()) {
					tracker.track(effectGO);
					synchronized (effectsQueue) {
						pos = pos == -1 ? effectsQueue.size() : pos;
						effectsQueue.add(pos, effectGO);
					}
				} else {
					effectGO.update();
					effectGO.finish();
					tracker.track(effectGO);
				}
				return effectGO;
			} else if (e.isNextEffectsAlways()) {
				for (EAdEffect ne : e.getNextEffects())
					addEffect(ne);
			}
		}
		return null;

	}

	@Override
	public EAdScene getPreviousScene() {
		return previousSceneStack.pop();
	}

	@Override
	public boolean isPaused() {
		return paused;
	}

	@Override
	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	@Override
	public void updateEffectsQueue() {
		synchronized (effectsQueue) {
			for (EffectGO<?> e : effectsQueue) {
				effects.add(e);
			}
			effectsQueue.clear();
		}

	}

	@Override
	public void addEffect(EAdEffect e) {
		this.addEffect(e, null, null);
	}

	@Override
	public SceneElementGO<?> getActiveElement() {
		EAdSceneElement activeElement = valueMap.getValue(
				valueMap.getValue(SystemFields.ACTIVE_ELEMENT),
				SceneElementDef.VAR_SCENE_ELEMENT);
		if (activeElement != null)
			return sceneElementFactory.get(activeElement);
		else
			return null;
	}

	@Override
	public void setActiveElement(EAdSceneElement activeElement) {
		valueMap.setValue(SystemFields.ACTIVE_ELEMENT, activeElement);
	}

	@Override
	public void setInitialScene(EAdScene initialScene) {
		ChangeSceneEf ef = new ChangeSceneEf(initialScene);
		this.addEffect(ef);
		// ((LoadingScreen) loadingScreen).setInitialScreen(initialScene);
	}

	private void installPlugins() {
		pluginHandler.install(effectFactory);
		pluginHandler.install(eventGOFactory);
		pluginHandler.install(sceneElementFactory);
	}

	public void saveState() {
		ArrayList<EAdEffect> effectsList = new ArrayList<EAdEffect>();
		for (EffectGO<?> effGO : effects) {
			effectsList.add(effGO.getEffect());
		}

		Stack<EAdScene> stack = new Stack<EAdScene>();
		for (EAdScene s : previousSceneStack) {
			stack.add(s);
		}

		Map<EAdVarDef<?>, Object> systemVars = new HashMap<EAdVarDef<?>, Object>();
		systemVars.putAll(valueMap.getSystemVars());

		Map<EAdElement, Map<EAdVarDef<?>, Object>> originalElementVars = valueMap
				.getElementVars();
		Map<EAdElement, Map<EAdVarDef<?>, Object>> elementVars = new HashMap<EAdElement, Map<EAdVarDef<?>, Object>>();
		for (Entry<EAdElement, Map<EAdVarDef<?>, Object>> entry : originalElementVars
				.entrySet()) {
			Map<EAdVarDef<?>, Object> map = new HashMap<EAdVarDef<?>, Object>();
			map.putAll(entry.getValue());
			elementVars.put(entry.getKey(), map);
		}

		ArrayList<EAdElement> updateList = new ArrayList<EAdElement>();
		updateList.addAll(valueMap.getUpdateList());

		gameStateData = new GameStateData(scene.getElement(), effectsList,
				stack, systemVars, elementVars, updateList);
	}

	private GameStateData clone(GameStateData state) {
		ArrayList<EAdEffect> effectsList = new ArrayList<EAdEffect>();
		for (EAdEffect eff : state.getEffects()) {
			effectsList.add(eff);
		}

		Stack<EAdScene> stack = new Stack<EAdScene>();
		for (EAdScene s : state.getPreviousSceneStack()) {
			stack.add(s);
		}

		Map<EAdVarDef<?>, Object> systemVars = new HashMap<EAdVarDef<?>, Object>();
		systemVars.putAll(state.getSystemVars());

		Map<EAdElement, Map<EAdVarDef<?>, Object>> originalElementVars = state
				.getElementVars();
		Map<EAdElement, Map<EAdVarDef<?>, Object>> elementVars = new HashMap<EAdElement, Map<EAdVarDef<?>, Object>>();
		for (Entry<EAdElement, Map<EAdVarDef<?>, Object>> entry : originalElementVars
				.entrySet()) {
			Map<EAdVarDef<?>, Object> map = new HashMap<EAdVarDef<?>, Object>();
			map.putAll(entry.getValue());
			elementVars.put(entry.getKey(), map);
		}

		ArrayList<EAdElement> updateList = new ArrayList<EAdElement>();
		updateList.addAll(state.getUpdateList());
		
		return new GameStateData(state.getScene(), effectsList, stack,
				systemVars, elementVars, updateList);
	}

	public void loadState() {
		if (gameStateData == null) {
			logger.info("No state saved.");
		} else {
			GameStateData gameStateData = clone(this.gameStateData);
			this.previousSceneStack = gameStateData.getPreviousSceneStack();
			sceneElementFactory.remove(gameStateData.getScene());

			scene = (SceneGO<?>) sceneElementFactory.get(gameStateData
					.getScene());
			scene.update();

			// FIXME this will fail in some cases (when the effect depend on an
			// InputAction, for example
			for (EAdEffect effect : gameStateData.getEffects()) {
				this.addEffect(effect);
			}

			valueMap.setElementVars(gameStateData.getElementVars());
			valueMap.setUpdateList(gameStateData.getUpdateList());
			valueMap.getUpdateList().addAll(
					gameStateData.getElementVars().keySet());
			valueMap.setSystemVars(gameStateData.getSystemVars());

		}
	}

}

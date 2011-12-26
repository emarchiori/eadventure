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

package es.eucm.eadventure.engine.core.impl.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import es.eucm.eadventure.common.ReflectionProvider;
import es.eucm.eadventure.common.model.elements.EAdAdventureModel;
import es.eucm.eadventure.common.model.elements.EAdAdventureModelImpl;
import es.eucm.eadventure.common.model.elements.scene.EAdScene;
import es.eucm.eadventure.engine.core.debuggers.Debugger;
import es.eucm.eadventure.engine.core.debuggers.impl.EAdMainDebugger;
import es.eucm.eadventure.engine.core.evaluators.EvaluatorFactory;
import es.eucm.eadventure.engine.core.evaluators.EvaluatorFactoryImpl;
import es.eucm.eadventure.engine.core.game.Game;
import es.eucm.eadventure.engine.core.game.GameController;
import es.eucm.eadventure.engine.core.game.GameControllerImpl;
import es.eucm.eadventure.engine.core.game.GameImpl;
import es.eucm.eadventure.engine.core.game.GameState;
import es.eucm.eadventure.engine.core.game.GameStateImpl;
import es.eucm.eadventure.engine.core.game.ValueMap;
import es.eucm.eadventure.engine.core.game.VariableMap;
import es.eucm.eadventure.engine.core.gameobjects.factories.EffectGOFactory;
import es.eucm.eadventure.engine.core.gameobjects.factories.EffectGOFactoryImpl;
import es.eucm.eadventure.engine.core.gameobjects.factories.EventGOFactory;
import es.eucm.eadventure.engine.core.gameobjects.factories.EventGOFactoryImpl;
import es.eucm.eadventure.engine.core.gameobjects.factories.SceneElementGOFactory;
import es.eucm.eadventure.engine.core.gameobjects.factories.SceneElementGOFactoryImpl;
import es.eucm.eadventure.engine.core.gameobjects.huds.EffectHUD;
import es.eucm.eadventure.engine.core.gameobjects.huds.EffectHUDImpl;
import es.eucm.eadventure.engine.core.impl.LoadingScreen;
import es.eucm.eadventure.engine.core.inventory.InventoryHandler;
import es.eucm.eadventure.engine.core.inventory.InventoryHandlerImpl;
import es.eucm.eadventure.engine.core.operator.OperatorFactory;
import es.eucm.eadventure.engine.core.operators.OperatorFactoryImpl;
import es.eucm.eadventure.engine.core.platform.AbstractEngineConfiguration;
import es.eucm.eadventure.engine.core.platform.FontHandlerImpl;
import es.eucm.eadventure.engine.core.platform.GenericInjector;
import es.eucm.eadventure.engine.core.platform.EngineConfiguration;
import es.eucm.eadventure.engine.core.platform.FontHandler;
import es.eucm.eadventure.engine.core.platform.impl.JavaInjector;
import es.eucm.eadventure.engine.core.platform.impl.JavaPluginHandler;
import es.eucm.eadventure.engine.core.platform.impl.JavaReflectionProvider;
import es.eucm.eadventure.engine.core.plugins.PluginHandler;
import es.eucm.eadventure.engine.core.trajectories.TrajectoryFactory;
import es.eucm.eadventure.engine.core.trajectories.TrajectoryFactoryImpl;

public class BasicGameModule extends AbstractModule {

	@Override
	protected void configure() {
		installFactories();
		bind(ValueMap.class).to(VariableMap.class);
		bind(EngineConfiguration.class)
.to(AbstractEngineConfiguration.class);
		bind(GameState.class).to(GameStateImpl.class);
		bind(GameController.class).to(GameControllerImpl.class);
		bind(Game.class).to(GameImpl.class);
		bind(EffectHUD.class).to(EffectHUDImpl.class);
		bind(FontHandler.class).to(FontHandlerImpl.class);
		bind(Debugger.class).to(EAdMainDebugger.class);
		bind(PluginHandler.class).to(JavaPluginHandler.class);
		bind(GenericInjector.class).to(JavaInjector.class);
		bind(InventoryHandler.class).to(InventoryHandlerImpl.class);

		bind(ReflectionProvider.class).to(JavaReflectionProvider.class);

		bind(EAdAdventureModel.class).to(EAdAdventureModelImpl.class);
		bind(EAdScene.class).annotatedWith(Names.named("LoadingScreen"))
				.to(LoadingScreen.class).asEagerSingleton();

	}

	private void installFactories() {
		bind(EvaluatorFactory.class).to(EvaluatorFactoryImpl.class);
		bind(OperatorFactory.class).to(OperatorFactoryImpl.class);
		bind(TrajectoryFactory.class).to(TrajectoryFactoryImpl.class);
		bind(SceneElementGOFactory.class).to(SceneElementGOFactoryImpl.class);
		bind(EffectGOFactory.class).to(EffectGOFactoryImpl.class);
		bind(EventGOFactory.class).to(EventGOFactoryImpl.class);
	}

	@Provides
	@Named("classParam")
	public String provideThreaded() {
		return "class";
	}

}

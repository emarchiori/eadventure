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

package ead.engine.core.gameobjects.huds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import ead.common.model.EAdElement;
import ead.common.model.elements.ResourcedElement;
import ead.common.model.elements.guievents.enums.KeyEventType;
import ead.common.model.elements.guievents.enums.KeyGEvCode;
import ead.common.model.elements.scenes.SceneElement;
import ead.common.model.elements.scenes.SceneElementDef;
import ead.common.model.elements.variables.SystemFields;
import ead.common.model.predef.events.ChaseMouseEv;
import ead.common.model.predef.events.StayInBoundsEv;
import ead.common.params.fills.ColorFill;
import ead.common.params.text.EAdString;
import ead.common.resources.EAdBundleId;
import ead.common.resources.assets.drawable.EAdDrawable;
import ead.common.resources.assets.drawable.basics.Caption;
import ead.common.resources.assets.drawable.basics.Image;
import ead.common.resources.assets.text.BasicFont;
import ead.common.util.EAdPosition;
import ead.engine.core.game.GameState;
import ead.engine.core.game.ValueMap;
import ead.engine.core.gameobjects.GameObjectManager;
import ead.engine.core.gameobjects.factories.SceneElementGOFactory;
import ead.engine.core.gameobjects.go.DrawableGO;
import ead.engine.core.input.InputAction;
import ead.engine.core.input.InputHandler;
import ead.engine.core.input.actions.KeyInputAction;
import ead.engine.core.platform.EngineConfiguration;
import ead.engine.core.platform.GUI;
import ead.engine.core.platform.assets.AssetHandler;
import ead.engine.core.platform.assets.RuntimeDrawable;
import ead.engine.core.util.EAdTransformation;
import ead.tools.StringHandler;

/**
 * <p>
 * The basic HUD contains the contextual information about elements (i.e. a sign
 * with the element name), and the mouse pointer. Mouse pointer can be disabled
 * through the system field {@link SystemFields#SHOW_MOUSE}
 * </p>
 * 
 */
@Singleton
public class TopBasicHUDImpl extends AbstractHUD implements TopBasicHUD {

	/**
	 * The logger
	 */
	protected static final Logger logger = LoggerFactory.getLogger("BasicHUDImpl");

	private static final int CURSOR_SIZE = 32;

	private MenuHUD menuHUD;

	private SceneElementGOFactory sceneElementFactory;

	protected GameState gameState;

	protected InputHandler inputHandler;

	private EngineConfiguration engineConfiguration;

	private StringHandler stringHandler;

	private AssetHandler assetHandler;

	private GameObjectManager gameObjectManager;

	private DrawableGO<?> currentGO;

	// Contextual
	private SceneElement contextual;

	private Caption contextualCaption;

	// Mouse
	private SceneElement mouse;

	private DrawableGO<?> mouseGO;

	private EAdDrawable cursor;

	private EAdBundleId mouseBundle = new EAdBundleId("mouse2");

	private int i = 0;

	@Inject
	public TopBasicHUDImpl(MenuHUD menuHUD, SceneElementGOFactory gameObjectFactory,
			GameState gameState, InputHandler inputHandler, StringHandler stringHandler, GUI gui,
			AssetHandler assetHandler, EngineConfiguration engineConfiguration) {
		super(gui);
		this.menuHUD = menuHUD;
		this.sceneElementFactory = gameObjectFactory;
		this.gameState = gameState;
		this.inputHandler = inputHandler;
		this.stringHandler = stringHandler;
		this.assetHandler = assetHandler;
		this.engineConfiguration = engineConfiguration;
	}

	public void setGameObjectManager(GameObjectManager gameObjectManager) {
		this.gameObjectManager = gameObjectManager;
	}

	@Override
	public boolean processAction(InputAction<?> action) {
		if (action instanceof KeyInputAction) {
			KeyInputAction keyAction = (KeyInputAction) action;
			if (keyAction.getKeyCode() == KeyGEvCode.ESC
					&& keyAction.getType() == KeyEventType.KEY_PRESSED) {
				gameObjectManager.addHUD(menuHUD);
				gameState.setPaused(true);
				action.consume();
				return true;
			}
		}
		return false;
	}

	public void doLayout(EAdTransformation t) {
		super.doLayout(t);
		gui.addElement(mouseGO, t);
	}

	@Override
	public void update() {
		super.update();
		updateContextual();
		updateMouse();
	}

	@Override
	public boolean contains(int x, int y) {
		return false;
	}

	// Contextual
	private void initContextual() {
		contextualCaption = new Caption();
		contextualCaption.setFont(new BasicFont(12.0f));
		contextualCaption.setBubblePaint(new ColorFill(255, 255, 125));
		contextualCaption.setPadding(10);
		contextualCaption.setTextPaint(ColorFill.BLACK);
		stringHandler.setString(contextualCaption.getText(), "");
		contextual = new SceneElement(contextualCaption);
		contextual.setPosition(new EAdPosition(0, 0, 0.5f, 1.5f));
		contextual.setVarInitialValue(SceneElement.VAR_VISIBLE, Boolean.FALSE);
		contextual.setVarInitialValue(SceneElement.VAR_ENABLE, Boolean.FALSE);
		contextual.getEvents().add(new StayInBoundsEv(contextual));
		contextual.getEvents().add(new ChaseMouseEv());
		contextual.setId("contextual");
		addElement(sceneElementFactory.get(contextual));
	}

	private void updateContextual() {
		DrawableGO<?> go = inputHandler.getGameObjectUnderPointer();
		if (go != currentGO) {
			ValueMap valueMap = gameState.getValueMap();
			if (go != null) {
				EAdString name = valueMap.getValue((EAdElement) go.getElement(),
						SceneElement.VAR_NAME);
				if (name != null && !stringHandler.getString(name).equals("")) {
					stringHandler.setString(contextualCaption.getText(),
							stringHandler.getString(name));

					gameState.getValueMap().setValue(contextual, SceneElement.VAR_VISIBLE, true);
				} else {
					gameState.getValueMap().setValue(contextual, SceneElement.VAR_VISIBLE, false);

				}
			} else {
				gameState.getValueMap().setValue(contextual, SceneElement.VAR_VISIBLE, false);
			}
			currentGO = go;
		}
	}

	// Mouse
	private void initMouse() {
		mouse = new SceneElement(cursor);
		mouse.setId("mouse");
		mouse.setVarInitialValue(SceneElement.VAR_ENABLE, Boolean.FALSE);
		mouse.setPropagateGUIEvents(true);
		mouseGO = sceneElementFactory.get(mouse);
	}

	@SuppressWarnings("unchecked")
	private void checkMouseImage() {
		Image newCursor = gameState.getValueMap().getValue(SystemFields.MOUSE_CURSOR);
		if (cursor != newCursor) {
			cursor = newCursor;
			if (cursor != null) {
				RuntimeDrawable<EAdDrawable, ?> rAsset = (RuntimeDrawable<EAdDrawable, ?>) assetHandler
						.getRuntimeAsset(cursor);
				EAdBundleId bundle = i++ % 2 == 1 ? mouse.getDefinition().getInitialBundle()
						: mouseBundle;
				mouse.getDefinition().getResources()
						.addAsset(bundle, SceneElementDef.appearance, cursor);
				float scale = 1.0f;
				if (rAsset.getWidth() > 0 && rAsset.getHeight() > 0) {
					scale = 1.0f / (rAsset.getWidth() > rAsset.getHeight() ? rAsset.getWidth()
							/ CURSOR_SIZE : rAsset.getHeight() / CURSOR_SIZE);

				}
				gameState.getValueMap().setValue(mouse, SceneElement.VAR_SCALE, scale);
				gameState.getValueMap().setValue(mouse, ResourcedElement.VAR_BUNDLE_ID, bundle);
			}

		}
	}

	private void updateMouse() {
		checkMouseImage();
		int x = gameState.getValueMap().getValue(SystemFields.MOUSE_X);
		int y = gameState.getValueMap().getValue(SystemFields.MOUSE_Y);
		boolean showMouse = gameState.getValueMap().getValue(SystemFields.SHOW_MOUSE);

		gameState.getValueMap().setValue(mouse, SceneElement.VAR_X, x);
		gameState.getValueMap().setValue(mouse, SceneElement.VAR_Y, y);

		gameState.getValueMap().setValue(mouse, SceneElement.VAR_VISIBLE, showMouse);

		mouseGO.update();
		mouseGO.getTransformation().setClip(0, 0, engineConfiguration.getWidth(),
				engineConfiguration.getHeight());
	}
	
	public void init( ){
		initContextual();
		initMouse();
	}

}

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

package es.eucm.eadventure.engine.core.gameobjects.impl.transitions;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import es.eucm.eadventure.common.model.elements.EAdScene;
import es.eucm.eadventure.common.model.elements.impl.EAdBasicSceneElement;
import es.eucm.eadventure.common.params.EAdString;
import es.eucm.eadventure.common.params.fills.impl.EAdPaintImpl;
import es.eucm.eadventure.common.params.fills.impl.EAdColor;
import es.eucm.eadventure.common.params.geom.impl.EAdPositionImpl;
import es.eucm.eadventure.common.resources.StringHandler;
import es.eucm.eadventure.common.resources.assets.drawable.basics.Caption;
import es.eucm.eadventure.common.resources.assets.drawable.basics.Image;
import es.eucm.eadventure.common.resources.assets.drawable.basics.impl.CaptionImpl;
import es.eucm.eadventure.common.resources.assets.drawable.basics.impl.shapes.RectangleShape;
import es.eucm.eadventure.engine.core.GameState;
import es.eucm.eadventure.engine.core.MouseState;
import es.eucm.eadventure.engine.core.ValueMap;
import es.eucm.eadventure.engine.core.gameobjects.GameObject;
import es.eucm.eadventure.engine.core.gameobjects.GameObjectFactory;
import es.eucm.eadventure.engine.core.gameobjects.SceneGO;
import es.eucm.eadventure.engine.core.gameobjects.TransitionGO;
import es.eucm.eadventure.engine.core.gameobjects.impl.SceneGOImpl;
import es.eucm.eadventure.engine.core.platform.AssetHandler;
import es.eucm.eadventure.engine.core.platform.GUI;
import es.eucm.eadventure.engine.core.platform.PlatformConfiguration;
import es.eucm.eadventure.engine.core.platform.RuntimeAsset;
import es.eucm.eadventure.engine.core.util.EAdTransformation;

public class SimpleTransitionGO extends SceneGOImpl implements TransitionGO {

	private SceneGO<?> previousSceneGO;

	private EAdScene nextEAdScene;

	protected SceneGO<?> nextSceneGO;

	private RuntimeAsset<Image> background;

	private Caption caption;

	protected RuntimeAsset<Image> previousSceneImage;

	protected EAdBasicSceneElement loadingText;

	protected EAdBasicSceneElement screenBlock;

	protected boolean loading = false;

	protected boolean loaded = false;

	@Inject
	public SimpleTransitionGO(AssetHandler assetHandler,
			StringHandler stringHandler, GameObjectFactory gameObjectFactory,
			GUI gui, GameState gameState, ValueMap valueMap,
			PlatformConfiguration platformConfiguration) {
		super(assetHandler, stringHandler, gameObjectFactory, gui, gameState, valueMap,
				platformConfiguration);

		EAdString string = EAdString.newEAdString("Loading");
		string.parse("Loading");
		caption = new CaptionImpl(string);
		loadingText = new EAdBasicSceneElement("loadingText");
		loadingText.getResources().addAsset(loadingText.getInitialBundle(),
				EAdBasicSceneElement.appearance, caption);
		loadingText.setPosition(EAdPositionImpl.volatileEAdPosition(750, 550,
				1.0f, 1.0f));

		RectangleShape rs = new RectangleShape(
				platformConfiguration.getVirtualWidth(),
				platformConfiguration.getVirtualHeight());
		rs.setPaint(new EAdPaintImpl(new EAdColor(100, 100, 100, 30),
				EAdColor.BLACK));

		screenBlock = new EAdBasicSceneElement("screenBlock");
		screenBlock.getResources().addAsset(screenBlock.getInitialBundle(),
				EAdBasicSceneElement.appearance, rs);
	}

	@Override
	public boolean acceptsVisualEffects() {
		return false;
	}

	@Override
	public void setElement(EAdScene element) {

	}

	@Override
	public GameObject<?> getDraggableElement(MouseState mouseState) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.eucm.eadventure.engine.core.gameobjects.impl.SceneGOImpl#doLayout(int,
	 * int)
	 * 
	 * The bloking element and the loading text should never be offset
	 */

	@Override
	public void doLayout(EAdTransformation transformation) {
		gui.addElement(gameObjectFactory.get(screenBlock), transformation);
		gui.addElement(gameObjectFactory.get(loadingText), transformation);
		if (loaded) {
			gui.addElement(nextSceneGO, transformation);
		}
	}

	@Override
	public void setPrevious(SceneGO<?> screen) {
		this.previousSceneGO = screen;
	}

	@Override
	public void setNext(EAdScene screen) {
		this.nextEAdScene = screen;
		if (nextEAdScene == null)
			nextEAdScene = this.gameState.getPreviousScene();
	}

	@Override
	public RuntimeAsset<Image> getBackground() {
		return background;
	}

	@Override
	public void update() {
		if (!loading) {
			loading = true;
			nextSceneGO = (SceneGO<?>) gameObjectFactory.get(nextEAdScene);

			List<RuntimeAsset<?>> newAssetList = nextSceneGO.getAssets(
					new ArrayList<RuntimeAsset<?>>(), false);

			List<RuntimeAsset<?>> oldAssetList = new ArrayList<RuntimeAsset<?>>();
			oldAssetList = previousSceneGO.getAssets(oldAssetList, true);
			// unload unnecessary assets
			if (oldAssetList != null) {
				for (RuntimeAsset<?> asset : oldAssetList) {
					if (asset != null && newAssetList != null && !newAssetList.contains(asset)) {
						asset.freeMemory();
					}
				}
			}
			System.gc();
			// pre-load minimum required assets
			for (RuntimeAsset<?> asset : newAssetList) {
				if (asset != null && !asset.isLoaded())
					asset.loadAsset();
			}

			nextSceneGO.update();
			loaded = true;
		}

		if (previousSceneImage == null) {
			previousSceneImage = gui.commitToImage();
		}

		if (loaded) {
			gameState.setScene(nextSceneGO);
			if (previousSceneImage != null)
				previousSceneImage.freeMemory();
		}
	}

}

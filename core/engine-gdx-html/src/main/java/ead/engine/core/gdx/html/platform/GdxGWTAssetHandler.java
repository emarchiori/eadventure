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

package ead.engine.core.gdx.html.platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import ead.engine.core.game.VariableMap;
import ead.engine.core.gdx.assets.GdxAssetHandler;
import ead.engine.core.gdx.assets.GdxFont;
import ead.engine.core.gdx.assets.GdxSound;
import ead.engine.core.gdx.assets.drawables.GdxImage;
import ead.engine.core.gdx.assets.drawables.GdxShape;
import ead.engine.core.gdx.platform.GdxCanvas;
import ead.engine.core.platform.GUI;
import ead.engine.core.platform.assets.RuntimeAsset;
import ead.engine.core.platform.assets.drawables.basics.RuntimeCaption;
import ead.engine.core.platform.assets.drawables.basics.RuntimeFramesAnimation;
import ead.engine.core.platform.assets.drawables.compounds.RuntimeComposedDrawable;
import ead.engine.core.platform.assets.drawables.compounds.RuntimeFilteredDrawable;
import ead.engine.core.platform.assets.drawables.compounds.RuntimeStateDrawable;
import ead.tools.GenericInjector;
import ead.tools.StringHandler;

@Singleton
public class GdxGWTAssetHandler extends GdxAssetHandler {

	private Logger logger = LoggerFactory.getLogger("GdxGWTAssetHandler");

	private VariableMap valueMap;

	private StringHandler stringHandler;

	private GUI gui;

	@Inject
	public GdxGWTAssetHandler(GenericInjector injector, VariableMap valueMap,
			StringHandler stringHandler, GUI gui) {
		super(injector);
		this.valueMap = valueMap;
		this.stringHandler = stringHandler;
		this.gui = gui;
	}

	@Override
	public void initialize() {
		super.initialize();
		setLoaded(true);
	}

	@Override
	public RuntimeAsset<?> getInstance(Class<? extends RuntimeAsset<?>> clazz) {

		// FIXME: it is ugly to discard all these generics; find another way to
		// get clean builds
		@SuppressWarnings("rawtypes")
		RuntimeAsset r = null;
		if (clazz == GdxImage.class)
			r = new GdxImage(this);
		else if (clazz == GdxShape.class)
			r = new GdxShape();
		else if (clazz == (Object) RuntimeCaption.class)
			r = new RuntimeCaption<GdxCanvas>(gui, fontHandler, valueMap,
					stringHandler, this);
		else if (clazz == GdxSound.class)
			r = new GdxSound(this);
		else if (clazz == (Object) RuntimeComposedDrawable.class)
			r = new RuntimeComposedDrawable<GdxCanvas>(this);
		else if (clazz == (Object) RuntimeFilteredDrawable.class)
			r = new RuntimeFilteredDrawable<GdxCanvas>(this);
		else if (clazz == (Object) RuntimeFramesAnimation.class)
			r = new RuntimeFramesAnimation(this);
		else if (clazz == (Object) RuntimeStateDrawable.class)
			r = new RuntimeStateDrawable(this);
		else if (clazz == (Object) GdxFont.class) {
			r = new GdxFont(this);
		} else {
			logger.error("No instance for runtime asset: {}", clazz);
		}

		return (RuntimeAsset<?>) r;
	}

}

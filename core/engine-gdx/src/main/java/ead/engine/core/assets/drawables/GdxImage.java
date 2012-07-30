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

package ead.engine.core.assets.drawables;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.google.inject.Inject;

import ead.engine.core.assets.GdxAssetHandler;
import ead.engine.core.platform.assets.AssetHandler;
import ead.engine.core.platform.assets.drawables.basics.RuntimeImage;
import ead.engine.core.platform.rendering.GenericCanvas;

public class GdxImage extends RuntimeImage<SpriteBatch> {
	
	private TextureRegion textureRegion;
	private Pixmap pixmap;
	private GdxAssetHandler assetHandler;

	@Inject
	public GdxImage(AssetHandler assetHandler) {
		super(null);
		this.assetHandler = (GdxAssetHandler) assetHandler;
	}
	
	@Override
	public boolean loadAsset() {
		pixmap = new Pixmap(assetHandler.getFileHandle(descriptor.getUri().getPath()));
		Texture texture = new Texture(pixmap);
		textureRegion = new TextureRegion(texture);
		textureRegion.flip(false, true);
		return false;
	}
	
	@Override
	public boolean contains(int x, int y) {
		if ( super.contains(x, y)){
			int alpha = pixmap.getPixel( x, y ) & 255;
			return alpha > 128;
		}
		return false;
	}

	@Override
	public int getWidth() {
		return Math.abs( textureRegion.getRegionWidth() );
	}

	@Override
	public int getHeight() {
		return Math.abs( textureRegion.getRegionHeight() );
	}



	@Override
	public void freeMemory() {
		textureRegion.getTexture().dispose();
	}

	@Override
	public boolean isLoaded() {
		return textureRegion != null;
	}
	
	@Override
	public void render(GenericCanvas<SpriteBatch> c) {
		render(c.getNativeGraphicContext());
	}

	public void render(SpriteBatch batch) {
		batch.draw(textureRegion, 0, 0);
	}

}

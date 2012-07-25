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

package ead.engine.core.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.google.inject.Inject;

import ead.common.util.EAdRectangle;
import ead.engine.core.platform.assets.AssetHandler;
import ead.engine.core.platform.assets.text.BasicRuntimeFont;

public class GdxFont extends BasicRuntimeFont {

	private BitmapFont bitmapFont;

	private static final String defaultFont = "data/font/droid-12.fnt";
	private static final String defaultFontPng = "data/font/droid-12.png";

	@Inject
	public GdxFont(AssetHandler assetHandler) {
		super(assetHandler);
	}

	public boolean loadAsset() {
		super.loadAsset();
		int size = Math.round(descriptor.getSize());
		String modifier = "";
		switch (descriptor.getStyle()) {
		case BOLD:
			modifier = "bold";
			break;
		case ITALIC:
			modifier = "italic";
			break;
		default:
			modifier = "";
		}
		String fileName = "data/font/droid-" + size
				+ (modifier.equals("") ? "" : "-" + modifier);

		String fontData = defaultFont;
		String fontPng = defaultFontPng;

		if (Gdx.files.internal(fileName + ".fnt").exists()
				&& Gdx.files.internal(fileName + ".png").exists()) {
			fontData = fileName + ".fnt";
			fontPng = fileName + ".png";
		}
		bitmapFont = new BitmapFont(Gdx.files.internal(fontData),
				Gdx.files.internal(fontPng), true);
		return true;
	}

	@Override
	public int stringWidth(String string) {
		return (int) bitmapFont.getBounds(string).width;
	}

	@Override
	public int lineHeight() {
		return (int) bitmapFont.getLineHeight();
	}

	@Override
	public EAdRectangle stringBounds(String string) {
		TextBounds b = bitmapFont.getBounds(string);
		return new EAdRectangle(0, 0, (int) b.width, (int) b.height);
	}

	public BitmapFont getBitmapFont() {
		return bitmapFont;
	}

}

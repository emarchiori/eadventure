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

package ead.engine.android.gl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;

import com.google.inject.Inject;

import ead.engine.core.platform.assets.AssetHandler;
import ead.engine.core.platform.assets.drawables.basics.RuntimeImage;
import ead.engine.core.platform.rendering.GenericCanvas;

public class GLImage extends RuntimeImage<GL10> {

	private static final Logger logger = LoggerFactory.getLogger("GLImage");

	private int width;

	private int height;

	private FloatBuffer buffer;

	private FloatBuffer colorBuffer;

	private FloatBuffer textureBuffer;

	private int textureId = -1;

	private int position;

	@Inject
	public GLImage(AssetHandler assetHandler) {
		super(assetHandler);
		width = 1;
		height = 1;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public boolean loadAsset() {

		Bitmap b = decodeFile(assetHandler.getAbsolutePath(descriptor.getUri()
				.getPath()));
		if (b != null) {
			width = b.getWidth();
			height = b.getHeight();
			((GLAssetHandler) assetHandler).addTexture(descriptor, b);
			b.recycle();
			buffer = ((GLAssetHandler) assetHandler).getFloatBuffer(width,
					height);
			float[] color = new float[] { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
					0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f };
			colorBuffer = ((GLAssetHandler) assetHandler).getFloatBuffer(color);

		}
		return true;
	}

	@Override
	public void freeMemory() {

	}

	@Override
	public boolean isLoaded() {
		return true;
	}

	@Override
	public void render(GenericCanvas<GL10> c) {
		if (buffer != null) {
			GLCanvas gl = (GLCanvas) c;
			buffer.position(0);
			gl.setAttributePosition(buffer);
			colorBuffer.position(0);
			gl.setAttributeColor(colorBuffer);

			if (textureId == -1) {
				int index = ((GLAssetHandler) assetHandler)
						.getBitmapTextureIndex(descriptor);
				textureBuffer = ((GLAssetHandler) assetHandler)
						.getBuffer(index);
				textureId = ((GLAssetHandler) assetHandler).getTextureId(index);
				position = ((GLAssetHandler) assetHandler).getPosition(index,
						descriptor);
			}

			textureBuffer.position(position);
			gl.setAttributeTexture(textureBuffer, textureId);

			GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
		}
	}

	/**
	 * Returns a bitmap considering its size in order to reduce the amount of
	 * memory used
	 */
	private Bitmap decodeFile(String path) {

		File f = new File(path);
		Bitmap b = null;
		try {
			BitmapFactory.Options o = new BitmapFactory.Options();
			// o.inInputShareable = true;
			// o.inPurgeable = true;
			// o.inTempStorage = new byte[16 * 1024];
			// o.inPreferredConfig = Bitmap.Config.ARGB_8888;
			o.inScaled = false;

			FileInputStream fis = new FileInputStream(f);
			b = BitmapFactory.decodeStream(fis, null, o);
			try {
				fis.close();
			} catch (IOException e) {
				logger.info("Couldn't close file input stream");
			}
		} catch (FileNotFoundException e) {
			logger.info("File not found: {}", f.getName());
		} catch (OutOfMemoryError e) {
			logger.info("Out of memory error caused by: {}", f.getName());
		}
		return b;
	}

}

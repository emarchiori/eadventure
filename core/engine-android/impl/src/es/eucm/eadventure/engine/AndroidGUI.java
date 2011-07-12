package es.eucm.eadventure.engine;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import android.graphics.Canvas;
import es.eucm.eadventure.common.resources.assets.drawable.Image;
import es.eucm.eadventure.engine.core.KeyboardState;
import es.eucm.eadventure.engine.core.MouseState;
import es.eucm.eadventure.engine.core.ValueMap;
import es.eucm.eadventure.engine.core.gameobjects.GameObjectManager;
import es.eucm.eadventure.engine.core.gameobjects.huds.BasicHUD;
import es.eucm.eadventure.engine.core.platform.GraphicRendererFactory;
import es.eucm.eadventure.engine.core.platform.PlatformConfiguration;
import es.eucm.eadventure.engine.core.platform.RuntimeAsset;
import es.eucm.eadventure.engine.core.platform.impl.AbstractGUI;
import es.eucm.eadventure.engine.extra.AndroidCanvas;
import es.eucm.eadventure.engine.extra.EAdventureRenderingThread;

@Singleton
public class AndroidGUI extends AbstractGUI<Canvas> {

	private AndroidCanvas canvas;
	
	@SuppressWarnings("rawtypes")
	@Inject
	public AndroidGUI(PlatformConfiguration platformConfiguration,
			GraphicRendererFactory<?> assetRendererFactory,
			GameObjectManager gameObjectManager,
			MouseState mouseState,
			BasicHUD androidBasicHUD,
			KeyboardState keyboardState, ValueMap valueMap) {
		super(platformConfiguration, assetRendererFactory, gameObjectManager,
				mouseState, keyboardState, valueMap);
		gameObjects.addHUD(androidBasicHUD);
	}
	
	@Override
	public RuntimeAsset<Image> commitToImage() {
		//FIXME does not commit to image
		//		canvas.getBitmap();?
		return null;
	}

	@Override
	public void initilize() {
		// Nothing to initialize
	}

	@Override
	public void commit(float interpolation) {
		processInput();
		
		if (canvas != null) {
			synchronized(EAdventureRenderingThread.canvasLock) {
				render(canvas, interpolation);
			}
		}
	}

	@Override
	public void showSpecialResource(Object object, int x, int y,
			boolean fullscreen) {
		// TODO Process request, possible object could be an intent?
	}

	public void setCanvas(AndroidCanvas aCanvas) {
		this.canvas = aCanvas;
	}

}

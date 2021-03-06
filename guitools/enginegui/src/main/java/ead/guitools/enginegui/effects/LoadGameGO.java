package ead.guitools.enginegui.effects;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.inject.Inject;

import ead.common.model.elements.EAdAdventureModel;
import ead.common.util.EAdURI;
import ead.engine.core.game.GameLoader;
import ead.engine.core.game.GameState;
import ead.engine.core.gameobjects.effects.AbstractEffectGO;
import ead.engine.core.gameobjects.factories.SceneElementGOFactory;
import ead.engine.core.gdx.desktop.platform.GdxDesktopGUI;
import ead.engine.core.platform.GUI;
import ead.engine.core.platform.assets.AssetHandler;
import ead.guitools.enginegui.EngineGUI;
import ead.importer.EAdventureImporter;

public class LoadGameGO extends AbstractEffectGO<LoadGameEffect> {

	private static boolean initialize = false;
	private static JFileChooser chooser = null;
	private static EAdventureImporter importer = null;

	private GameLoader gameLoader;
	private EAdAdventureModel model;
	private boolean done;
	private AssetHandler assetHandler;
	private String resourcesPath;

	@Inject
	public LoadGameGO(SceneElementGOFactory gameObjectFactory, GUI gui,
			GameState gameState, GameLoader gameLoader,
			AssetHandler assetHandler) {
		super(gameObjectFactory, gui, gameState);
		this.gameLoader = gameLoader;
		this.assetHandler = assetHandler;
	}

	@Override
	public void initialize() {
		super.initialize();
		if (!initialize) {
			initStatic();
		}
		done = false;
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				File f = new File(EngineGUI.getProperty("folder", "."));
				if (f.exists()) {
					chooser.setSelectedFile(f);
				}
				if (chooser.showOpenDialog(((GdxDesktopGUI) gui).getFrame()) == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					EngineGUI.setProperty("folder", file.getAbsolutePath());
					model = importer.importGame(file.getAbsolutePath(), null);
					resourcesPath = importer.getDestinyFile();
					done = true;
				}
			}

		});
	}

	private static void initStatic() {
		initialize = true;
		chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"EAD games", "ead");
		chooser.setFileFilter(filter);
		importer = new EAdventureImporter();
	}

	@Override
	public boolean isVisualEffect() {
		return false;
	}

	public void finish() {
		super.finish();
		assetHandler.setResourcesLocation(new EAdURI(resourcesPath));
		gameLoader.loadGame(model, importer.getStrings(), null);
	}

	@Override
	public boolean isFinished() {
		return done;
	}

}

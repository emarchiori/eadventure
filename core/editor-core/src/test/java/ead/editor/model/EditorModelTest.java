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
package ead.editor.model;

import com.google.inject.Guice;
import com.google.inject.Injector;
import ead.common.importer.ImporterConfigurationModule;
import ead.common.model.EAdElement;
import ead.editor.Log4jConfig;
import ead.engine.core.platform.module.DesktopAssetHandlerModule;
import ead.engine.core.platform.module.DesktopModule;
import ead.engine.core.platform.modules.BasicGameModule;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mfreire
 */
public class EditorModelTest {

    private static final Logger logger = LoggerFactory.getLogger("EditorModelTest");

    private EditorModel model;

    public EditorModelTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        Log4jConfig.configForConsole(Log4jConfig.Slf4jLevel.Info, new Object[]{
			"ModelVisitorDriver", Log4jConfig.Slf4jLevel.Info,
			"EditorModel", Log4jConfig.Slf4jLevel.Debug,
			"NullAnnotator", Log4jConfig.Slf4jLevel.Debug,
		    "EAdventureImporter", Log4jConfig.Slf4jLevel.Debug
		});

        Injector injector = Guice.createInjector(
                new ImporterConfigurationModule(),
                new BasicGameModule(),
                new DesktopModule(),
                new DesktopAssetHandlerModule());
        model = injector.getInstance(EditorModel.class);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of loadFromImportFile method, of class EditorModel.
     */
    @Test
    public void testLoadFromImportFile() {
        System.out.println("loadFromImportFile");
    }

    /**
     * Test of save method, of class EditorModel.
     */
    @Test
    public void testSave() throws Exception {
        System.out.println("save");
    }

    /**
     * Test of load method, of class EditorModel.
     */
    @Test
    public void testLoad() throws Exception {
        System.out.println("load");
    }

	// --- non-automated tests ---

    /**
	 * Tests indexing and searching
	 */
    private void testSimpleSearch() {
		String s = "disp_x";
        logger.info("Now searching for '"+s+"' in all fields, all nodes...");
		for (DependencyNode e : model.searchAll(s)) {
            logger.info("found: " + e.getId() + " "
                    + e.getContent().getClass().getSimpleName() + " "
                    + e.getContent() + " :: "
                    + (e.getContent() instanceof EAdElement ? ((EAdElement) e.getContent()).getId() : "??"));
        }
    }

	/**
	 * Test import-loading of an old ead 1.x file
	 */
	private void testImportLoad(File oldEadFile) throws IOException {
		model.loadFromImportFile(oldEadFile);
	}

	/**
	 * Test saving editor-model to new format (must have something already loaded)
	 */
	private void testSave(File saveFile) throws IOException {
		model.save(saveFile);
	}

	/**
	 * Test loading editor-model from new format (create dusing testSave)
	 */
	private void testLoad(File saveFile) throws IOException {
		model.load(saveFile);
	}

    public static void main(String[] args) throws IOException {
		EditorModelTest emt = new EditorModelTest();
		emt.setUp();

		logger.info("Starting test run...");

		// Import-load
		File f = new File("/home/mfreire/code/e-ucm/e-adventure-1.x/games/PrimerosAuxiliosGame.ead");
		emt.testImportLoad(f);

		// Simple search
//		emt.testSimpleSearch();

//		// Create gephi-compatible graph
//      emt.model.exportGraph(new File("/tmp/exported.graphml"));

//      // Test saving with a big random EditorNode
//		ArrayList<DependencyNode> test = new ArrayList<DependencyNode>();
//		for (int i=1; i<10; i++) test.add(emt.model.getNode(i));
//		EditorNode en = new EditorNode(emt.model.generateId());
//		emt.model.registerEditorNode(en, test);
//		File saveFile = new File("/tmp/saved.eap");
//		emt.testSave(saveFile);

//      // Test loading from previous save-file
//		emt.testLoad(saveFile);
    }
}

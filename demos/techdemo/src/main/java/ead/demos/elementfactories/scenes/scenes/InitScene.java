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

package ead.demos.elementfactories.scenes.scenes;

import java.util.ArrayList;
import java.util.List;

import ead.common.model.elements.effects.ChangeSceneEf;
import ead.common.model.elements.effects.text.SpeakEf;
import ead.common.model.elements.guievents.MouseGEv;
import ead.common.model.elements.scene.EAdSceneElementDef;
import ead.common.model.elements.scenes.SceneElement;
import ead.common.model.elements.scenes.SceneElementDef;
import ead.common.model.elements.transitions.DisplaceTransition;
import ead.common.model.elements.transitions.FadeInTransition;
import ead.common.model.elements.transitions.enums.DisplaceTransitionType;
import ead.common.model.predef.sceneelements.Button;
import ead.common.params.fills.ColorFill;
import ead.common.params.fills.Paint;
import ead.common.params.paint.EAdFill;
import ead.common.resources.assets.drawable.basics.Image;
import ead.common.resources.assets.text.BasicFont;
import ead.common.resources.assets.text.EAdFont;
import ead.common.util.EAdPosition.Corner;
import ead.demos.elementfactories.EAdElementsFactory;
import ead.demos.elementfactories.StringFactory;
import ead.demos.elementfactories.scenes.SceneDemo;
import ead.demos.elementfactories.scenes.normalguy.NgMainScreen;

public class InitScene extends EmptyScene {

	private List<SceneDemo> sceneDemos;

	private SceneElement goBack;

	private EAdSceneElementDef infoButton;

	private EAdFill fill = new ColorFill(255, 255, 255, 200);

	private EAdFont font = new BasicFont(18);

	private Paint speakPaint = new Paint(fill, ColorFill.LIGHT_GRAY, 5);

	public InitScene() {
		setId("InitScene");
		this.setBackground(new SceneElement(new Image(
				"@drawable/techdemo-bg.png")));
		getBackground().setId("background");
		initList();
		initGOBackButton();
		initInfoButton();
		int y = 200;
		int x = 120;
		StringFactory sf = EAdElementsFactory.getInstance().getStringFactory();
		for (SceneDemo s : sceneDemos) {
			Button b = new Button();
			sf.setString(b.getLabel(), s.getDemoName());
			b.setPosition(x, y);
			b.addBehavior(MouseGEv.MOUSE_LEFT_PRESSED, new ChangeSceneEf(s,
					new DisplaceTransition(1000,
							DisplaceTransitionType.VERTICAL, true)));
			this.getSceneElements().add(b);
			s.getSceneElements().add(goBack);
			SceneElement info = new SceneElement(infoButton);
			info.setPosition(Corner.BOTTOM_LEFT, 80, 590);
			SpeakEf effect = new SpeakEf();
			effect.setId("infoSpeak");
			effect.setColor(ColorFill.GRAY, speakPaint);
			effect.setFont(font);
			sf.setString(effect.getString(), s.getSceneDescription());
			info.addBehavior(MouseGEv.MOUSE_LEFT_PRESSED, effect);
			// info.setScale(0.5f);
			s.getSceneElements().add(info);
			y += 45;
			if (y > 520) {
				y = 200;
				x += 210;
			}
		}
	}

	private void initInfoButton() {
		infoButton = new SceneElementDef(new Image("@drawable/infobutton.png"));
		infoButton.setId("info");
	}

	private void initGOBackButton() {
		goBack = new SceneElement(new Image("@drawable/goback.png"));
		goBack.setId("goBack");
		goBack.setPosition(Corner.BOTTOM_LEFT, 10, 590);
		goBack.addBehavior(MouseGEv.MOUSE_LEFT_PRESSED, new ChangeSceneEf(this,
				new FadeInTransition(1000)));
		goBack.setInitialScale(0.5f);

	}

	private void initList() {
		sceneDemos = new ArrayList<SceneDemo>();
		sceneDemos.add(new EmptyScene());
		sceneDemos.add(new ShapeScene());
		sceneDemos.add(new TextsScene());
		sceneDemos.add(new CharacterScene());
		sceneDemos.add(new SpeakAndMoveScene());
		sceneDemos.add(new ComplexElementScene());
		sceneDemos.add(new SoundScene());
		sceneDemos.add(new DrawablesScene());
		// sceneDemos.add(new MoleGame());
		sceneDemos.add(new ShowQuestionScene());
		sceneDemos.add(new TrajectoriesScene());
		sceneDemos.add(new PhysicsScene());
		sceneDemos.add(new PhysicsScene2());
		sceneDemos.add(new DragDropScene());
		sceneDemos.add(new PositionScene());
		sceneDemos.add(new DepthZScene());
		sceneDemos.add(new SharingEffectsScene());
		sceneDemos.add(new InventoryScene());
		sceneDemos.add(new ScrollScene());
		sceneDemos.add(new FiltersDemo());
		sceneDemos.add(new VideoSceneDemo());
		sceneDemos.add(new WebMVideoScene());
		sceneDemos.add(new WidgetsScene());
		sceneDemos.add(new NgMainScreen(this));
		// sceneDemos.add(new NgRoom1());

	}

	@Override
	public String getSceneDescription() {
		return "A scene containing the demos scene";
	}

	public String getDemoName() {
		return "Scene Demo Chooser";
	}

}

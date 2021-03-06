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

import ead.common.model.elements.scenes.BasicScene;
import ead.common.model.elements.scenes.SceneElementDef;
import ead.common.params.fills.ColorFill;
import ead.common.params.fills.LinearGradientFill;
import ead.common.params.paint.EAdFill;
import ead.common.params.text.EAdString;
import ead.common.resources.assets.drawable.basics.shapes.RectangleShape;
import ead.demos.elementfactories.scenes.SceneDemo;

/**
 * An empty scene
 * 
 */
public class EmptyScene extends BasicScene implements SceneDemo {

	private RectangleShape rectangle;

	public EmptyScene() {
		super();
		setId("EmptyScene");
		((SceneElementDef) this.getDefinition()).setName(EAdString.newRandomEAdString("name"));
		((SceneElementDef) this.getDefinition()).setDesc(EAdString.newRandomEAdString("desc"));
		((SceneElementDef) this.getDefinition()).setDetailDesc(EAdString.newRandomEAdString("detailDesc"));
		((SceneElementDef) this.getDefinition()).setDoc(EAdString.newRandomEAdString("doc"));
		
		
		rectangle = new RectangleShape(800, 600);
		rectangle.setPaint(new LinearGradientFill(new ColorFill(240, 240, 240), ColorFill.WHITE, 800, 600));
		getBackground().getDefinition().getResources().addAsset(
				getBackground().getDefinition().getInitialBundle(),
				SceneElementDef.appearance, rectangle);
	}
	
	public void setBackgroundFill( EAdFill fill ){
		rectangle.setPaint(fill);
	}

	@Override
	public String getSceneDescription() {
		return "An empty scene. Not much to do here.";
	}

	public String getDemoName() {
		return "Empty Scene";
	}
	
	public String toString(){
		return getDemoName() + " - " + getSceneDescription();
	}
	
	public String getId(){
		return getDemoName();
	}

}

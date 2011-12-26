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

package es.eucm.eadventure.common.impl.importer.subimporters.chapter.scene.elements;

import java.awt.Dimension;

import com.google.inject.Inject;

import es.eucm.eadventure.common.EAdElementImporter;
import es.eucm.eadventure.common.data.chapter.ElementReference;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.elements.Item;
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.impl.importer.interfaces.EAdElementFactory;
import es.eucm.eadventure.common.impl.importer.interfaces.ResourceImporter;
import es.eucm.eadventure.common.interfaces.features.enums.Orientation;
import es.eucm.eadventure.common.model.elements.EAdCondition;
import es.eucm.eadventure.common.model.elements.conditions.EmptyCond;
import es.eucm.eadventure.common.model.elements.scene.EAdSceneElement;
import es.eucm.eadventure.common.model.elements.scenes.SceneElementDefImpl;
import es.eucm.eadventure.common.model.elements.scenes.SceneElementImpl;
import es.eucm.eadventure.common.resources.StringHandler;
import es.eucm.eadventure.common.util.EAdPosition;
import es.eucm.eadventure.common.util.EAdRectangle;

/**
 * Elements reference importer
 * 
 */
public class ElementReferenceImporter extends ElementImporter<ElementReference> {

	private ResourceImporter resourceImporter;

	@Inject
	public ElementReferenceImporter(EAdElementFactory factory,
			EAdElementImporter<Conditions, EAdCondition> conditionsImporter,
			StringHandler stringHandler, ResourceImporter resourceImporter) {
		super(factory, conditionsImporter, stringHandler);
		this.resourceImporter = resourceImporter;
	}

	public EAdSceneElement init(ElementReference oldObject) {
		SceneElementImpl newRef = new SceneElementImpl();
		return newRef;
	}

	@Override
	public EAdSceneElement convert(ElementReference oldObject, Object object) {

		SceneElementDefImpl actor = (SceneElementDefImpl) factory
				.getElementById(oldObject.getTargetId());
		SceneElementImpl newRef = (SceneElementImpl) object;
		newRef.setId(oldObject.getTargetId() + "_ref");

		newRef.setPosition(new EAdPosition(
				EAdPosition.Corner.BOTTOM_CENTER, oldObject.getX(),
				oldObject.getY()));
		newRef.setScale(oldObject.getScale());
		newRef.setInitialOrientation(Orientation.S);
		newRef.setDefinition(actor);

		if (factory.getCurrentOldChapterModel().getAtrezzo(
				oldObject.getTargetId()) == null) {

			if (!factory.isFirstPerson()) {
				// add influence area
				String imageUri = getImageUri(oldObject.getTargetId());
				Dimension d = resourceImporter.getDimensions(imageUri);
				int width = (int) d.getWidth();
				int height = (int) d.getHeight();
				EAdPosition p = new EAdPosition(oldObject.getX(),
						oldObject.getY(), 0.5f, 1.0f);
				float scale = oldObject.getScale();
				EAdRectangle bounds = new EAdRectangle(p.getJavaX(width
						* scale), p.getJavaY(height * scale),
						(int) (width * scale), (int) (height * scale));
				super.addInfluenceArea(newRef, bounds,
						oldObject.getInfluenceArea());
			}

			// add description
			super.addDefaultBehavior(newRef, newRef.getDefinition().getDesc());

			// add enable
			super.addEnableEvent(newRef,
					super.getEnableCondition(oldObject.getConditions()));

			// add dragable
			if (factory.isDraggableActor(actor)) {
				newRef.setDragCond(EmptyCond.TRUE_EMPTY_CONDITION);
				newRef.setVarInitialValue(
						SceneElementImpl.VAR_RETURN_WHEN_DRAGGED,
						isReturnWhenDragged(oldObject.getTargetId()));

			}
		} else {
			newRef.setVarInitialValue(SceneElementImpl.VAR_ENABLE,
					Boolean.FALSE);
		}

		return newRef;
	}

	private boolean isReturnWhenDragged(String targetId) {
		Item i = factory.getCurrentOldChapterModel().getItem(targetId);
		NPC npc = factory.getCurrentOldChapterModel().getCharacter(targetId);
		if (i != null) {
			return i.isReturnsWhenDragged();
		} else if (npc != null)
			return npc.isReturnsWhenDragged();

		return false;
	}

	private String getImageUri(String targetId) {
		Item i = factory.getCurrentOldChapterModel().getItem(targetId);
		NPC npc = factory.getCurrentOldChapterModel().getCharacter(targetId);
		if (i != null) {
			return i.getResources().get(0)
					.getAssetPath(Item.RESOURCE_TYPE_IMAGE);
		} else if (npc != null) {
			return npc.getResources().get(0)
					.getAssetPath(NPC.RESOURCE_TYPE_STAND_DOWN);
		}
		return null;
	}
}

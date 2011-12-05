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

package es.eucm.eadventure.common.model.effects.impl;

import es.eucm.eadventure.common.interfaces.Element;
import es.eucm.eadventure.common.interfaces.Param;
import es.eucm.eadventure.common.model.effects.impl.enums.InventoryEffectAction;
import es.eucm.eadventure.common.model.elements.EAdSceneElementDef;

@Element(detailed = EAdInventoryEffect.class, runtime = EAdInventoryEffect.class)
public class EAdInventoryEffect extends AbstractEAdEffect {

	@Param("modification")
	private InventoryEffectAction modification;

	@Param("sceneElement")
	private EAdSceneElementDef actor;

	@Param("boolean")
	private boolean removeFromScene;

	public EAdInventoryEffect() {
		this(null, InventoryEffectAction.ADD_TO_INVENTORY);
	}

	public EAdInventoryEffect(EAdSceneElementDef actor,
			InventoryEffectAction modification) {
		this(actor, modification, true);
	}

	public EAdInventoryEffect(EAdSceneElementDef actor,
			InventoryEffectAction modification, boolean removeFromScene) {
		super();
		this.modification = modification;
		this.actor = actor;
		this.removeFromScene = removeFromScene;
	}

	public void setModification(InventoryEffectAction modification) {
		this.modification = modification;
	}

	public InventoryEffectAction getModification() {
		return modification;
	}

	public void setSceneElement(EAdSceneElementDef actor) {
		this.actor = actor;
	}

	public EAdSceneElementDef getElement() {
		return actor;
	}

	public boolean isRemoveFromScene() {
		return removeFromScene;
	}

}

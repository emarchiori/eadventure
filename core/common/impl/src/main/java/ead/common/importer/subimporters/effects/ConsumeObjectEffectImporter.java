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

package ead.common.importer.subimporters.effects;

import com.google.inject.Inject;

import ead.common.EAdElementImporter;
import ead.common.importer.interfaces.EAdElementFactory;
import ead.common.model.elements.EAdCondition;
import ead.common.model.elements.EAdEffect;
import ead.common.model.elements.effects.ModifyInventoryEf;
import ead.common.model.elements.effects.enums.InventoryEffectAction;
import ead.common.model.elements.scene.EAdSceneElementDef;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.effects.ConsumeObjectEffect;

public class ConsumeObjectEffectImporter extends EffectImporter<ConsumeObjectEffect, EAdEffect>{
	
	private EAdElementFactory factory;
	
	@Inject
	public ConsumeObjectEffectImporter(
			EAdElementImporter<Conditions, EAdCondition> conditionImporter,
			EAdElementFactory factory) {
		super(conditionImporter);
		this.factory = factory;
	}

	@Override
	public EAdEffect init(ConsumeObjectEffect oldObject) {
		EAdEffect effect = new ModifyInventoryEf();
		effect.setId("consumeObject" + oldObject.getTargetId());
		return effect;
	}

	@Override
	public EAdEffect convert(ConsumeObjectEffect oldObject, Object object) {
		ModifyInventoryEf effect = (ModifyInventoryEf) object;
		
		importConditions(oldObject, effect);

		effect.setModification(InventoryEffectAction.REMOVE_FROM_INVENTORY);
		effect.setSceneElementDef((EAdSceneElementDef) factory.getElementById(oldObject.getTargetId()));

		return effect;
	}

}

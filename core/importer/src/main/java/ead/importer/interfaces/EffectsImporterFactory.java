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

package ead.importer.interfaces;

import ead.common.model.elements.EAdEffect;
import ead.common.model.elements.effects.EffectsMacro;
import ead.importer.EAdElementImporter;
import ead.tools.Factory;
import es.eucm.eadventure.common.data.chapter.effects.Effect;
import es.eucm.eadventure.common.data.chapter.effects.Effects;

/**
 * Factory for effects importers
 * 
 */
public interface EffectsImporterFactory extends
		Factory<Class<? extends EAdElementImporter<?, ?>>> {

	EAdElementImporter<?, ?> getImporter(Class<?> clazz);

	EAdEffect getEffect(Effect effect);

	/**
	 * Returns a macro with all the converted
	 * effects passed in the parameter
	 * 
	 * @param effect
	 *            old effects
	 * @return the macro; {@code null} if effect was empty.
	 */
	EffectsMacro getMacroEffects(Effects effect);

}

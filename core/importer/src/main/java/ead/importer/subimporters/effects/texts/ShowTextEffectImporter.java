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

package ead.importer.subimporters.effects.texts;

import com.google.inject.Inject;

import ead.common.model.elements.EAdCondition;
import ead.common.model.elements.effects.text.SpeakEf;
import ead.common.model.elements.variables.EAdOperation;
import ead.common.params.fills.ColorFill;
import ead.common.params.fills.Paint;
import ead.importer.EAdElementImporter;
import ead.importer.annotation.ImportAnnotator;
import ead.importer.interfaces.EAdElementFactory;
import ead.importer.interfaces.ResourceImporter;
import ead.tools.StringHandler;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.effects.ShowTextEffect;

public class ShowTextEffectImporter extends TextEffectImporter<ShowTextEffect> {

	@Inject
	public ShowTextEffectImporter(StringHandler stringHandler,
			EAdElementImporter<Conditions, EAdCondition> conditionImporter,
            EAdElementFactory factory, ImportAnnotator annotator, ResourceImporter resourceImporter) {
		super(stringHandler, conditionImporter, factory, annotator, resourceImporter);
	}

	@Override
	public SpeakEf convert(ShowTextEffect oldObject, Object object) {
		SpeakEf showText = super.convert(oldObject, object);
		addSound(oldObject.getAudioPath(), showText);

		for ( EAdOperation op: TextEffectImporter.getOperations(oldObject.getText(), factory)){
			showText.getCaption().getFields().add(op);
		}		
		
		
		String line = TextEffectImporter.translateLine(oldObject.getText());
		stringHandler.setString(showText.getString(), line);

		ColorFill center = new ColorFill(Integer.toHexString(oldObject
				.getRgbFrontColor()) + "ff");
		ColorFill border = new ColorFill(Integer.toHexString(oldObject
				.getRgbBorderColor()) + "ff");
		showText.setColor(new Paint(center, border), Paint.TRANSPARENT);				

		return showText;
	}

}

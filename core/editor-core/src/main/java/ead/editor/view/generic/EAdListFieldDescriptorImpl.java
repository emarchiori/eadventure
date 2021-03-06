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

package ead.editor.view.generic;

import ead.common.model.elements.extra.EAdList;
import ead.editor.view.generic.EAdListFieldDescriptor;
import ead.editor.view.generic.Panel;

/**
 * Generic implementation of {@link EAdListFieldDescriptor}
 *
 * @param <S>
 */
public class EAdListFieldDescriptorImpl<S> extends FieldDescriptorImpl<EAdList<S>> implements EAdListFieldDescriptor<S> {

	private EAdList<S> list;

	/**
	 * @param element
	 *            The element where the value is stored
	 * @param fieldName
	 *            The name of the field
	 * @param list
	 */
	public EAdListFieldDescriptorImpl(Object element, String fieldName, EAdList<S> list) {
		super(element, fieldName);
		this.list = list;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.eucm.eadventure.editor.view.generics.FieldDescriptor#getElement()
	 */
	@Override
	public Object getElement() {
		return element;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.eucm.eadventure.editor.view.generics.FieldDescriptor#getFieldName()
	 */
	@Override
	public String getFieldName() {
		return fieldName;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public S getElementAt(int pos) {
		return list.get(pos);
	}

	@Override
	public Panel getPanel(int pos, boolean selected) {
		return null;
	}

	@Override
	public EAdList<S> getList() {
		return list;
	}

}

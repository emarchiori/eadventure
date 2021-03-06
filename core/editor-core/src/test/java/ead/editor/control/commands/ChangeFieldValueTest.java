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

package ead.editor.control.commands;

import org.junit.Test;

import ead.editor.control.commands.ChangeFieldValueCommand;
import ead.editor.view.generic.FieldDescriptor;
import ead.editor.view.generic.FieldDescriptorImpl;

import junit.framework.TestCase;

public class ChangeFieldValueTest extends TestCase {

	FieldDescriptor<Boolean> fieldDescriptor;

	TestClass testElement;


	@Override
	public void setUp() {
		testElement = new TestClass();
		fieldDescriptor = new FieldDescriptorImpl<Boolean>(testElement, "value");
	}

	@Test
	public void testPerformAndUndoFailCommand() {
//		assert(!testElement.getValue());
//		ChangeFieldValueCommand<Boolean> command = new ChangeFieldValueCommand<Boolean>(Boolean.TRUE, fieldDescriptor);
//		command.performCommand();
//		assert(testElement.getValue());
//		command.undoCommand();
//		assert(!testElement.getValue());
	}

	public static class TestClass {

		private Boolean value;

		public void setValue(Boolean value) {
			this.value = value;
		}

		public Boolean getValue() {
			return value;
		}
	}
}

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

package ead.engine.core.game;

import java.util.ArrayList;
import java.util.Map;

import ead.common.model.EAdElement;
import ead.common.model.elements.variables.EAdField;
import ead.common.model.elements.variables.EAdOperation;
import ead.common.model.elements.variables.EAdVarDef;
import ead.engine.core.operators.OperatorFactory;

/**
 * The value map interfaces allows for the definition of key-value pairs of
 * different classes.
 */
public interface ValueMap {

	/**
	 * Sets the operator factory used for this value amp
	 * 
	 * @param operatorFactory
	 *            the operator factory
	 */
	void setOperatorFactory(OperatorFactory operatorFactory);

	// Sets

	/**
	 * Sets the field to given value
	 * 
	 * @param field
	 *            the field
	 * @param value
	 *            the value to the field
	 */
	void setValue(EAdField<?> field, Object value);

	/**
	 * Sets the value a variable in a element
	 * 
	 * @param element
	 *            the element
	 * @param varDef
	 *            the var definition
	 * @param value
	 *            the value for the variable
	 */
	void setValue(EAdElement element, EAdVarDef<?> varDef, Object value);

	/**
	 * Sets the variable to the result value of the operation
	 * 
	 * @param var
	 * @param operation
	 */
	void setValue(EAdField<?> var, EAdOperation operation);

	/**
	 * Sets the variable value for the given element
	 * 
	 * @param element
	 *            the element holding the variable. If the element is
	 *            {@code null}, it's considered that the variable belongs to the
	 *            system
	 * @param var
	 *            the variable definition
	 * @param operation
	 *            the operation whose result will be assigned to the variable
	 */
	void setValue(EAdElement element, EAdVarDef<?> var, EAdOperation operation);

	// Gets

	/**
	 * Returns the value of the field
	 * 
	 * @param <S>
	 * @param field
	 *            the field to be consulted
	 * @return the value of the field
	 */
	<S> S getValue(EAdField<S> field);

	/**
	 * Returns the value of the variable in the given element
	 * 
	 * @param element
	 *            the element. If the element is {@code null}, is considered as
	 *            a system variable
	 * @param varDef
	 *            the variable definition to be consulted
	 * @return the variable's value
	 */
	<S> S getValue(EAdElement element, EAdVarDef<S> varDef);

	/**
	 * Returns the variables associated to an element, whose values are
	 * different from the defaults
	 * 
	 * @param element
	 *            the element
	 * @return a map with the variables
	 */
	Map<EAdVarDef<?>, Object> getElementVars(EAdElement element);

	/**
	 * Removes all fields associated to the given element
	 * 
	 * @param element
	 *            the element
	 */
	void remove(EAdElement element);

	/**
	 * Returns the final element associated to the given element. It could be
	 * the element itself, but if the element is a field (with type
	 * {@link EAdElement}), the element pointed by the field will be returned,
	 * 
	 * @param element
	 *            the element
	 * @return the final element pointed by the element
	 */
	EAdElement getFinalElement(EAdElement element);

	/**
	 * Checks if the value map contains updated variables' values for the given
	 * element. If it does, true is returned, and the element checking for
	 * updates should read the variables he is interested in. The element is
	 * deleted for the update list of the value map until another of its fields
	 * is updated
	 * 
	 * @param element
	 *            the element
	 * @return if any element's field has been updated since last check
	 */
	boolean checkForUpdates(EAdElement element);

	/**
	 * Sets if the updates list is enable and it is recording all fields changes
	 * 
	 * @param enable
	 *            if it's enable or not
	 * 
	 */
	void setUpdateListEnable(boolean enable);

	/**
	 * Clears the update list
	 */
	void clearUpdateList();

	Map<EAdVarDef<?>, Object> getSystemVars();

	void setSystemVars(Map<EAdVarDef<?>, Object> systemVars);

	Map<EAdElement, Map<EAdVarDef<?>, Object>> getElementVars();

	void setElementVars(Map<EAdElement, Map<EAdVarDef<?>, Object>> map);

	ArrayList<EAdElement> getUpdateList();

	void setUpdateList(ArrayList<EAdElement> updateList);

}

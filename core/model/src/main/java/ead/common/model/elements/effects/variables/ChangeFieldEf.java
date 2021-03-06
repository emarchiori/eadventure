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

package ead.common.model.elements.effects.variables;

import ead.common.interfaces.Element;
import ead.common.interfaces.Param;
import ead.common.model.elements.effects.AbstractEffect;
import ead.common.model.elements.extra.EAdList;
import ead.common.model.elements.extra.EAdListImpl;
import ead.common.model.elements.variables.EAdField;
import ead.common.model.elements.variables.EAdOperation;
import ead.common.model.elements.variables.EAdVarDef;

/**
 * Effect for changing a field value
 * 
 */
@Element
public class ChangeFieldEf extends AbstractEffect {

	/**
	 * Fields to be changed
	 */
	@Param("fields")
	private EAdList<EAdField<?>> fields;

	@Param("varDef")
	private EAdVarDef<?> varDef;

	/**
	 * Operation to be done. The result of this operation should be assigned to
	 * the variable
	 */
	@Param("operation")
	private EAdOperation operation;

	/**
	 * Creates an empty effect
	 * 
	 * @param id
	 *            Elements's id
	 */
	public ChangeFieldEf() {
		this(null, null);
	}

	/**
	 * Creates an effect with the required parameters
	 * 
	 * @param id
	 *            Elements id
	 * @param var
	 *            The field to be changed
	 * @param operation
	 *            The operation to be performed to obtain the value of the field
	 */
	public ChangeFieldEf( EAdField<?> field,
			EAdOperation operation) {
		super();
		setId("changeField");
		this.fields = new EAdListImpl<EAdField<?>>(EAdField.class);
		if (field != null)
			fields.add(field);
		this.operation = operation;
	}

	/**
	 * Adds a field to be updated with the operation result
	 * 
	 * @param var
	 *            the variable
	 */
	public void addField(EAdField<?> var) {
		fields.add(var);
	}

	/**
	 * Sets the operation to be done by this effect. The result of this
	 * operation should be assigned to the fields contained by the effect
	 * 
	 * @param operation
	 *            the operation
	 */
	public void setOperation(EAdOperation operation) {
		this.operation = operation;
	}

	/**
	 * Returns a list of the fields to be updated with the operation result
	 * 
	 * @return a list of the fields to be updated with the operation result
	 */
	public EAdList<EAdField<?>> getFields() {
		return fields;
	}

	/**
	 * Returns the operation to be done by this effect
	 * 
	 * @return the operation to be done by this effect
	 */
	public EAdOperation getOperation() {
		return operation;
	}

	/**
	 * Sets the variable to change in the parent of the effect
	 * 
	 * @param varDef
	 *            the variable definition
	 */
	public void setParentVar(EAdVarDef<?> varDef) {
		this.varDef = varDef;
	}

	public EAdVarDef<?> getParentVar() {
		return varDef;
	}

	public String toString() {
		return fields + " : " + operation;
	}

	public void setFields(EAdList<EAdField<?>> fields) {
		this.fields = fields;
	}

	public void setVarDef(EAdVarDef<?> varDef) {
		this.varDef = varDef;
	}

	public EAdVarDef<?> getVarDef() {
		return varDef;
	}
	
	
	
}

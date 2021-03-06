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

package ead.common.util;

import com.gwtent.reflection.client.Reflectable;

/**
 * Generic Java matrix definition, used for transformations
 */
@Reflectable
public interface EAdMatrix {

	/**
	 * [0 3 6] [1 4 7] [2 5 8]
	 * 
	 * @return the matrix itself
	 */
	float[] getFlatMatrix();

	/**
	 * @return a transposed version of the values in the matrix
	 */
	float[] getTransposedMatrix();

	/**
	 * @return the inverse matrix
	 */
	float[] getInversedMatrix();

	void translate(float x, float y, boolean post);

	void rotate(float angle, boolean post);

	void scale(float scaleX, float scaleY, boolean post);

	void multiply(float m[], boolean post);

	void setIdentity();

	float[] multiplyPoint(float x, float y, boolean post);

	float[] multiplyPointInverse(float x, float y, boolean post);

	boolean isValidated();

	void setValidated(boolean validated);

	void setValues(float[] values);

}

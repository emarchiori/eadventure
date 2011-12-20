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

package es.eucm.eadventure.engine.core.util.impl;

import es.eucm.eadventure.common.util.EAdMatrix;
import es.eucm.eadventure.common.util.EAdTransformation;
import es.eucm.eadventure.common.util.impl.EAdMatrixImpl;

public class EAdTransformationImpl implements EAdTransformation {

	public static final EAdTransformation INITIAL_TRANSFORMATION = new EAdTransformationImpl();

	private EAdMatrix matrix;

	private boolean visible;

	private float alpha;

	public EAdTransformationImpl(EAdMatrix matrix, boolean visible, float alpha) {
		this.matrix = matrix;
		this.visible = visible;
		this.alpha = alpha;
	}

	public EAdTransformationImpl() {
		matrix = new EAdMatrixImpl();
		visible = true;
		alpha = 1.0f;
	}

	@Override
	public EAdMatrix getMatrix() {
		return matrix;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public float getAlpha() {
		return alpha;
	}
	
	public void setAlpha(float alpha){
		this.alpha = alpha;
	}
	
	public void setVisible( boolean visible ){
		this.visible = visible;
	}
	
	public Object clone(){
		EAdTransformationImpl t = new EAdTransformationImpl();
		t.alpha = alpha;
		t.visible = visible;
		t.matrix = new EAdMatrixImpl(matrix.getFlatMatrix());
		return t;
		
	}
	

}

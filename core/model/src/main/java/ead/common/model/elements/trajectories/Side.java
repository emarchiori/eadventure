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

package ead.common.model.elements.trajectories;

import ead.common.interfaces.Element;
import ead.common.interfaces.Param;
import ead.common.model.EAdElement;
import ead.common.model.elements.BasicElement;

@Element
public class Side extends BasicElement {

	@Param("idStart")
	private String idStart;

	@Param("idEnd")
	private String idEnd;

	@Param("length")
	private float length = 1;

	@Param("realLength")
	private float realLength = 1;

	public Side() {

	}

	public Side(String idStart, String idEnd) {

		this.idStart = idStart;
		this.idEnd = idEnd;
	}

	public void setRealLength(float realLength) {
		this.realLength = realLength;
	}

	public String getIdStart() {

		return idStart;
	}

	public String getIdEnd() {

		return idEnd;
	}

	public void setIdStart(String idStart) {
		this.idStart = idStart;
	}

	public void setIdEnd(String idEnd) {
		this.idEnd = idEnd;
	}

	public void setLength(float length) {

		this.length = length;
	}

	@Override
	public boolean equals(Object o) {

		if (o == null)
			return false;
		if (o instanceof Side) {
			Side side = (Side) o;
			if (side.idEnd.equals(this.idEnd)
					&& side.idStart.equals(this.idStart))
				return true;
		}
		return false;
	}

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.idStart != null ? this.idStart.hashCode() : 0);
        hash = 41 * hash + (this.idEnd != null ? this.idEnd.hashCode() : 0);
        return hash;
    }

	public float getLength() {

		return length;
	}

	public EAdElement copy() {

		Side s = new Side(null, null);
		s.idEnd = (idEnd != null ? new String(idEnd) : null);
		s.idStart = (idStart != null ? new String(idStart) : null);
		s.length = length;
		return s;
	}

	public float getRealLength() {
		return realLength;
	}
}

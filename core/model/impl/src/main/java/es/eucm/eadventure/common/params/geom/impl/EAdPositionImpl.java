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

package es.eucm.eadventure.common.params.geom.impl;

import es.eucm.eadventure.common.params.geom.EAdPosition;

/**
 * <p>
 * This class represents a position in the eAdventure model
 * </p>
 * <p>
 * Positions in the eAdventure model are not only represented by there (x, y)
 * coordinates but by the displacement of the center of the element
 * <p>
 * <p>
 * For instance, if the object A is positioned at (2, 2) in a TOP_LEFT corner,
 * and the object B is positioned at (7, 4) in a BOTTOM_CENTER corner:
 * </p>
 * <code>
 * + 0 1 2 3 4 5 6 7 8 9<br>
 * 0 . . . . . . . . . .<br>
 * 1 . . . . . . . . . .<br>
 * 2 . . + - - . - - - .<br>
 * 3 . . | A | . | B | .<br>
 * 4 . . - - - . - + - .<br>
 * 5 . . . . . . . . . .<br>
 * </code>
 */
public class EAdPositionImpl implements EAdPosition {

	public static enum Corner {
		TOP_LEFT, BOTTOM_LEFT, BOTTOM_CENTER, CENTER, TOP_RIGHT
	}

	private int x;

	private int y;

	private float dispX;

	private float dispY;

	public EAdPositionImpl(Corner corner, int x, int y) {
		this(x, y, 0f, 0f);
		setCorner(corner);
	}

	/**
	 * Constructs a position with the given coordinates, and its corner set to
	 * {@link Corner#TOP_LEFT}
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordiante
	 */
	public EAdPositionImpl(int x, int y) {
		this(Corner.TOP_LEFT, x, y);
	}

	/**
	 * Constructs a position with the given coordinates and the given
	 * displacement. The displacement is used to calculate the value
	 * {@link EAdPositionImpl#getJavaX(float)} and
	 * {@link EAdPositionImpl#getJavaY(float)}.
	 * 
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param dispX
	 *            displacement in the x axis
	 * @param dispY
	 *            displacement in the y axis
	 */
	public EAdPositionImpl(int x, int y, float dispX, float dispY) {
		this.x = x;
		this.y = y;
		this.dispX = dispX;
		this.dispY = dispY;
	}

	/**
	 * Creates a position copying the given one
	 * 
	 * @param position
	 *            the position to copy
	 */
	public EAdPositionImpl(EAdPositionImpl position) {
		this(position.x, position.y, position.dispX, position.dispY);
	}
	
	public EAdPositionImpl( String data ){
		parse( data );
	}

	private void setCorner(Corner corner) {
		switch (corner) {
		case TOP_LEFT:
			dispX = 0f;
			dispY = 0f;
			break;
		case TOP_RIGHT:
			dispX = 1f;
			dispY = 0f;
			break;
		case BOTTOM_LEFT:
			dispX = 0f;
			dispY = 1f;
			break;
		case BOTTOM_CENTER:
			dispX = 0.5f;
			dispY = 1f;
			break;
		case CENTER:
			dispX = 0.5f;
			dispY = 0.5f;
			break;
		}
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public float getDispX() {
		return dispX;
	}

	/**
	 * Sets the displacement in the x axis
	 * 
	 * @param dispX
	 *            the displacement
	 */
	public void setDispX(float dispX) {
		this.dispX = dispX;
	}

	@Override
	public float getDispY() {
		return dispY;
	}

	/**
	 * Sets the displacement in the x axis
	 * 
	 * @param dispX
	 *            the displacement
	 */
	public void setDespY(float despY) {
		this.dispY = despY;
	}

	@Override
	public int getJavaX(float width) {
		return (int) (x - dispX * width);
	}

	@Override
	public int getJavaY(float height) {
		return (int) (y - dispY * height);
	}

	/**
	 * Adds the given coordinates to this position
	 * 
	 * @param x
	 *            value to be added to the x coordinate
	 * @param y
	 *            value to be added to the y coordinate
	 */
	public void add(int x, int y) {
		this.x += x;
		this.y += y;
	}

	@Override
	public String toString() {
		return toStringData();
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof EAdPositionImpl) {
			EAdPositionImpl temp = (EAdPositionImpl) o;
			if (temp.x == x && temp.y == y && temp.dispX == dispX
					&& temp.dispY == dispY)
				return true;
		}
		return false;
	}

	private static EAdPositionImpl volatileEAdPosition = new EAdPositionImpl(0, 0);

	/**
	 * Returns a static position, with the corner set to {@link Corner#TOP_LEFT}
	 * , that can be used to save memory in some calculations
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @return the static position
	 */
	public static EAdPositionImpl volatileEAdPosition(int x, int y) {
		return volatileEAdPosition(Corner.TOP_LEFT, x, y);
	}

	/**
	 * Returns a static position that can be used to save memory in some
	 * calculations
	 * 
	 * @param corner
	 *            the position corner
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * 
	 * @return the static position
	 */
	public static EAdPositionImpl volatileEAdPosition(Corner corner, int x, int y) {
		volatileEAdPosition.x = x;
		volatileEAdPosition.y = y;
		volatileEAdPosition.setCorner(corner);
		return volatileEAdPosition;
	}

	/**
	 * Returns a static position that can be used to save memory in some
	 * calculations
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param dispX
	 *            displacement in the x axis
	 * @param dispY
	 *            displacement in the y axis
	 * @return the static position
	 */
	public static EAdPositionImpl volatileEAdPosition(int x, int y, float dispX,
			float dispY) {
		volatileEAdPosition.x = x;
		volatileEAdPosition.y = y;
		volatileEAdPosition.dispX = dispX;
		volatileEAdPosition.dispY = dispY;
		return volatileEAdPosition;
	}

	@Override
	public String toStringData() {
		return x + ":" + y + ":" + dispX + ":" + dispY;
	}

	@Override
	public void parse(String data) {
		String temp[] = data.split(":");
		x = Integer.parseInt(temp[0]);
		y = Integer.parseInt(temp[1]);
		dispX = Float.parseFloat(temp[2]);
		dispY = Float.parseFloat(temp[3]);
	}

	@Override
	public void set(int x, int y) {
		setX( x );
		setY( y );
		
	}

}

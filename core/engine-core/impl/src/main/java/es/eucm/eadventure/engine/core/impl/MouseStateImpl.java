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

package es.eucm.eadventure.engine.core.impl;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import es.eucm.eadventure.engine.core.MouseState;
import es.eucm.eadventure.engine.core.gameobjects.DrawableGO;
import es.eucm.eadventure.engine.core.guiactions.MouseAction;

@Singleton
public class MouseStateImpl implements MouseState {

	public static final int OUT_VAL = -1;
	
	private int mouseX = OUT_VAL;
	
	private int mouseY = OUT_VAL;

	private boolean mousePressed = false;

	private Queue<MouseAction> mouseEvents;
	
	private DrawableGO<?> gameObjectUnderMouse;

	private DrawableGO<?> draggingGameObject;
	
	private int dragInitX = OUT_VAL;
	
	private int dragInitY = OUT_VAL;
	
	@Inject
	public MouseStateImpl() {
		mouseEvents = new ConcurrentLinkedQueue<MouseAction>();
	}
	
	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void setMousePosition(int mouseX, int mouseY) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

	public Queue<MouseAction> getMouseEvents() {
		return mouseEvents;
	}

	public DrawableGO<?> getGameObjectUnderMouse() {
		return gameObjectUnderMouse;
	}

	public void setGameObjectUnderMouse(DrawableGO<?> gameObjectUnderMouse) {
		this.gameObjectUnderMouse = gameObjectUnderMouse;
	}

	public DrawableGO<?> getDraggingGameObject() {
		return draggingGameObject;
	}

	@Override
	public void setDraggingGameObject(DrawableGO<?> draggingGameObject) {
		this.draggingGameObject = draggingGameObject;
		if (draggingGameObject != null) {
			this.dragInitX = mouseX;
			this.dragInitY = mouseY;
		}
	}

	@Override
	public int getDragDifX() {
		return mouseX - dragInitX;
	}
	
	@Override
	public int getDragDifY() {
		return mouseY - dragInitY;
	}
	
	public boolean isInside() {
		if (mouseX == OUT_VAL && mouseY == OUT_VAL)
			return false;
		return true;
	}
	
}

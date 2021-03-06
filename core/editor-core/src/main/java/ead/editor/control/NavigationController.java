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

package ead.editor.control;

import ead.editor.control.change.ChangeNotifier;

/**
 * Interface for view navigation (going to previously visited screens).
 * The implementation should register itself with whatever actually creates the
 * screens, so that it will be notified when views are opened or visited.
 */
public interface NavigationController extends ChangeNotifier {

	/**
	 * Go forward in the screen history
	 */
	void goForward();
	
	/**
	 * Go back in the screen history
	 */
	void goBackward();
	
	/**
	 * @return true if there are is a screen forward in the history
	 */
	boolean canGoForward();
	
	/**
	 * @return true if there are is a screen backward in the history 
	 */
	boolean canGoBackward();
	
	/**
	 * Clear screen history
	 */
	void clearHistory();

	/**
	 * Set the actual super-controller.
	 * @param controller the main controller, providing access to model, views,
	 * and more
	 */
	void setController(Controller controller);
}

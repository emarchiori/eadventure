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

package ead.common.model.weev.story;

import ead.common.model.elements.extra.EAdList;
import ead.common.model.weev.WEEVElement;
import ead.common.model.weev.story.elements.Comment;
import ead.common.model.weev.story.elements.Node;
import ead.common.model.weev.story.elements.StoryTransition;
import ead.common.model.weev.story.section.StorySection;

/**
 * The story representation of the {@link WEEVModel}
 * <p>
 * The story consists of the {@link Node}s, the {@link StoryTransition}s between them
 * and other elements, such as {@link Comments} that make up the story
 * representation of a game
 */
public interface Story extends WEEVElement {

	/**
	 * @return the list of {@link Node}s that make up the story
	 */
	EAdList<Node> getNodes();

	/**
	 * @return the initial {@link Node} in the story
	 */
	Node getInitialNode();

	/**
	 * @return the list of {@link StoryTransition}s that make up the story
	 */
	EAdList<StoryTransition> getTransitions();

	/**
	 * @return the list of {@link Comment}s in the story
	 */
	EAdList<Comment> getComments();

	/**
	 * @return the list of {@link StorySection}s in the story
	 */
	EAdList<StorySection> getStorySections();

}

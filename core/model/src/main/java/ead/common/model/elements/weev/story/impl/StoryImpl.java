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

package ead.common.model.elements.weev.story.impl;

import ead.common.interfaces.Element;
import ead.common.interfaces.Param;
import ead.common.model.elements.extra.EAdList;
import ead.common.model.elements.extra.EAdListImpl;
import ead.common.model.elements.weev.impl.AbstractWEEVElement;
import ead.common.model.weev.story.Story;
import ead.common.model.weev.story.elements.Comment;
import ead.common.model.weev.story.elements.Node;
import ead.common.model.weev.story.elements.StoryTransition;
import ead.common.model.weev.story.section.StorySection;

/**
 * Default {@link Story} implementation
 */
@Element
public class StoryImpl extends AbstractWEEVElement implements Story {

	EAdList<Node> nodes;
	
	EAdList<StoryTransition> transitions;
	
	EAdList<Comment> comments;
	
	EAdList<StorySection> storySections;
	
	@Param(value = "initialNode")
	Node initialNode;
	
	public StoryImpl() {
		nodes = new EAdListImpl<Node>(Node.class);
		transitions = new EAdListImpl<StoryTransition>(StoryTransition.class);
		comments = new EAdListImpl<Comment>(Comment.class);
		storySections = new EAdListImpl<StorySection>(StorySection.class);
	}
	
	@Override
	public EAdList<Node> getNodes() {
		return nodes;
	}

	@Override
	public Node getInitialNode() {
		return initialNode;
	}
	
	public void setInitialNode(Node initialNode) {
		this.initialNode = initialNode;
	}

	@Override
	public EAdList<StoryTransition> getTransitions() {
		return transitions;
	}

	@Override
	public EAdList<Comment> getComments() {
		return comments;
	}

	@Override
	public EAdList<StorySection> getStorySections() {
		return storySections;
	}

}

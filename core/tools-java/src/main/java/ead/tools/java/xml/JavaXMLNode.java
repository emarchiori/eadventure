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

package ead.tools.java.xml;

import org.w3c.dom.Node;

import ead.tools.xml.XMLAttributes;
import ead.tools.xml.XMLNode;
import ead.tools.xml.XMLNodeList;

public class JavaXMLNode implements XMLNode {

	private Node node;

	private XMLAttributes attributes;

	private XMLNodeList childNodes;

	public JavaXMLNode(Node node) {
		this.node = node;
		attributes = new JavaXMLAttributes(node.getAttributes());
		childNodes = new JavaXMLNodeList(node.getChildNodes());
	}

	@Override
	public XMLAttributes getAttributes() {
		return attributes;
	}

	@Override
	public String getNodeText() {
		return node.getTextContent();
	}

	@Override
	public XMLNodeList getChildNodes() {
		return childNodes;
	}

	@Override
	public String getNodeName() {
		return node.getNodeName();
	}

	@Override
	public boolean hasChildNodes() {
		return node.hasChildNodes();
	}

	@Override
	public XMLNode getFirstChild() {
		if (hasChildNodes()) {
			return getChildNodes().item(0);
		} else {
			return null;
		}

	}

}

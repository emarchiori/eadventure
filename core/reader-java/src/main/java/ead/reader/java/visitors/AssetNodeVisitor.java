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

package ead.reader.java.visitors;

import java.lang.reflect.Field;

import org.w3c.dom.Node;

import ead.common.resources.assets.AssetDescriptor;
import ead.reader.adventure.DOMTags;
import ead.reader.java.extra.ObjectFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Visitor for the asset element of resources.
 * </p>
 * <p>
 * The asset element should be<br>
 * {@code <asset id="ASSET_ID" class="ASSETDESCRIPTOR_CLASS">ASSET_VALUE</asset>}
 * <br>
 * </p>
 */
public class AssetNodeVisitor extends NodeVisitor<AssetDescriptor> {

	private static final Logger logger = LoggerFactory.getLogger("AssetNodeVisitor");

	@Override
	public AssetDescriptor visit(Node node, Field field, Object parent, Class<?> listClass) {
		AssetDescriptor element =  (AssetDescriptor) ObjectFactory.getObject(node.getTextContent(), AssetDescriptor.class);
		if (element != null) {
			setValue(field, parent, element);
			return element;
		}

		String uniqueId = node.getAttributes().getNamedItem(DOMTags.UNIQUE_ID_AT).getNodeValue();

		String clazz = node.getAttributes().getNamedItem(DOMTags.CLASS_AT).getNodeValue();
		clazz = translateClass(clazz);

		Class<?> c = null;
		try {
			c = ClassLoader.getSystemClassLoader().loadClass(clazz);
			element = (AssetDescriptor) c.newInstance();
		} catch (Exception e) {
			logger.error("loading class for {}", clazz, e);
			error = true;
		}

		if (element != null)
			ObjectFactory.addAsset(uniqueId, element);
		setValue(field, parent, element);

		readFields(element, node);

		return element;
	}




	@Override
	public String getNodeType() {
		return DOMTags.ASSET_AT;
	}

}

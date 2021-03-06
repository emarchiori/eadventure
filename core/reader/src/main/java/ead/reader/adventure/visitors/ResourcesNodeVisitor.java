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

package ead.reader.adventure.visitors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ead.common.resources.EAdBundleId;
import ead.common.resources.EAdResources;
import ead.common.resources.assets.AssetDescriptor;
import ead.reader.adventure.DOMTags;
import ead.tools.reflection.ReflectionField;
import ead.tools.xml.XMLNode;
import ead.tools.xml.XMLNodeList;

/**
 * <p>
 * Vistor for the resource element.
 * </p>
 * <p>
 * The resource element should be<br>
 * {@code <resources />} if there are no resources<br>
 * if there are resources then it should be:<br>
 * {@code <resources>}<br>
 * &nbsp;&nbsp;&nbsp;
 * {@code <asset id="ASSET_ID" class="ASSETDESCRIPTOR_CLASS">ASSET_VALUE</asset>}
 * x N<br>
 * {@code </resources>}<br>
 * and if there are bundles:<br>
 * {@code <resources> initialBundle="INITIAL_BUNDLEID"}<br>
 * &nbsp;&nbsp;&nbsp;
 * {@code   <asset id="ASSET_ID" class="ASSETDESCRIPTOR_CLASS">ASSET_VALUE</asset>}
 * x N<br>
 * &nbsp;&nbsp;&nbsp;{@code	<bundle id="BUNDLE_ID">}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * {@code   <asset id="ASSET_ID" class="ASSETDESCRIPTOR_CLASS">ASSET_VALUE</asset>}
 * x N<br>
 * &nbsp;&nbsp;&nbsp;{@code	</bundle>}<br>
 * {@code </resources>}<br>
 * 
 * </p>
 */
public class ResourcesNodeVisitor extends NodeVisitor<EAdResources> {

	protected static final Logger logger = LoggerFactory
			.getLogger("ElementNodeVisitor");

	@Override
	public void visit(XMLNode node, ReflectionField field, Object parent,
			Class<?> listClass, NodeVisitorListener listener) {
		EAdResources resources = null;
		try {
			resources = (EAdResources) field.getFieldValue(parent);

			String initialBundleId = node.getAttributes().getValue(
					DOMTags.INITIAL_BUNDLE_TAG);

			XMLNodeList nl = node.getChildNodes();

			for (int i = 0, cnt = nl.getLength(); i < cnt; i++) {
				if (nl.item(i).getNodeName().equals(DOMTags.BUNDLE_TAG)) {
					String bundleId = nl.item(i).getAttributes()
							.getValue(DOMTags.ID_AT);
					EAdBundleId id = new EAdBundleId(bundleId);
					resources.addBundle(id);
					if (bundleId.equals(initialBundleId)
							&& !id.equals(resources.getInitialBundle())) {
						EAdBundleId temp = resources.getInitialBundle();
						resources.setInitialBundle(id);
						resources.removeBundle(temp);
					}

					XMLNodeList nl2 = nl.item(i).getChildNodes();
					if (nl2 != null) {
						for (int j = 0, cnt2 = nl2.getLength(); j < cnt2; j++) {

							VisitorFactory.getVisitor(DOMTags.ASSET_AT).visit(
									nl2.item(j),
									null,
									null,
									null,
									new ResourceNodeVisitorListener(id,
											resources, nl2.item(j)
													.getAttributes()
													.getValue(DOMTags.ID_AT)));

						}
					}
				} else {
					VisitorFactory.getVisitor(DOMTags.ASSET_AT).visit(
							nl.item(i),
							null,
							null,
							null,
							new ResourceNodeVisitorListener(null, resources, nl
									.item(i).getAttributes()
									.getValue(DOMTags.ID_AT)));

				}
			}

		} catch (IllegalArgumentException e) {
			logger.error("Error visiting node {}", node.getNodeName(), e);
		} finally {

		}

		listener.elementRead(resources);
	}

	@Override
	public String getNodeType() {
		return DOMTags.RESOURCES_TAG;
	}

	public static class ResourceNodeVisitorListener implements
			NodeVisitorListener {

		private EAdBundleId bundle;

		private EAdResources resources;

		private String id;

		public ResourceNodeVisitorListener(EAdBundleId bundle,
				EAdResources resources, String id) {
			this.bundle = bundle;
			this.resources = resources;
			this.id = id;
		}

		@Override
		public void elementRead(Object element) {
			AssetDescriptor asset = (AssetDescriptor) element;
			if (bundle == null) {
				resources.addAsset(id, asset);
			} else {
				resources.addAsset(bundle, id, asset);
			}
		}

	}

}

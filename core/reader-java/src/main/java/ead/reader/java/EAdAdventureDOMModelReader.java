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

package ead.reader.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ead.common.model.elements.EAdAdventureModel;
import ead.reader.adventure.AdventureReader;
import ead.reader.adventure.ObjectFactory;
import ead.reader.java.visitors.NodeVisitor;
import ead.tools.java.reflection.JavaReflectionClassLoader;
import ead.tools.java.reflection.JavaReflectionProvider;
import ead.tools.java.xml.JavaXMLParser;
import ead.tools.reflection.ReflectionClassLoader;

/**
 * The reader for the XML representation of the model
 */
public class EAdAdventureDOMModelReader {

	private static final Logger logger = LoggerFactory.getLogger("EAdReader");

	public EAdAdventureModel read(URI fileURI) {
		try {
			File file = new File(fileURI);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			AdventureReader xmlReader = new AdventureReader(new JavaXMLParser());
			StringBuilder xml = new StringBuilder();
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					xml.append(line);
				}
				reader.close();
			} catch (IOException e) {

			}

			ReflectionClassLoader.init(new JavaReflectionClassLoader());
			ObjectFactory.init(new JavaReflectionProvider());
			EAdAdventureModel data = xmlReader.readXML(xml.toString());
			return data;
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	public EAdAdventureModel read(Reader r) {
		BufferedReader reader = new BufferedReader(r);
		AdventureReader xmlReader = new AdventureReader(new JavaXMLParser());
		StringBuilder xml = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				xml.append(line);
			}
			reader.close();
		} catch (IOException e) {

		}

		ReflectionClassLoader.init(new JavaReflectionClassLoader());
		ObjectFactory.init(new JavaReflectionProvider());
		EAdAdventureModel data = xmlReader.readXML(xml.toString());
		return data;
	}

	public EAdAdventureModel read(InputStream inputStream) {
		return read( new InputStreamReader(inputStream));
//		BasicAdventureModel data = null;
//		try {
//			ObjectFactory.initialize();
//			Document doc = DocumentBuilderFactory.newInstance()
//					.newDocumentBuilder().parse(inputStream);
//			ElementNodeVisitor env = new ElementNodeVisitor();
//			NodeVisitor.init(doc.getFirstChild().getAttributes()
//					.getNamedItem(DOMTags.PACKAGE_AT).getNodeValue());
//			getAliasMap(doc);
//			data = (BasicAdventureModel) env.visit(doc.getFirstChild()
//					.getFirstChild(), null, null, null);
//			data.getDepthControlList().clear();
//
//			return data;
//		} catch (Exception e) {
//			logger.error("Error reading model", e);
//		}
//		return null;
	}

	private void getAliasMap(Document doc) {
		NodeList nl = doc.getFirstChild().getChildNodes();

		for (int i = 0, cnt = nl.getLength(); i < cnt; i++) {
			if (nl.item(i).getNodeName().equals("keyMap")) {
				NodeList nl2 = nl.item(i).getChildNodes();

				for (int j = 0, cnt2 = nl2.getLength(); j < cnt2; j++) {
					Node n = nl2.item(j);
					NodeVisitor.aliasMap.put(
							n.getAttributes().getNamedItem("key")
									.getNodeValue(), n.getAttributes()
									.getNamedItem("value").getNodeValue());
				}

			}
		}

	}

	public void visit(Node node, int level) {
		NodeList nl = node.getChildNodes();

		for (int i = 0, cnt = nl.getLength(); i < cnt; i++) {
			System.out.println(level
					+ " ["
					+ nl.item(i).getNodeName()
					+ (nl.item(i).getNodeValue() != null ? nl.item(i)
							.getNodeValue() : "") + "]");
			visit(nl.item(i), level + 1);
		}
	}

	public EAdAdventureModel read(String fileURI) {
		try {
			File file = new File(fileURI);
			FileInputStream fileInputStream = new FileInputStream(file);
			EAdAdventureModel data = read(fileInputStream);
			return data;
		} catch (FileNotFoundException e) {
			return null;
		}
	}

}

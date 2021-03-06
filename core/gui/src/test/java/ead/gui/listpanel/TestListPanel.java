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

package ead.gui.listpanel;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ead.gui.EAdGUILookAndFeel;
import ead.gui.extra.EAdBorderLayout;
import ead.gui.listpanel.ColumnDescriptor;
import ead.gui.listpanel.ListPanel;
import ead.gui.listpanel.ListPanelListener;
import ead.gui.listpanel.columntypes.ButtonCellRendererEditor;
import ead.gui.listpanel.columntypes.ConditionsCellRendererEditor;
import ead.gui.listpanel.columntypes.StringCellRendererEditor;
import ead.gui.listpanel.columntypes.extra.TableButtonActionListener;

public class TestListPanel {

	public static void main(String args[]) {
		
		try {
			UIManager.setLookAndFeel(EAdGUILookAndFeel.getInstance());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		JFrame f = new JFrame("Test List Panel");

		// created a list elements
		ListPanelListener listPanelListener = new TestElementList();

		
		// -----------------LIST PANEL----------------------
		ListPanel listPanel = new ListPanel(listPanelListener);
		// ----------------EXAMPLE COLUMNS------------------
		// Create a column only with a name
		listPanel.addColumn(new ColumnDescriptor("Primera", "", null));
		// Create another column only with a string and the help (not
		// implemented), furthermore is editable.
		listPanel.addColumn(new ColumnDescriptor("Segunda", "prueba.html",
				new StringCellRendererEditor()));
		// Create a column of conditions
		listPanel.addColumn(new ColumnDescriptor("Tercera", "conditions.html",
				new ConditionsCellRendererEditor()));
		// Create a column of documentation
		listPanel.addColumn(new ColumnDescriptor("Cuarta", "documentation.html",
				new ButtonCellRendererEditor<TestElement>("button", new TableButtonActionListener<TestElement>() {

					@Override
					public void processClick(TestElement element) {
						System.out.println("click " + element);
					}
					
				})));

		//this initialize the panel, it's necessary
		listPanel.createElements();
		// ---------------END LIST PANEL-------------------

		// Created a JSplitPanel with the ListPanel and a EAdPanel
		JSplitPane tableWithSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				listPanel, new JPanel());
		tableWithSplit.setOneTouchExpandable(true);
		tableWithSplit.setDividerLocation(140);
		tableWithSplit.setContinuousLayout(true);
		tableWithSplit.setResizeWeight(0.5);
		tableWithSplit.setDividerSize(10);

		f.add(tableWithSplit, EAdBorderLayout.CENTER);
		f.setSize(new Dimension(800, 600));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

}

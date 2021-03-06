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

package ead.engine.android.home.local;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ead.engine.R;
import ead.engine.android.home.repository.database.GameInfo;

/**
 * An adapter to store the installed games
 * 
 * @author Roberto Tornero
 */
public class LocalGamesListAdapter  extends ArrayAdapter<GameInfo> {

	/**
	 * The list of installed games
	 */
	private ArrayList<GameInfo> items;

	/**
	 * Constructor
	 */
	public LocalGamesListAdapter(Context context, int textViewResourceId, ArrayList<GameInfo> items) {
		super(context, textViewResourceId, items);
		this.items = items;
	}

	/**
	 * The view for each list item. Contains the name of the game, its description and its icon image
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.local_games_listitem, null);
		}
		GameInfo game = items.get(position);
		if (game != null) {
			TextView tt = (TextView) v.findViewById(R.id.toptext);
			TextView bt = (TextView) v.findViewById(R.id.bottomtext);
			ImageView iv = (ImageView) v.findViewById(R.id.icon);
			if (tt != null) {
				tt.setText(game.getGameTitle());                            }
			if(bt != null){
				bt.setText(game.getGameDescription());
			}
			if(iv != null){
				if (game.getImageIcon()!=null)
					iv.setImageBitmap(game.getImageIcon());
			}

		}
		return v;
	}

}



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

package ead.engine.core.gameobjects.factories;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import ead.common.model.elements.transitions.DisplaceTransition;
import ead.common.model.elements.transitions.EAdTransition;
import ead.common.model.elements.transitions.EmptyTransition;
import ead.common.model.elements.transitions.FadeInTransition;
import ead.engine.core.gameobjects.go.transitions.TransitionGO;
import ead.engine.core.gameobjects.transitions.BasicTransitionGO;
import ead.engine.core.gameobjects.transitions.DisplaceTransitionGO;
import ead.engine.core.gameobjects.transitions.FadeInTransitionGO;
import ead.engine.core.platform.TransitionFactory;
import ead.tools.GenericInjector;

@Singleton
public class TransitionFactoryImpl implements TransitionFactory {
	
	private GenericInjector injector;
	
	@Inject
	public TransitionFactoryImpl( GenericInjector injector ){
		this.injector = injector;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends EAdTransition> TransitionGO<T> getTransition(T transition) {
		TransitionGO<T> transitionGO = null;

		if ( transition instanceof DisplaceTransition )
			transitionGO = (TransitionGO<T>) injector.getInstance(DisplaceTransitionGO.class);
		else if ( transition instanceof FadeInTransition )
			transitionGO = (TransitionGO<T>) injector.getInstance(FadeInTransitionGO.class);
		else if ( transition instanceof EmptyTransition )
			transitionGO = (TransitionGO<T>) injector.getInstance(BasicTransitionGO.class);



		if (transitionGO != null)
			transitionGO.setTransition(transition);
		return transitionGO;
	}

}

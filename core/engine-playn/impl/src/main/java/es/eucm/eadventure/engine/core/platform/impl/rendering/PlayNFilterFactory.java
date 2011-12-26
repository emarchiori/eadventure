package es.eucm.eadventure.engine.core.platform.impl.rendering;

import playn.core.Canvas;

import com.google.inject.Inject;

import es.eucm.eadventure.common.ReflectionProvider;
import es.eucm.eadventure.engine.core.platform.rendering.AbstractFilterFactory;

public class PlayNFilterFactory extends AbstractFilterFactory<Canvas>{

	@Inject
	public PlayNFilterFactory(ReflectionProvider interfacesProvider) {
		super( new PlayNFilterProvider(), interfacesProvider);
	}

}

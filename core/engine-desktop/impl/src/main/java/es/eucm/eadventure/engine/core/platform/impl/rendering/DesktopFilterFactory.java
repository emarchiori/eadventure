package es.eucm.eadventure.engine.core.platform.impl.rendering;

import java.awt.Graphics2D;

import com.google.inject.Inject;

import es.eucm.eadventure.common.ReflectionProvider;
import es.eucm.eadventure.engine.core.platform.rendering.AbstractFilterFactory;

public class DesktopFilterFactory extends AbstractFilterFactory<Graphics2D>{

	@Inject
	public DesktopFilterFactory(ReflectionProvider interfacesProvider) {
		super( new DesktopFilterProvider(), interfacesProvider);
	}

}

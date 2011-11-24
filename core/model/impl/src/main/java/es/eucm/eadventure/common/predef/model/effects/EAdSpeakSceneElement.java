package es.eucm.eadventure.common.predef.model.effects;

import es.eucm.eadventure.common.model.EAdElement;
import es.eucm.eadventure.common.model.effects.impl.text.EAdSpeakEffect;
import es.eucm.eadventure.common.model.elements.EAdSceneElement;
import es.eucm.eadventure.common.model.elements.EAdSceneElementDef;
import es.eucm.eadventure.common.model.elements.impl.EAdBasicSceneElement;
import es.eucm.eadventure.common.model.elements.impl.EAdSceneElementDefImpl;
import es.eucm.eadventure.common.model.variables.impl.EAdFieldImpl;

public class EAdSpeakSceneElement extends EAdSpeakEffect {
	
	public EAdSpeakSceneElement(){
		super( );
	}

	public EAdSpeakSceneElement(EAdElement element) {
		super();
		setId("speakSceneElement");
		this.setElement(element);
	}
	
	public void setElement( EAdElement element ){
		if ( element instanceof EAdSceneElement )
			setOrigin(element);
		else if ( element instanceof EAdSceneElementDef )
			setOrigin( new EAdFieldImpl<EAdSceneElement>( element, EAdSceneElementDefImpl.VAR_SCENE_ELEMENT));
		else
			setOrigin( element );
	}

	private void setOrigin(EAdElement element) {
		EAdFieldImpl<Integer> centerX = new EAdFieldImpl<Integer>(element,
				EAdBasicSceneElement.VAR_CENTER_X);
		EAdFieldImpl<Integer> centerY = new EAdFieldImpl<Integer>(element,
				EAdBasicSceneElement.VAR_CENTER_Y);
		
		setPosition(centerX, centerY);

	}

}
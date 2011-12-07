package es.eucm.eadventure.engine.extra;

import es.eucm.eadventure.common.interfaces.Element;
import es.eucm.eadventure.common.model.actions.EAdAction;
import es.eucm.eadventure.common.model.actions.impl.EAdBasicAction;
import es.eucm.eadventure.common.model.effects.impl.EAdActorActionsEffect;
import es.eucm.eadventure.common.model.effects.impl.enums.ChangeActorActions;
import es.eucm.eadventure.common.model.elements.impl.EAdBasicSceneElement;
import es.eucm.eadventure.common.model.elements.impl.EAdSceneElementDefImpl;
import es.eucm.eadventure.common.model.guievents.impl.EAdMouseEventImpl;
import es.eucm.eadventure.common.predef.model.effects.EAdChangeAppearance;
import es.eucm.eadventure.common.resources.assets.AssetDescriptor;

@Element(detailed = EAdBasicSceneElement.class, runtime = EAdBasicSceneElement.class)
public class AndroidAction extends EAdBasicSceneElement {

	public AndroidAction(EAdAction eAdAction) {
		super();

		// TODO null?
		EAdActorActionsEffect e = new EAdActorActionsEffect( null,
				ChangeActorActions.HIDE_ACTIONS);
		this.addBehavior(EAdMouseEventImpl.MOUSE_LEFT_CLICK, e);
		this.addBehavior(EAdMouseEventImpl.MOUSE_RIGHT_CLICK, e);
		this.addBehavior(EAdMouseEventImpl.MOUSE_LEFT_CLICK,
				eAdAction.getEffects());
		this.addBehavior(EAdMouseEventImpl.MOUSE_RIGHT_CLICK,
				eAdAction.getEffects());

		AssetDescriptor asset = eAdAction.getAsset(
				eAdAction.getInitialBundle(), EAdBasicAction.appearance);
		this.getDefinition().getResources().addAsset(this.getDefinition().getInitialBundle(),
				EAdSceneElementDefImpl.appearance, asset);

		if (eAdAction.getResources().getBundles()
				.contains(eAdAction.getHighlightBundle())) {
			getDefinition().getResources().addBundle(eAdAction.getHighlightBundle());
			getDefinition().getResources().addAsset(
					eAdAction.getHighlightBundle(),
					EAdSceneElementDefImpl.appearance,
					eAdAction.getAsset(eAdAction.getHighlightBundle(),
							EAdBasicAction.appearance));
			this.addBehavior(EAdMouseEventImpl.MOUSE_ENTERED,
					new EAdChangeAppearance( this,
							eAdAction.getHighlightBundle()));
		} else
			getDefinition().getResources().addAsset(eAdAction.getHighlightBundle(),
					EAdSceneElementDefImpl.appearance, asset);

		this.addBehavior(
				EAdMouseEventImpl.MOUSE_EXITED,
				new EAdChangeAppearance( this, getDefinition()
						.getInitialBundle()));
		
		
	}

}


package es.eucm.eadventure.common.elementfactories.scenedemos;

import es.eucm.eadventure.common.elementfactories.EAdElementsFactory;
import es.eucm.eadventure.common.model.effects.impl.EAdVarInterpolationEffect;
import es.eucm.eadventure.common.model.effects.impl.EAdVarInterpolationEffect.InterpolationType;
import es.eucm.eadventure.common.model.effects.impl.EAdVarInterpolationEffect.LoopType;
import es.eucm.eadventure.common.model.effects.impl.variables.EAdChangeFieldValueEffect;
import es.eucm.eadventure.common.model.elements.impl.EAdBasicSceneElement;
import es.eucm.eadventure.common.model.elements.impl.EAdComposedElementImpl;
import es.eucm.eadventure.common.model.events.EAdSceneElementEvent;
import es.eucm.eadventure.common.model.events.EAdSceneElementEvent.SceneElementEvent;
import es.eucm.eadventure.common.model.events.impl.EAdSceneElementEventImpl;
import es.eucm.eadventure.common.model.guievents.impl.EAdMouseEventImpl;
import es.eucm.eadventure.common.model.variables.EAdField;
import es.eucm.eadventure.common.model.variables.impl.EAdFieldImpl;
import es.eucm.eadventure.common.model.variables.impl.operations.ValueOperation;
import es.eucm.eadventure.common.params.fills.impl.EAdBorderedColor;
import es.eucm.eadventure.common.params.fills.impl.EAdColor;
import es.eucm.eadventure.common.params.fills.impl.EAdLinearGradient;
import es.eucm.eadventure.common.params.geom.impl.EAdPositionImpl;
import es.eucm.eadventure.common.params.geom.impl.EAdPositionImpl.Corner;
import es.eucm.eadventure.common.resources.assets.drawable.basics.impl.shapes.RectangleShape;

public class ComplexElementScene extends EmptyScene {

	public ComplexElementScene() {
		EAdLinearGradient d = new EAdLinearGradient( EAdColor.BLACK, EAdColor.RED, 400, 400 );
		EAdComposedElementImpl complex = new EAdComposedElementImpl("complex");
		RectangleShape rectangle = new RectangleShape(400, 400);
		rectangle.setFill(EAdBorderedColor.BLACK_ON_WHITE);
		rectangle.setFill(d);
		complex.getResources().addAsset(complex.getInitialBundle(),
				EAdBasicSceneElement.appearance, rectangle);
		complex.setBounds(400, 400);
		complex.setPosition(new EAdPositionImpl(Corner.CENTER, 400, 300));

		EAdBasicSceneElement e = EAdElementsFactory
				.getInstance()
				.getSceneElementFactory()
				.createSceneElement(new RectangleShape(400, 400, d),
						new RectangleShape(400, 400, d), 40, 40);
		
		e.setScale(0.1f);
		e.setVarInitialValue(EAdBasicSceneElement.VAR_ROTATION, (float) Math.PI / 6);
		e.setPosition(new EAdPositionImpl(Corner.CENTER, -50, 50));

		complex.getElements().add(e);

		getElements().add(complex);

		EAdField<Float> rotation = new EAdFieldImpl<Float>(complex,
				EAdBasicSceneElement.VAR_ROTATION);

		EAdVarInterpolationEffect effect = new EAdVarInterpolationEffect(
				"effect");
		effect.setInterpolation(rotation, 0, 2 * (float) Math.PI, 10000,
				LoopType.RESTART, InterpolationType.LINEAR);

		EAdSceneElementEvent event = new EAdSceneElementEventImpl();
		event.addEffect(SceneElementEvent.ADDED_TO_SCENE, effect);

//		complex.getEvents().add(event);
		
		

		EAdField<Float> rotation2 = new EAdFieldImpl<Float>(e,
				EAdBasicSceneElement.VAR_ROTATION);
		
		e.addBehavior(EAdMouseEventImpl.MOUSE_RIGHT_CLICK, new EAdChangeFieldValueEffect( "t", rotation, new ValueOperation((float) 0.1f )));

		EAdVarInterpolationEffect effect2 = new EAdVarInterpolationEffect(
				"effect");
		effect2.setInterpolation(rotation2, 0, 2 * (float) Math.PI, 1000,
				LoopType.RESTART, InterpolationType.LINEAR);

		EAdSceneElementEvent event2 = new EAdSceneElementEventImpl();
		event2.addEffect(SceneElementEvent.ADDED_TO_SCENE, effect2);

//		e.getEvents().add(event2);

		EAdField<Float> scale = new EAdFieldImpl<Float>(complex,
				EAdBasicSceneElement.VAR_SCALE);

		EAdVarInterpolationEffect effect3 = new EAdVarInterpolationEffect(
				"effect");
		effect3.setInterpolation(scale, 0.5f, 2.0f, 5000, LoopType.REVERSE,
				InterpolationType.LINEAR);

		event2.addEffect(SceneElementEvent.ADDED_TO_SCENE, effect3);

	}

	@Override
	public String getDescription() {
		return "A scene to show complex elements";
	}

	public String getDemoName() {
		return "Complex Element Scene";
	}

}
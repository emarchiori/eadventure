package es.eucm.eadventure.common.elementfactories.scenedemos;

import es.eucm.eadventure.common.elementfactories.EAdElementsFactory;
import es.eucm.eadventure.common.model.effects.impl.EAdMoveActiveElement;
import es.eucm.eadventure.common.model.effects.impl.sceneelements.EAdMoveSceneElement;
import es.eucm.eadventure.common.model.effects.impl.text.EAdSpeakEffect;
import es.eucm.eadventure.common.model.elements.impl.EAdBasicSceneElement;
import es.eucm.eadventure.common.model.events.EAdSceneElementEvent;
import es.eucm.eadventure.common.model.events.EAdSceneElementEvent.SceneElementEvent;
import es.eucm.eadventure.common.model.events.impl.EAdSceneElementEventImpl;
import es.eucm.eadventure.common.model.guievents.impl.EAdMouseEventImpl;
import es.eucm.eadventure.common.model.trajectories.impl.SimpleTrajectoryDefinition;
import es.eucm.eadventure.common.model.variables.impl.SystemFields;
import es.eucm.eadventure.common.params.EAdFontImpl;
import es.eucm.eadventure.common.params.fills.impl.EAdColor;
import es.eucm.eadventure.common.params.geom.impl.EAdPositionImpl;
import es.eucm.eadventure.common.params.geom.impl.EAdPositionImpl.Corner;
import es.eucm.eadventure.common.predef.model.effects.EAdMakeActiveElementEffect;
import es.eucm.eadventure.common.predef.model.effects.EAdSpeakSceneElement;

public class SpeakAndMoveScene extends EmptyScene {

	public SpeakAndMoveScene() {
		super.setBackgroundFill(EAdColor.GREEN);
		EAdBasicSceneElement character = EAdElementsFactory
				.getInstance()
				.getSceneElementFactory()
				.createSceneElement(CharacterScene.getStateDrawable(), 100, 300);

		character.setScale(3.0f);
		character.setPosition(new EAdPositionImpl(Corner.BOTTOM_CENTER, 400,
				400));



		EAdSpeakEffect effect = new EAdSpeakSceneElement(character);
		EAdElementsFactory
				.getInstance()
				.getStringFactory()
				.setString(
						effect.getString(),
						"Hello, my friend. I have a loooooooooooooooooooooooooooooot of things to say. Will I be able to tell all in one only bubble? Yeah, I didn't think so. So let's move on to the next topic, shall we?Hello, my friend. I have a loooooooooooooooooooooooooooooot of things to say. Will I be able to tell all in one only bubble? Yeah, I didn't think so. So let's move on to the next topic, shall we?Hello, my friend. I have a loooooooooooooooooooooooooooooot of things to say. Will I be able to tell all in one only bubble? Yeah, I didn't think so. So let's move on to the next topic, shall we?Hello, my friend. I have a loooooooooooooooooooooooooooooot of things to say. Will I be able to tell all in one only bubble? Yeah, I didn't think so. So let's move on to the next topic, shall we?Hello, my friend. I have a loooooooooooooooooooooooooooooot of things to say. Will I be able to tell all in one only bubble? Yeah, I didn't think so. So let's move on to the next topic, shall we? Hello, my friend. I have a loooooooooooooooooooooooooooooot of things to say. Will I be able to tell all in one only bubble? Yeah, I didn't think so. So let's move on to the next topic, shall we?");
//		effect.setBalloonType(BalloonType.RECTANGLE);
		effect.setFont(new EAdFontImpl(18));

		character.addBehavior(EAdMouseEventImpl.MOUSE_LEFT_PRESSED, effect);

		this.getElements().add(character);

		EAdMakeActiveElementEffect makeActive = new EAdMakeActiveElementEffect(
				character);

		EAdSceneElementEvent event = new EAdSceneElementEventImpl(
				"makeAcitveCharacter");
		event.addEffect(SceneElementEvent.ADDED_TO_SCENE, makeActive);
		character.getEvents().add(event);

		
		SimpleTrajectoryDefinition d = new SimpleTrajectoryDefinition(false);
		d.setLimits(0, 0, 800, 600);
		setTrajectoryDefinition(d);

		EAdMoveSceneElement move = new EAdMoveSceneElement("moveCharacter");
		move.setTargetCoordiantes(SystemFields.MOUSE_X, SystemFields.MOUSE_Y);

		getBackground().addBehavior(EAdMouseEventImpl.MOUSE_LEFT_PRESSED,
				new EAdMoveActiveElement("moveCharacter"));
	}

	@Override
	public String getSceneDescription() {
		return "A scene with a character moving and talking";
	}

	public String getDemoName() {
		return "Speak and Move Scene";
	}

}

package es.eucm.eadventure.common.elementfactories.scenedemos;

import es.eucm.eadventure.common.elementfactories.EAdElementsFactory;
import es.eucm.eadventure.common.model.conditions.impl.ANDCondition;
import es.eucm.eadventure.common.model.conditions.impl.OperationCondition;
import es.eucm.eadventure.common.model.conditions.impl.enums.Comparator;
import es.eucm.eadventure.common.model.effects.impl.EAdAddActorReferenceEffect;
import es.eucm.eadventure.common.model.effects.impl.EAdInterpolationEffect;
import es.eucm.eadventure.common.model.effects.impl.enums.InterpolationType;
import es.eucm.eadventure.common.model.effects.impl.enums.InterpolationLoopType;
import es.eucm.eadventure.common.model.effects.impl.physics.EAdPhysicsEffect;
import es.eucm.eadventure.common.model.effects.impl.physics.EAdPhysicsEffect.PhType;
import es.eucm.eadventure.common.model.effects.impl.physics.PhApplyImpluse;
import es.eucm.eadventure.common.model.effects.impl.variables.EAdChangeFieldValueEffect;
import es.eucm.eadventure.common.model.elements.EAdSceneElementDef;
import es.eucm.eadventure.common.model.elements.impl.EAdSceneElementDefImpl;
import es.eucm.eadventure.common.model.elements.impl.EAdBasicSceneElement;
import es.eucm.eadventure.common.model.elements.impl.EAdSceneImpl;
import es.eucm.eadventure.common.model.events.EAdConditionEvent;
import es.eucm.eadventure.common.model.events.EAdSceneElementEvent;
import es.eucm.eadventure.common.model.events.enums.ConditionedEventType;
import es.eucm.eadventure.common.model.events.enums.SceneElementEventType;
import es.eucm.eadventure.common.model.events.impl.EAdConditionEventImpl;
import es.eucm.eadventure.common.model.events.impl.EAdSceneElementEventImpl;
import es.eucm.eadventure.common.model.guievents.impl.EAdMouseEventImpl;
import es.eucm.eadventure.common.model.variables.EAdField;
import es.eucm.eadventure.common.model.variables.impl.EAdFieldImpl;
import es.eucm.eadventure.common.model.variables.impl.SystemFields;
import es.eucm.eadventure.common.model.variables.impl.operations.MathOperation;
import es.eucm.eadventure.common.params.fills.impl.EAdColor;
import es.eucm.eadventure.common.params.fills.impl.EAdLinearGradient;
import es.eucm.eadventure.common.params.geom.impl.EAdPositionImpl;
import es.eucm.eadventure.common.params.geom.impl.EAdPositionImpl.Corner;
import es.eucm.eadventure.common.resources.assets.drawable.basics.impl.CaptionImpl;
import es.eucm.eadventure.common.resources.assets.drawable.basics.impl.ImageImpl;
import es.eucm.eadventure.common.resources.assets.drawable.basics.impl.shapes.BezierShape;
import es.eucm.eadventure.common.resources.assets.drawable.basics.impl.shapes.CircleShape;
import es.eucm.eadventure.common.resources.assets.drawable.basics.impl.shapes.RectangleShape;

public class PhysicsScene3 extends EmptyScene {

	public PhysicsScene3() {
		init();
	}

	protected void init() {
		setBackgroundFill(new EAdLinearGradient(EAdColor.CYAN, EAdColor.BLUE,
				800, 600));

		// Add sky

		addSky();

		EAdBasicSceneElement e2 = new EAdBasicSceneElement(
				new RectangleShape(10, 100, EAdColor.BROWN));
		e2.setId("e2");
		getElements().add(e2);
		e2.setPosition(new EAdPositionImpl(Corner.CENTER, 500, 300));

		EAdPhysicsEffect effect = new EAdPhysicsEffect();
		effect.addSceneElement(e2);
		
		e2.setVarInitialValue(EAdPhysicsEffect.VAR_PH_TYPE, PhType.DYNAMIC);

		EAdConditionEvent event = new EAdConditionEventImpl();
		OperationCondition condition = new OperationCondition(
				new EAdFieldImpl<Boolean>(this, EAdSceneImpl.VAR_SCENE_LOADED));
		event.setCondition(condition);
		event.addEffect(ConditionedEventType.CONDITIONS_MET, effect);

		// getEvents().add(event);
		getBackground()
				.addBehavior(EAdMouseEventImpl.MOUSE_ENTERED, effect);

		addCanyon(effect);
		addWalls(effect);
		addPendulum(effect);
	}

	@Override
	public String getSceneDescription() {
		return "A scene showing the use of jbox2d";
	}

	protected void addWalls(EAdPhysicsEffect effect) {
		RectangleShape groundS = new RectangleShape(750, 50);
		groundS.setPaint(new EAdLinearGradient(EAdColor.BROWN,
				EAdColor.DARK_BROWN, 750, 50));
		EAdBasicSceneElement ground = new EAdBasicSceneElement(groundS);
		ground.setId("ground");
		ground.setPosition(new EAdPositionImpl(Corner.CENTER, 400, 545));

		EAdBasicSceneElement wall = new EAdBasicSceneElement( groundS);
		wall.setId("wall");
		wall.setPosition(new EAdPositionImpl(Corner.CENTER, 775, 300));
		wall.setVarInitialValue(EAdBasicSceneElement.VAR_ROTATION,
				(float) Math.PI / 2.0f);

		EAdBasicSceneElement wall2 = new EAdBasicSceneElement( groundS);
		wall2.setId("wall");
		wall2.setPosition(new EAdPositionImpl(Corner.CENTER, 25, 300));
		wall2.setVarInitialValue(EAdBasicSceneElement.VAR_ROTATION,
				(float) Math.PI / 2.0f);

		effect.addSceneElement(ground);
		getElements().add(ground);
		effect.addSceneElement(wall);
		getElements().add(wall);
		effect.addSceneElement(wall2);
		getElements().add(wall2);

	}

	public String getDemoName() {
		return "Physics Scene";
	}

	private void addCanyon(EAdPhysicsEffect effect) {

		EAdBasicSceneElement canyon = new EAdBasicSceneElement(
				new ImageImpl("@drawable/canyon.png"));
		canyon.setId("canyon");
		EAdBasicSceneElement canyonSupport = new EAdBasicSceneElement(
				 new ImageImpl("@drawable/canyonbottom.png"));
		canyonSupport.setId("canyonSupport");

		canyon.setPosition(new EAdPositionImpl(Corner.CENTER, 130, 475));
		canyonSupport.setPosition(new EAdPositionImpl(100, 475));

		EAdField<Float> rotationField = new EAdFieldImpl<Float>(canyon,
				EAdBasicSceneElement.VAR_ROTATION);

		EAdChangeFieldValueEffect followMouse = new EAdChangeFieldValueEffect();
		followMouse.setId("followMouse");

		EAdField<Integer> mouseX = SystemFields.MOUSE_X;
		EAdField<Integer> mouseY = SystemFields.MOUSE_Y;
		EAdField<Integer> canyonX = new EAdFieldImpl<Integer>(canyon,
				EAdBasicSceneElement.VAR_X);

		EAdField<Integer> canyonY = new EAdFieldImpl<Integer>(canyon,
				EAdBasicSceneElement.VAR_Y);

		String expression = "- acos( ( [2] - [0] ) / sqrt( sqr( [2] - [0] ) + sqr( [3] - [1] ) ) )";
		MathOperation op = new MathOperation(expression, canyonX, canyonY,
				mouseX, mouseY);
		followMouse.setOperation(op);
		followMouse.addField(rotationField);
		OperationCondition c1 = new OperationCondition(mouseX, 0,
				Comparator.GREATER_EQUAL);
		OperationCondition c2 = new OperationCondition(mouseY,
				new EAdFieldImpl<Integer>(canyon, EAdBasicSceneElement.VAR_Y),
				Comparator.LESS_EQUAL);
		OperationCondition c3 = new OperationCondition(mouseX,
				new EAdFieldImpl<Integer>(canyon, EAdBasicSceneElement.VAR_X),
				Comparator.GREATER_EQUAL);
		followMouse.setCondition(new ANDCondition(c1, c2, c3));

		EAdSceneElementEvent event = new EAdSceneElementEventImpl();
		event.addEffect(SceneElementEventType.ALWAYS, followMouse);
		canyon.getEvents().add(event);

		getElements().add(canyon);
		getElements().add(canyonSupport);

		// Bullet generation

		EAdSceneElementDef bullet = new EAdSceneElementDefImpl();
		bullet.setId("bullet");
		BezierShape circle = new CircleShape(10, 10, 10, 25);
		circle.setPaint(new EAdLinearGradient(EAdColor.LIGHT_GRAY,
				EAdColor.DARK_GRAY, 20, 20));
		bullet.getResources().addAsset(bullet.getInitialBundle(),
				EAdBasicSceneElement.appearance, circle);

		PhApplyImpluse applyForce = new PhApplyImpluse();
		applyForce.setForce(new MathOperation("[0] - [1]", mouseX, canyonX),
				new MathOperation("[0] - [1]", mouseY, canyonY));
		EAdAddActorReferenceEffect addEffect = new EAdAddActorReferenceEffect(
				bullet, new EAdPositionImpl(Corner.CENTER, 140, 470),
				applyForce);

		getBackground().addBehavior(EAdMouseEventImpl.MOUSE_LEFT_PRESSED,
				addEffect);

		// Add text
		CaptionImpl c = EAdElementsFactory.getInstance().getCaptionFactory()
				.createCaption("radians: #0 ");
		c.getFields().add(rotationField);
		c.setBubblePaint(EAdColor.TRANSPARENT);
		EAdBasicSceneElement e = new EAdBasicSceneElement( c);
		e.setPosition(120, 375);
		getElements().add(e);

	}

	private void addPendulum(EAdPhysicsEffect effect) {

		EAdBasicSceneElement holder = new EAdBasicSceneElement(
				new RectangleShape(100, 10, new EAdLinearGradient(
						EAdColor.DARK_BROWN, EAdColor.LIGHT_BROWN, 100, 10)));
		holder.setId("holder");
		holder.setPosition(new EAdPositionImpl(Corner.CENTER, 400, 50));

		EAdBasicSceneElement rope = new EAdBasicSceneElement(
				new RectangleShape(150, 10, new EAdLinearGradient(
						EAdColor.YELLOW, EAdColor.LIGHT_BROWN, 150, 10)));
		rope.setId("rope");

		rope.setPosition(new EAdPositionImpl(Corner.CENTER, 450, 50));
		rope.setVarInitialValue(EAdPhysicsEffect.VAR_PH_TYPE, PhType.DYNAMIC);
		rope.setVarInitialValue(EAdPhysicsEffect.VAR_PH_FRICTION, 0.7f);
		getElements().add(rope);
		getElements().add(holder);

		effect.addJoint(holder, rope);

	}

	private void addSky() {
		getBackground().getResources().addAsset(
				this.getBackground().getInitialBundle(),
				EAdBasicSceneElement.appearance,
				new ImageImpl("@drawable/sky.png"));

		EAdSceneElementEvent event = new EAdSceneElementEventImpl();

		EAdInterpolationEffect effect = new EAdInterpolationEffect(
				new EAdFieldImpl<Integer>(getBackground(),
						EAdBasicSceneElement.VAR_X), 0, -800, 100000,
				InterpolationLoopType.REVERSE, InterpolationType.LINEAR);

		event.addEffect(SceneElementEventType.ADDED_TO_SCENE,
				effect);

		this.getBackground().getEvents().add(event);

	}

}
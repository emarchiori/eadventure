package es.eucm.eadventure.common.elementfactories.scenedemos;

import es.eucm.eadventure.common.model.conditions.impl.EmptyCondition;
import es.eucm.eadventure.common.model.effects.impl.variables.EAdChangeFieldValueEffect;
import es.eucm.eadventure.common.model.elements.impl.EAdBasicSceneElement;
import es.eucm.eadventure.common.model.guievents.EAdDragEvent.DragAction;
import es.eucm.eadventure.common.model.guievents.impl.EAdDragEventImpl;
import es.eucm.eadventure.common.model.guievents.impl.EAdMouseEventImpl;
import es.eucm.eadventure.common.model.variables.EAdField;
import es.eucm.eadventure.common.model.variables.impl.EAdFieldImpl;
import es.eucm.eadventure.common.model.variables.impl.operations.ValueOperation;
import es.eucm.eadventure.common.model.variables.impl.operations.MathOperation;
import es.eucm.eadventure.common.params.fills.impl.EAdBorderedColor;
import es.eucm.eadventure.common.params.fills.impl.EAdColor;
import es.eucm.eadventure.common.params.fills.impl.EAdLinearGradient;
import es.eucm.eadventure.common.params.geom.impl.EAdPositionImpl;
import es.eucm.eadventure.common.params.geom.impl.EAdPositionImpl.Corner;
import es.eucm.eadventure.common.resources.assets.drawable.basics.impl.shapes.BallonShape;
import es.eucm.eadventure.common.resources.assets.drawable.basics.impl.shapes.BallonShape.BalloonType;
import es.eucm.eadventure.common.resources.assets.drawable.basics.impl.shapes.BezierShape;

public class DragDropScene extends EmptyScene {

	public DragDropScene() {
		setBackgroundFill(new EAdLinearGradient(EAdColor.LIGHT_GRAY,
				new EAdColor(245, 255, 245), 800, 600));
		BezierShape shape = new BallonShape(0, 0, 100, 100,
				BalloonType.ROUNDED_RECTANGLE);
		shape.setFill(new EAdLinearGradient(EAdColor.RED, new EAdColor(200, 0,
				0), 100, 100));
		EAdBasicSceneElement e1 = new EAdBasicSceneElement("e1", shape);
		e1.setDraggabe(EmptyCondition.TRUE_EMPTY_CONDITION);
		e1.setPosition(new EAdPositionImpl(Corner.CENTER, 100, 100));
		EAdField<Float> rotation = new EAdFieldImpl<Float>(e1,
				EAdBasicSceneElement.VAR_ROTATION);
		EAdChangeFieldValueEffect changeRotation1 = new EAdChangeFieldValueEffect(
				"change", rotation,
				new ValueOperation((float) (Math.PI / 8.0f)));
		EAdChangeFieldValueEffect changeRotation2 = new EAdChangeFieldValueEffect(
				"change", rotation, new ValueOperation(0.0f));
		e1.addBehavior(EAdMouseEventImpl.MOUSE_START_DRAG, changeRotation1);
		e1.addBehavior(EAdMouseEventImpl.MOUSE_DROP, changeRotation2);

		BezierShape shape2 = new BallonShape(0, 0, 110, 110,
				BalloonType.ROUNDED_RECTANGLE);
		shape2.setFill(EAdBorderedColor.BLACK_ON_WHITE);
		EAdBasicSceneElement e2 = new EAdBasicSceneElement("e2", shape2);
		e2.setPosition(new EAdPositionImpl(Corner.CENTER, 100, 300));

		EAdBasicSceneElement e3 = new EAdBasicSceneElement("e3", shape2);
		e3.setPosition(new EAdPositionImpl(Corner.CENTER, 300, 300));

		addBehaviors(e2, e1);
		addBehaviors(e3, e1);

		getElements().add(e2);
		getElements().add(e3);
		getElements().add(e1);

	}

	private void addBehaviors(EAdBasicSceneElement e2, EAdBasicSceneElement e1) {
		EAdField<Float> scale = new EAdFieldImpl<Float>(e2,
				EAdBasicSceneElement.VAR_SCALE);
		EAdChangeFieldValueEffect changeScale1 = new EAdChangeFieldValueEffect(
				"changeScale", scale, new ValueOperation(1.2f));
		EAdChangeFieldValueEffect changeScale2 = new EAdChangeFieldValueEffect(
				"changeScale", scale, new ValueOperation(1.0f));
		e2.addBehavior(new EAdDragEventImpl(e1, DragAction.ENTERED),
				changeScale1);
		e2.addBehavior(new EAdDragEventImpl(e1, DragAction.EXITED),
				changeScale2);

		EAdChangeFieldValueEffect changeX = new EAdChangeFieldValueEffect("x",
				new EAdFieldImpl<Integer>(e1, EAdBasicSceneElement.VAR_X),
				new MathOperation("[0]", new EAdFieldImpl<Integer>(e2,
						EAdBasicSceneElement.VAR_X)));

		EAdChangeFieldValueEffect changeY = new EAdChangeFieldValueEffect("x",
				new EAdFieldImpl<Integer>(e1, EAdBasicSceneElement.VAR_Y),
				new MathOperation("[0]", new EAdFieldImpl<Integer>(e2,
						EAdBasicSceneElement.VAR_Y)));

		e2.addBehavior(new EAdDragEventImpl(e1, DragAction.DROP), changeX);
		e2.addBehavior(new EAdDragEventImpl(e1, DragAction.DROP), changeY);
	}

	@Override
	public String getDescription() {
		return "A scene showing drag and drop";
	}

	public String getDemoName() {
		return "Drag & Drop Scene";
	}

}
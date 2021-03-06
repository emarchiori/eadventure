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

package ead.common.model.elements.effects;

import ead.common.interfaces.Element;
import ead.common.interfaces.Param;
import ead.common.model.EAdElement;
import ead.common.model.elements.effects.enums.InterpolationLoopType;
import ead.common.model.elements.effects.enums.InterpolationType;
import ead.common.model.elements.variables.EAdField;
import ead.common.model.elements.variables.BasicField;
import ead.common.model.elements.variables.EAdVarDef;
import ead.common.model.elements.variables.operations.MathOp;

/**
 * Effect that performs an interpolation between two values in an {@link EAdVar}
 * 
 * 
 */
@Element
public class InterpolationEf extends AbstractEffect {

	@Param("element")
	private EAdElement element;

	@Param("var")
	private EAdVarDef<?> varDef;

	@Param("initialValue")
	private MathOp initialValue;

	@Param("endValue")
	private MathOp endValue;

	@Param("time")
	private int interpolationTime;

	@Param("delay")
	private int delay;

	@Param("loopType")
	private InterpolationLoopType loopType;

	@Param("loops")
	private int loops;

	@Param("interpolation")
	private InterpolationType interpolationType;

	/**
	 * @param elementField
	 *            field containing the element holding the variable
	 * @param element
	 *            the element holding the variable
	 * @param varDef
	 *            the variable definition
	 * @param initialValue
	 *            the initial value for the interpolation
	 * @param endValue
	 *            the end value for the interpolation
	 * @param interpolationTime
	 *            the time for the interpolation
	 * @param delay
	 *            the delay until the interpolation begins
	 * @param loopType
	 *            the loop type
	 * @param loops
	 *            the number of loops. If loops < 0, is considered to be
	 *            infinite
	 * @param interpolationType
	 *            the interpolation type
	 */
	public InterpolationEf(EAdElement element,
			EAdVarDef<?> varDef, MathOp initialValue,
			MathOp endValue, int interpolationTime, int delay,
			InterpolationLoopType loopType, int loops, InterpolationType interpolationType) {
		super();
		setId("interpolation");
		this.element = element;
		this.varDef = varDef;
		this.initialValue = initialValue;
		this.endValue = endValue;
		this.interpolationTime = interpolationTime;
		this.delay = delay;
		this.loopType = loopType;
		this.loops = loopType == InterpolationLoopType.NO_LOOP ? 1 : loops;
		this.interpolationType = interpolationType;
		setQueueable(true);
	}

	public InterpolationEf(EAdElement element, EAdVarDef<?> varDef,
			Number initialValue, Number endValue, int interpolationTime,
			int delay, InterpolationLoopType loopType, int loops,
			InterpolationType interpolationType) {
		this(element, varDef, new MathOp(initialValue
				+ ""), new MathOp(endValue + ""), interpolationTime,
				delay, loopType, loops, interpolationType);
	}

	public InterpolationEf(EAdElement element, EAdVarDef<?> varDef,
			Number initialValue, Number endValue, int interpolationTime) {
		this( element, varDef, new MathOp(initialValue
				+ ""), new MathOp(endValue + ""), interpolationTime, 0,
				InterpolationLoopType.NO_LOOP, 1, InterpolationType.LINEAR);
	}

	public InterpolationEf() {
		super();
	}
	
	public InterpolationEf(EAdField<?> field, float startValue, float endValue, int time ){
		this(field, startValue, endValue, time, InterpolationLoopType.NO_LOOP, InterpolationType.LINEAR);
	}

	public InterpolationEf(EAdField<?> field, float startValue,
			float endValue, int time, InterpolationLoopType loopType,
			InterpolationType interpolationType) {
		this(field.getElement(), field.getVarDef(), startValue,
				endValue, time, 0, loopType, -1, interpolationType);
	}

	public InterpolationEf(BasicField<?> field, float start,
			float endValue, int time, InterpolationLoopType loopType) {
		this(field, start, endValue, time, loopType, InterpolationType.LINEAR);
	}

	public InterpolationEf(BasicField<?> field, int start,
			int end, int timeToFinish, InterpolationLoopType loopType) {
		this(field, (float) start, (float) end, timeToFinish, loopType);
	}

	public InterpolationEf(EAdField<Integer> field,
			MathOp start, MathOp end, int time,
			InterpolationLoopType loopType, InterpolationType interpolation) {
		this( field.getElement(), field.getVarDef(), start, end, time, 0, loopType, -1, interpolation);
	}

	public EAdElement getElement() {
		return element;
	}

	public void setElement(EAdElement element) {
		this.element = element;
	}

	public EAdVarDef<?> getVarDef() {
		return varDef;
	}

	public void setVarDef(EAdVarDef<?> varDef) {
		this.varDef = varDef;
	}

	public MathOp getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(MathOp initialValue) {
		this.initialValue = initialValue;
	}

	public MathOp getEndValue() {
		return endValue;
	}

	public void setEndValue(MathOp endValue) {
		this.endValue = endValue;
	}

	public int getInterpolationTime() {
		return interpolationTime;
	}

	public void setInterpolationTime(int interpolationTime) {
		this.interpolationTime = interpolationTime;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public InterpolationLoopType getLoopType() {
		return loopType;
	}

	public void setLoopType(InterpolationLoopType loopType) {
		this.loopType = loopType;
	}

	public int getLoops() {
		return loops;
	}

	public void setLoops(int loops) {
		this.loops = loops;
	}

	public InterpolationType getInterpolationType() {
		return interpolationType;
	}

	public void setInterpolationType(InterpolationType interpolationType) {
		this.interpolationType = interpolationType;
	}

	
	
}

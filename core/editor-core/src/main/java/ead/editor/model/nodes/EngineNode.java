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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ead.editor.model.nodes;

import ead.common.interfaces.Param;
import ead.common.model.EAdElement;
import ead.common.model.elements.extra.EAdList;
import ead.common.model.elements.extra.EAdMap;
import ead.common.model.elements.variables.VarDef;
import ead.common.params.EAdParam;
import ead.common.resources.EAdAssetDescriptor;
import ead.common.resources.EAdResources;
import ead.common.resources.assets.AssetDescriptor;
import ead.editor.model.EditorModel;
import ead.editor.model.visitor.ModelVisitorDriver;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * An engine-model node. Used as a base for the dependency-tracking mechanism
 * for the editor model.
 * @author mfreire
 */
public class EngineNode<T> extends DependencyNode<T> {

	public EngineNode(int id, T content) {
		super(id, content);
	}

	/**
	 * Generates a one-line description with as much information as possible.
	 * @return a human-readable description of this node
	 */
	@Override
	public String getTextualDescription(EditorModel m) {
		StringBuilder sb = new StringBuilder();
		appendDescription(m, content, sb, 0, 3);
		return sb.toString();
	}

	/**
	 * Returns a descriptive string for a given object.
	 * @param o object to drive into.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private void appendDescription(EditorModel m, Object o, StringBuilder sb, int depth, int maxDepth) {
		if (maxDepth == depth) {
			return;
		}


		String indent = new String(new char[depth*2]).replace('\0', ' ');

        if (o == null) {
            sb.append("(null)");
        }

		String id = "" + m.getIdFor(o);
		String cname = o.getClass().getSimpleName();
		if (o instanceof EAdElement) {
            if (o instanceof VarDef) {
                VarDef<?> v = ((VarDef)o);
                sb.append(indent + "(" + v.getId() + ") - "
                        + v.getType().getSimpleName() + " "
                        + v.getName() + " = "
                        + v.getInitialValue()
                        + "\n");
            } else {
                sb.append(indent + cname + " (" + id + ")" + "\n");
                appendParams(m, o, sb, depth, maxDepth);
            }
		} else if (o instanceof EAdList) {
			EAdList target = (EAdList) o;
			sb.append(indent + cname + " (" + id + ")" + "\n");
			int i = 0;
            if (target.size() == 0) {
                sb.append(indent + "  (empty)\n");
            } else if (depth == maxDepth -1) {
                sb.append(indent + "  (" + target.size() + " elements inside)\n");
            } else {
                for (i = 0; i < target.size(); i++) {
                    // visit all children-values of this list
                    Object inner = target.get(i);
                    if (inner != null) {
                        appendDescription(m, inner, sb, depth + 1, maxDepth);
                    }
                }
            }
		} else if (o instanceof EAdMap) {
			EAdMap<?, ?> target = (EAdMap<?, ?>) o;
			sb.append(indent + cname + " (" + id + ")" + "\n");
			int i = 0;
            if (target.size() == 0) {
                sb.append(indent + "  (empty)\n");
            } else if (depth == maxDepth -1) {
                sb.append(indent + "  (" + target.size() + " elements inside)\n");
            } else {
                for (Map.Entry<?, ?> e : target.entrySet()) {
                    if (e.getKey() != null) {
                        appendDescription(m, e.getKey(), sb, depth + 1, maxDepth);
                    }
                    sb.append(indent + "  -m->\n");
                    if (e.getValue() != null) {
                        appendDescription(m, e.getValue(), sb, depth + 1, maxDepth);
                    }
                    i++;
                }
            }
		} else if (o instanceof EAdParam) {
			sb.append(indent + ((EAdParam)o).toStringData());
		} else if (o instanceof EAdResources) {
			sb.append(indent + "resource" + " (" + id + "): x"
					+ ((EAdResources)o).getBundles().size()
					+ "\n");
		} else if (o instanceof EAdAssetDescriptor) {
			sb.append(indent + "asset" + " (" + id + "): "
					+ ((EAdAssetDescriptor)o).getAssetId()
					+ "\n");
			appendParams(m, o, sb, depth, maxDepth);
		} else if (o instanceof Class) {
			sb.append(indent + ((Class<?>) o).getName() + "\n");
		} else {
			sb.append(indent + " (" + cname + "~" + o.toString() + ")\n");
		}
	}

	private void appendParams(EditorModel m, Object o, StringBuilder sb, int depth, int maxDepth) {
		if (maxDepth == depth+1) {
			return;
		}
		String indent = new String(new char[depth*2]).replace('\0', ' ');
        Class<?> clazz = o.getClass();

        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                try {
                    Param param = field.getAnnotation(Param.class);
                    if (param != null) {
                        PropertyDescriptor pd = ModelVisitorDriver.getPropertyDescriptor(
                                o.getClass(), field.getName());
                        if (pd == null) {
							continue;
                        }
                        Method method = pd.getReadMethod();
                        if (method == null) {
							continue;
                        }
                        Object v = method.invoke(o);
                        if (! ModelVisitorDriver.isEmpty(v)) {
							sb.append(indent + pd.getName() + " --> ");
                            appendDescription(m, v, sb, depth+1, maxDepth);
                            sb.append("\n");
                        }
                    }
                } catch (Exception e) {

                }
            }
            clazz = clazz.getSuperclass();
        }
	}
}

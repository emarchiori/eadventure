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

package ead.engine.core.platform.assets;

import java.util.List;

import ead.common.interfaces.features.Resourced;
import ead.common.model.elements.scene.EAdScene;
import ead.common.resources.EAdBundleId;
import ead.common.resources.assets.AssetDescriptor;
import ead.common.resources.assets.drawable.EAdDrawable;
import ead.common.util.EAdURI;

/**
 * <p>
 * Handler of the assets in the eAdventure engine
 * </p>
 * <p>
 * The class that implements this interfaces is in charge of loading the
 * different assets into the system, and possibly performing some platform
 * specific optimizations as necessary.
 * </p>
 */
public interface AssetHandler {

	/**
	 * Initialize the asset handler, so assets can be loaded into the system
	 */
	void initialize();

	/**
	 * Terminate the asset handler, so resources are freed accordingly
	 */
	void terminate();

	/**
	 * Returns the runtime asset asset represented by the given id in the
	 * element for the selected bundle
	 * 
	 * @param element
	 *            The element with the asset
	 * @param bundleId
	 *            The selected bundle
	 * @param id
	 *            The id of the asset
	 * @return The platform-independent runtime asset
	 * @see RuntimeAsset
	 */
	RuntimeAsset<?> getRuntimeAsset(Resourced element, EAdBundleId bundleId,
			String id);

	/**
	 * Returns the runtime asset asset represented by the given id in the
	 * element, with no asset bundle
	 * 
	 * @param element
	 *            The element with the asset
	 * @param id
	 *            The id of the asset
	 * @return The platform-independent runtime asset
	 * @see RuntimeAsset
	 */
	RuntimeAsset<?> getRuntimeAsset(Resourced element, String id);

	/**
	 * Returns the runtime asset for a given asset descriptor
	 * 
	 * @param <T>
	 *            The type of the asset descriptor
	 * @param descriptor
	 *            The descriptor of the asset
	 * @return The runtime asset
	 * @see RuntimeAsset
	 * @see AssetDescriptor
	 */
	<T extends AssetDescriptor> RuntimeAsset<T> getRuntimeAsset(T descriptor);

	/**
	 * Returns the runtime asset for a given asset descriptor. It loads it if
	 * parameter load is true. Otherwise, asset must be loaded through
	 * {@link RuntimeAsset#loadAsset()}
	 * 
	 * @param descriptor
	 *            the asset descriptor
	 * @param load
	 *            if the asset must be loaded
	 * @return the runtime asset
	 */
	<T extends AssetDescriptor> RuntimeAsset<T> getRuntimeAsset(T descriptor,
			boolean load);

	<T extends EAdDrawable, GraphicContext> RuntimeDrawable<T, GraphicContext> getDrawableAsset(
			T descriptor);

	/**
	 * Returns true if the adventure assets have been correctly loaded
	 * 
	 * @return true if assets loaded
	 */
	boolean isLoaded();

	/**
	 * Frees and removes all the assets contained in the cache, except for the
	 * ones in the exceptions list
	 * 
	 * @param exceptions
	 *            list with assets not to be deleted
	 */
	void clean(List<AssetDescriptor> exceptions);

	/**
	 * Sets the resources location
	 * 
	 * @param uri
	 *            uri point to the resources locatin root
	 */
	void setResourcesLocation(EAdURI uri);

	/**
	 * Returns a set of strings containing the text file in the given path.
	 * Useful to read some configuration files at multi-platform level
	 * 
	 * @param path
	 *            textFile path
	 * @return
	 */
	void getTextFile(String path, LoadTextFileListener callback );

	/**
	 * Sets if the cache is enable for this asset handler. Cache is enabled by
	 * default.
	 * 
	 * @param enable
	 */
	void setCacheEnabled(boolean enable);

	/**
	 * Queues the scene to load all its assets. This method DOES NOT load the
	 * assets. {@link AssetHandler#loadStep()} must be used in order to do that.
	 * 
	 * @param scene
	 *            the scene whose assets must be loaded
	 */
	void queueSceneToLoad(EAdScene scene);

	/**
	 * Loads one asset of the queue.
	 * 
	 * @return if there are assets left to be loaded
	 */
	boolean loadStep();

	/**
	 * Clears the assets queue
	 */
	void clearAssetQueue();
	
	public interface LoadTextFileListener {
		
		void read(String text);
	}

}

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

package ead.importer.auxiliar.inputstreamcreators;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import es.eucm.eadventure.common.loader.InputStreamCreator;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EAPInputStreamCreator implements InputStreamCreator {

	private String absolutePath;

	private static final Logger logger = LoggerFactory.getLogger("EAPInputStreamCreator");

	public void setFile( String file ){
		this.absolutePath = file;
	}

	@Override
	public InputStream buildInputStream( String filePath ) {
		try {
			return new FileInputStream( new File( absolutePath, filePath ) );
		}
		catch ( FileNotFoundException e ) {
			
		}
		return null;
	}

	@Override
	public URL buildURL( String path ) {
		try {
			URL url = new File( absolutePath, path ).toURI( ).toURL( );
			return url;
		}
		catch ( MalformedURLException e ) {
			logger.error("Problem building URL for path: '{}'", path, e);
			return null;
		}
	}

	@Override
	public String[] listNames( String filePath ) {
		File dir = new File( absolutePath, filePath );
		if ( dir.exists( ) && dir.isDirectory( ) ) {
			return dir.list( );
		} else {
			logger.warn("Path does not exist or is not a directory: '{}'", filePath);
			return new String[0];
		}
	}
}
